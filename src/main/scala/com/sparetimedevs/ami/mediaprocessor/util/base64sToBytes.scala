/*
 * Copyright (c) 2022 sparetimedevs and respective authors and developers.
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

package com.sparetimedevs.ami.mediaprocessor.util

import cats.effect.IO
import cats.effect.implicits.*
import cats.implicits.*
import com.sparetimedevs.ami.core.util.{getMessage, nullableAsEither}
import com.sparetimedevs.ami.mediaprocessor.{DecodeError, Errors, IOEitherErrorsOr, asIOEitherErrorsOrT}

import scala.util.{Either, Try}

private[mediaprocessor] def base64sToBytes(base64EncodedImagesForParts: Map[String, String]): IOEitherErrorsOr[Map[String, Array[Byte]]] =
  base64EncodedImagesForParts.parUnorderedTraverse { base64EncodedString => decode(base64EncodedString) }

private def decode(base64EncodedString: String): IOEitherErrorsOr[Array[Byte]] = {
  val decoded: IO[Either[Errors, Array[Byte]]] = java.util.Base64.getDecoder
    .nullableAsEither(DecodeError("Expected to get a value when getting a decoder, but got none.").asOnlyError) match {
    case Right(decoder) =>
      IO {
        decoder.decode(base64EncodedString).nullableAsEither(DecodeError("Expected to get a value when decoding a base64 encoded string, but got none.").asOnlyError)
      }.handleErrorWith { (t: Throwable) =>
        IO.pure(DecodeError(s"An exception occurred during decoding of a base64 encoded string. The exception message is: ${getMessage(t)}").asOnlyError.asLeft)
      }
    case left @ Left(_) =>
      IO.pure(left.asInstanceOf[Either[Errors, Nothing]])
  }
  decoded.asIOEitherErrorsOrT
}
