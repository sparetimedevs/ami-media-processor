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

package componenttest.util

import com.commodityvectors.snapshotmatchers.SnapshotMatcher
import org.apache.commons.io.FileUtils
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, OneInstancePerTest}

import java.io.File
import scala.util.Try

trait ComponentSpec extends FixtureAnyFlatSpec with SnapshotMatcher with BeforeAndAfterAll with OneInstancePerTest {

  override def beforeAll(): Unit = cleanupTestOutput()

  private def cleanupTestOutput(): Unit = Try(FileUtils.deleteDirectory(new File("target/test-output")))

  protected val allTestMusicXmlFiles: Seq[String] = Seq(
    "43a-PianoStaff.xml",
    "46c-Midmeasure-Clef.xml",
    "21b-Chords-TwoNotes.xml"
  )
}
