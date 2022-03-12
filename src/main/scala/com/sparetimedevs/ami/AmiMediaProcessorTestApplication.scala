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

package com.sparetimedevs.ami

import cats.effect.unsafe.IORuntime
import cats.effect.{ExitCode, IO, IOApp}
import com.sparetimedevs.ami.core.util.nullableAsOption
import com.sparetimedevs.ami.mediaprocessor.file.Format
import com.sparetimedevs.ami.mediaprocessor.{Errors, MediaProcessorImpl}

import java.io.{File, InputStream}
import java.nio.file.{Files, Paths}
import scala.Array.emptyByteArray

/**
 * The Ami Media Processor Test Application entry point.
 *
 * The purpose of this test application is to showcase a possible
 * implementation.
 */
object AmiMediaProcessorTestApplication extends IOApp {

  private val xmlPath = "src/test/resources/lilypond_2_20_regression_musicxml/21a-Chord-Basic.xml"
  private val xsdPath = "src/main/resources/musicxml_3_1/schema/musicxml.xsd"

  private given implicitRuntime: IORuntime = this.runtime

  private val musicXmlData: Array[Byte] = Files.readAllBytes(Paths.get(xmlPath)).nullableAsOption match {
    case Some(value) => value
    case None        => emptyByteArray
  }

  private val mediaProcessor: MediaProcessor = new MediaProcessorImpl()

  override def run(args: List[String]): IO[ExitCode] =
    mediaProcessor
      .createImages(musicXmlData, Format.Png)
      .map { either =>
        either.fold(
          { (errors: Errors) =>
            errors.getAll.map { error =>
              println(error.message)
            }
            ExitCode.Error
          },
          { (imagesForParts: Map[String, Array[Byte]]) =>
            imagesForParts.map { (part: String, bytesOfImage: Array[Byte]) =>
              println(s"Part $part has this many bytes: ${bytesOfImage.length}")
            }
            ExitCode.Success
          }
        )
      }
}
