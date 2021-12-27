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

package com.sparetimedevs.ami.mediaprocessor

import cats.data.EitherT
import cats.effect.{ContextShift, IO}
import com.sparetimedevs.ami.MediaProcessor
import com.sparetimedevs.ami.core.{Measure, Part, ScorePartwise}
import com.sparetimedevs.ami.mediaprocessor.file.{Format, read}
import com.sparetimedevs.ami.mediaprocessor.graphic.{createImage, createImagesForParts, toPicture}
import com.sparetimedevs.ami.mediaprocessor.util.toEitherErrorsOrMap
import com.sparetimedevs.ami.mediaprocessor.{Errors, IOEitherErrorsOr, ValidationError}
import doodle.core.*
import doodle.effect.Writer.*
import doodle.image.Image
import doodle.java2d.*
import doodle.language.Basic
import doodle.syntax.*
import org.apache.commons.io.FileUtils

import java.io.File
import java.nio.file.{Files, Paths}
import scala.concurrent.ExecutionContext
import scala.util.Try

class MediaProcessorImpl extends MediaProcessor {

  private val xsdPath = "src/main/resources/musicxml_3_1/schema/musicxml.xsd"

  override def createImages(musicXmlData: Array[Byte], outputFileFormat: Format, executionContext: ExecutionContext): IO[Either[Errors, Map[String, Array[Byte]]]] =
    implicit val ec: ExecutionContext = executionContext
    read(musicXmlData, xsdPath)
      .map { (s: ScorePartwise) => getMeasures(s) }
      .flatMap { (measuresForParts: Map[String, Seq[Measure]]) =>
        createImagesForParts(measuresForParts)
      }
      .subflatMap { (imagesForParts: Map[String, Image]) =>
        imagesToBase64s(imagesForParts, outputFileFormat)
      }
      .map { (base64EncodedImagesForParts: Map[String, String]) =>
        base64sToByteArrays(base64EncodedImagesForParts)
      }
      .value

  private def getMeasures(scorePartwise: ScorePartwise): Map[String, Seq[Measure]] =
    scorePartwise.parts.map((part: Part) => { part.id -> part.measures }).toMap

  private def imagesToBase64s(imagesForParts: Map[String, Image], outputFileFormat: Format): Either[Errors, Map[String, String]] =
    imagesForParts.map { (partId: String, image: Image) =>
      val picture: Picture[Unit] = image.toPicture
      val base64EncodedStringOrError: Either[Errors, String] = outputFileFormat match {
        case Format.Svg =>
          Left(ValidationError("SVG is not supported.").asOnlyError)
        case Format.Png =>
          val (result, b64: Base64[Png]) = picture.base64[Png]()
          Right(b64.value)
      }
      (partId, base64EncodedStringOrError)
    }.toEitherErrorsOrMap

  private def base64sToByteArrays(base64EncodedImagesForParts: Map[String, String]): Map[String, Array[Byte]] = {
    base64EncodedImagesForParts.map { (partId: String, base64EncodedString: String) =>
      val decoded: Array[Byte] = java.util.Base64.getDecoder.decode(base64EncodedString)
      (partId, decoded)
    }
  }
}
