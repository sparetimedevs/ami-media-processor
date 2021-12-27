/*
 * Copyright (c) 2021 sparetimedevs and respective authors and developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sparetimedevs.ami.mediaprocessor.file

import cats.data.EitherT
import cats.effect.IO
import cats.syntax.either.catsSyntaxEitherId
import com.sparetimedevs.ami.core.ScorePartwise
import com.sparetimedevs.ami.mediaprocessor.{Errors, IOEitherErrorsOr, XmlParseError, asIOEitherErrorsOrT}
import musicxml.{Scoreu45partwise, XMLProtocol}
import scalaxb.{ElemName, XMLFormat}

import java.io.File
import scala.xml.{Node, NodeSeq}

private[mediaprocessor] def read(musicXmlData: Array[Byte], xsdPath: String): IOEitherErrorsOr[ScorePartwise] =
  validate(musicXmlData, xsdPath)
    .flatMap((rootElement: Node) => ScorePartwiseXmlProtocol.fromXml(rootElement))
    .map((scorePartwiseXml: Scoreu45partwise) => toDomainModel(scorePartwiseXml))

private object ScorePartwiseXmlProtocol extends XMLProtocol {

  private implicit val scorePartwiseXmlFormat: XMLFormat[Scoreu45partwise] = Musicxml_Scoreu45partwiseFormat

  def fromXml(seq: NodeSeq, stack: List[ElemName] = Nil): IOEitherErrorsOr[Scoreu45partwise] =
    IO {
      scalaxb.fromXMLEither(seq, stack).left.map { (message: String) => XmlParseError(message).asOnlyError }
    }.handleErrorWith { (t: Throwable) => IO.pure(XmlParseError(t.getMessage).asOnlyError.asLeft) }.asIOEitherErrorsOrT
}
