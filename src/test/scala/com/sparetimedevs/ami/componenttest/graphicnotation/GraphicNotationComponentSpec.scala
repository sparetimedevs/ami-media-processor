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

package com.sparetimedevs.ami.componenttest.graphicnotation

import cats.effect.unsafe.IORuntime
import com.commodityvectors.snapshotmatchers.{SnapshotAssertion, SnapshotFilename, SnapshotMatcher}
import com.sparetimedevs.ami.MediaProcessor
import com.sparetimedevs.ami.componenttest.util.ComponentSpec
import com.sparetimedevs.ami.core.{Measure, Part, ScorePartwise}
import com.sparetimedevs.ami.mediaprocessor.file.Format
import com.sparetimedevs.ami.mediaprocessor.{Errors, MediaProcessorImpl}
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach

import java.io.File
import java.nio.file.{Files, Paths}
import scala.runtime.Scala3RunTime.nn
import scala.util.Try

abstract class GraphicNotationComponentSpec extends ComponentSpec {

  private val mediaProcessor: MediaProcessor = new MediaProcessorImpl()
  private given runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  def createImages(xmlPath: String, outputFileFormat: Format): Either[Errors, List[String]] =
    val musicXmlData: Array[Byte] = Files.readAllBytes(Paths.get(xmlPath)).nn
    mediaProcessor
      .createImages(musicXmlData, outputFileFormat)
      .unsafeRunSync()
      .map { (images: Map[String, Array[Byte]]) =>
        images.map { (partId: String, imageData: Array[Byte]) =>
          val filePrefix = getFilePrefix(xmlPath)
          val filename = s"$filePrefix-$partId.png"
          val filePath = SnapshotFilename.getResultFilename(filename)
          makeSureDirectoriesExists(filePath)
          Files.write(Paths.get(filePath), imageData)
          filename
        }.toList
      }

  private def getFilePrefix(xmlPath: String): String =
    val indexOfLastSlash = xmlPath.lastIndexOf('/')
    val indexOfLastDot = xmlPath.lastIndexOf('.')
    xmlPath.substring(indexOfLastSlash + 1, indexOfLastDot).nn

  private def makeSureDirectoriesExists(filePath: String): Unit =
    val outputFile = new File(filePath)
    outputFile.getParentFile.nn.mkdirs()
}
