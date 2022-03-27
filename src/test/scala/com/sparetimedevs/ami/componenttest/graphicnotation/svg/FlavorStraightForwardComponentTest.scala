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

package com.sparetimedevs.ami.componenttest.graphicnotation.svg

import com.commodityvectors.snapshotmatchers.{SnapshotAssertion, SnapshotFilename, SnapshotMatcher}
import com.sparetimedevs.ami.MediaProcessor
import com.sparetimedevs.ami.componenttest.graphicnotation.GraphicNotationComponentSpec
import com.sparetimedevs.ami.componenttest.util.ComponentSpec
import com.sparetimedevs.ami.core.{Measure, Part, ScorePartwise}
import com.sparetimedevs.ami.mediaprocessor.file.Format
import com.sparetimedevs.ami.mediaprocessor.{Errors, ValidationError}
import com.sparetimedevs.test.util.getLeftResultForTest
import org.apache.commons.io.FileUtils
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers

import java.io.File
import java.nio.file.{Files, Paths}
import scala.util.Try

class FlavorStraightForwardComponentTest extends GraphicNotationComponentSpec with Matchers {

  allTestMusicXmlFiles.foreach { musicXmlFile =>
    it should s"match snapshots derived from $musicXmlFile" in { implicit testData: FixtureParam =>
      implicit val matcher: SnapshotMatcher = this

      val xmlPath = s"src/test/resources/lilypond_2_20_regression_musicxml/$musicXmlFile"

      val errorResult: Errors = createImages(xmlPath, Format.Svg).getLeftResultForTest

      errorResult should be(ValidationError("SVG is not supported.").asOnlyError)
    }
  }
}
