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

package com.sparetimedevs.ami.mediaprocessor.graphic

import cats.effect.unsafe.IORuntime
import com.sparetimedevs.ami.core.{Duration, Measure, Note, NoteType, Pitch, Step}
import com.sparetimedevs.test.util.getRightResultForTest
import doodle.image.Image
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GraphicsForPartsTest extends AnyFlatSpec with Matchers {

  private given runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  it should "create images for parts" in {
    val measuresForParts = Map(
      "part 1" -> Seq(
        Measure(musicData = Seq(Note(option = Pitch(Step.C, noteType = NoteType.Half, duration = Duration(2), isNotePartOfChord = false))), number = "A1"),
        Measure(musicData = Seq(Note(option = Pitch(Step.C, noteType = NoteType.Half, duration = Duration(2), isNotePartOfChord = false))), number = "A1")
      ),
      "part 2" -> Seq(
        Measure(musicData = Seq(Note(option = Pitch(Step.C, noteType = NoteType.Half, duration = Duration(2), isNotePartOfChord = false))), number = "A1"),
        Measure(musicData = Seq(Note(option = Pitch(Step.D, noteType = NoteType.Quarter, duration = Duration(2), isNotePartOfChord = false))), number = "A1"),
        Measure(musicData = Seq(Note(option = Pitch(Step.A, noteType = NoteType.Quarter, duration = Duration(2), isNotePartOfChord = false))), number = "A1")
      )
    )

    val result: Map[String, Image] = createImagesForParts(measuresForParts: Map[String, Seq[Measure]]).getRightResultForTest

    result("part 1") shouldBe a[Image]
    result("part 2") shouldBe a[Image]
  }
}
