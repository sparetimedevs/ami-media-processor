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

package com.sparetimedevs.ami.mediaprocessor.graphic.vector

import com.sparetimedevs.ami.core.{Duration, Measure, Note, NoteOption, NoteType, Octave, Pitch, Rest, Step}
import com.sparetimedevs.ami.test.data.{createPitch, createRest}
import doodle.image.Image
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NotesAsVectorsTest extends AnyFlatSpec with Matchers {

  it should "create NoteVectors for one note" in {
    val noteOptions: Seq[NoteOption] = Seq(createPitch(Step.C, noteType = NoteType.Whole))

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(NoteVectors(Vector(0.0, 26.5), Vector(400.0, 26.5)))
  }

  it should "create NoteVectors for two notes" in {
    val noteOptions: Seq[NoteOption] = Seq(
      createPitch(Step.C, noteType = NoteType.Half),
      createPitch(Step.A, noteType = NoteType.Half)
    )

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(NoteVectors(Vector(0.0, 26.5), Vector(200.0, 26.5)), NoteVectors(Vector(200.0, 25), Vector(400.0, 25)))
  }

  it should "create NoteVectors for rests" in {
    val noteOptions: Seq[NoteOption] = Seq(
      createPitch(Step.G, noteType = NoteType.Quarter),
      createRest(noteType = NoteType.Quarter),
      createPitch(Step.D, noteType = NoteType.Quarter),
      createRest(noteType = NoteType.Quarter)
    )

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(
      NoteVectors(Vector(0.0, 30.0), Vector(100.0, 30.0)),
      NoteVectors(Vector(100.0, -7.5), Vector(200.0, -7.5)),
      NoteVectors(Vector(200.0, 27.5), Vector(300.0, 27.5)),
      NoteVectors(Vector(300.0, -7.5), Vector(400.0, -7.5))
    )
  }

  it should "create NoteVectors for all note steps" in {
    val noteOptions: Seq[NoteOption] = Seq(
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createPitch(Step.A, noteType = NoteType._1024th),
      createPitch(Step.B, noteType = NoteType._1024th),
      createPitch(Step.C, noteType = NoteType._1024th),
      createPitch(Step.D, noteType = NoteType._1024th),
      createPitch(Step.E, noteType = NoteType._1024th),
      createPitch(Step.F, noteType = NoteType._1024th),
      createPitch(Step.G, noteType = NoteType._1024th),
      createRest(noteType = NoteType._1024th),
      createRest(noteType = NoteType._16th),
      createRest(noteType = NoteType.Eighth),
      createRest(noteType = NoteType.Quarter),
      createRest(noteType = NoteType.Half)
    )

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(
      NoteVectors(Vector(0.0, 25.0), Vector(0.390625, 25.0)),
      NoteVectors(Vector(0.390625, 26.0), Vector(0.78125, 26.0)),
      NoteVectors(Vector(0.78125, 26.5), Vector(1.171875, 26.5)),
      NoteVectors(Vector(1.171875, 27.5), Vector(1.5625, 27.5)),
      NoteVectors(Vector(1.5625, 28.5), Vector(1.953125, 28.5)),
      NoteVectors(Vector(1.953125, 29.0), Vector(2.34375, 29.0)),
      NoteVectors(Vector(2.34375, 30.0), Vector(2.734375, 30.0)),
      NoteVectors(Vector(2.734375, -7.5), Vector(3.125, -7.5)),
      NoteVectors(Vector(3.125, 25.0), Vector(3.515625, 25.0)),
      NoteVectors(Vector(3.515625, 26.0), Vector(3.90625, 26.0)),
      NoteVectors(Vector(3.90625, 26.5), Vector(4.296875, 26.5)),
      NoteVectors(Vector(4.296875, 27.5), Vector(4.6875, 27.5)),
      NoteVectors(Vector(4.6875, 28.5), Vector(5.078125, 28.5)),
      NoteVectors(Vector(5.078125, 29.0), Vector(5.46875, 29.0)),
      NoteVectors(Vector(5.46875, 30.0), Vector(5.859375, 30.0)),
      NoteVectors(Vector(5.859375, -7.5), Vector(6.25, -7.5)),
      NoteVectors(Vector(6.25, 25.0), Vector(6.640625, 25.0)),
      NoteVectors(Vector(6.640625, 26.0), Vector(7.03125, 26.0)),
      NoteVectors(Vector(7.03125, 26.5), Vector(7.421875, 26.5)),
      NoteVectors(Vector(7.421875, 27.5), Vector(7.8125, 27.5)),
      NoteVectors(Vector(7.8125, 28.5), Vector(8.203125, 28.5)),
      NoteVectors(Vector(8.203125, 29.0), Vector(8.59375, 29.0)),
      NoteVectors(Vector(8.59375, 30.0), Vector(8.984375, 30.0)),
      NoteVectors(Vector(8.984375, -7.5), Vector(9.375, -7.5)),
      NoteVectors(Vector(9.375, 25.0), Vector(9.765625, 25.0)),
      NoteVectors(Vector(9.765625, 26.0), Vector(10.15625, 26.0)),
      NoteVectors(Vector(10.15625, 26.5), Vector(10.546875, 26.5)),
      NoteVectors(Vector(10.546875, 27.5), Vector(10.9375, 27.5)),
      NoteVectors(Vector(10.9375, 28.5), Vector(11.328125, 28.5)),
      NoteVectors(Vector(11.328125, 29.0), Vector(11.71875, 29.0)),
      NoteVectors(Vector(11.71875, 30.0), Vector(12.109375, 30.0)),
      NoteVectors(Vector(12.109375, -7.5), Vector(12.5, -7.5)),
      NoteVectors(Vector(12.5, 25.0), Vector(12.890625, 25.0)),
      NoteVectors(Vector(12.890625, 26.0), Vector(13.28125, 26.0)),
      NoteVectors(Vector(13.28125, 26.5), Vector(13.671875, 26.5)),
      NoteVectors(Vector(13.671875, 27.5), Vector(14.0625, 27.5)),
      NoteVectors(Vector(14.0625, 28.5), Vector(14.453125, 28.5)),
      NoteVectors(Vector(14.453125, 29.0), Vector(14.84375, 29.0)),
      NoteVectors(Vector(14.84375, 30.0), Vector(15.234375, 30.0)),
      NoteVectors(Vector(15.234375, -7.5), Vector(15.625, -7.5)),
      NoteVectors(Vector(15.625, 25.0), Vector(16.015625, 25.0)),
      NoteVectors(Vector(16.015625, 26.0), Vector(16.40625, 26.0)),
      NoteVectors(Vector(16.40625, 26.5), Vector(16.796875, 26.5)),
      NoteVectors(Vector(16.796875, 27.5), Vector(17.1875, 27.5)),
      NoteVectors(Vector(17.1875, 28.5), Vector(17.578125, 28.5)),
      NoteVectors(Vector(17.578125, 29.0), Vector(17.96875, 29.0)),
      NoteVectors(Vector(17.96875, 30.0), Vector(18.359375, 30.0)),
      NoteVectors(Vector(18.359375, -7.5), Vector(18.75, -7.5)),
      NoteVectors(Vector(18.75, 25.0), Vector(19.140625, 25.0)),
      NoteVectors(Vector(19.140625, 26.0), Vector(19.53125, 26.0)),
      NoteVectors(Vector(19.53125, 26.5), Vector(19.921875, 26.5)),
      NoteVectors(Vector(19.921875, 27.5), Vector(20.3125, 27.5)),
      NoteVectors(Vector(20.3125, 28.5), Vector(20.703125, 28.5)),
      NoteVectors(Vector(20.703125, 29.0), Vector(21.09375, 29.0)),
      NoteVectors(Vector(21.09375, 30.0), Vector(21.484375, 30.0)),
      NoteVectors(Vector(21.484375, -7.5), Vector(21.875, -7.5)),
      NoteVectors(Vector(21.875, 25.0), Vector(22.265625, 25.0)),
      NoteVectors(Vector(22.265625, 26.0), Vector(22.65625, 26.0)),
      NoteVectors(Vector(22.65625, 26.5), Vector(23.046875, 26.5)),
      NoteVectors(Vector(23.046875, 27.5), Vector(23.4375, 27.5)),
      NoteVectors(Vector(23.4375, 28.5), Vector(23.828125, 28.5)),
      NoteVectors(Vector(23.828125, 29.0), Vector(24.21875, 29.0)),
      NoteVectors(Vector(24.21875, 30.0), Vector(24.609375, 30.0)),
      NoteVectors(Vector(24.609375, -7.5), Vector(25.0, -7.5)),
      NoteVectors(Vector(25.0, -7.5), Vector(50.0, -7.5)),
      NoteVectors(Vector(50.0, -7.5), Vector(100.0, -7.5)),
      NoteVectors(Vector(100.0, -7.5), Vector(200.0, -7.5)),
      NoteVectors(Vector(200.0, -7.5), Vector(400.0, -7.5))
    )
  }

  it should "create NoteVectors for all note steps in C ionian mode (C major)" in {
    val noteOptions: Seq[NoteOption] = Seq(
      createPitch(Step.C, noteType = NoteType.Eighth),
      createPitch(Step.D, noteType = NoteType.Eighth),
      createPitch(Step.E, noteType = NoteType.Eighth),
      createPitch(Step.F, noteType = NoteType.Eighth),
      createPitch(Step.G, noteType = NoteType.Eighth),
      createPitch(Step.A, octave = Octave(5), noteType = NoteType.Eighth),
      createPitch(Step.B, octave = Octave(5), noteType = NoteType.Eighth),
      createPitch(Step.C, octave = Octave(5), noteType = NoteType.Eighth)
    )

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(
      NoteVectors(Vector(0.0, 26.5), Vector(50.0, 26.5)),
      NoteVectors(Vector(50.0, 27.5), Vector(100.0, 27.5)),
      NoteVectors(Vector(100.0, 28.5), Vector(150.0, 28.5)),
      NoteVectors(Vector(150.0, 29.0), Vector(200.0, 29.0)),
      NoteVectors(Vector(200.0, 30.0), Vector(250.0, 30.0)),
      NoteVectors(Vector(250.0, 31.0), Vector(300.0, 31.0)),
      NoteVectors(Vector(300.0, 32.0), Vector(350.0, 32.0)),
      NoteVectors(Vector(350.0, 32.5), Vector(400.0, 32.5))
    )
  }

  it should "create NoteVectors for all note steps in C dorian mode" in {
    val noteOptions: Seq[NoteOption] = Seq(
      createPitch(Step.C, noteType = NoteType.Eighth),
      createPitch(Step.D, noteType = NoteType.Eighth),
      createPitch(Step.EFlat, noteType = NoteType.Eighth),
      createPitch(Step.F, noteType = NoteType.Eighth),
      createPitch(Step.G, noteType = NoteType.Eighth),
      createPitch(Step.A, octave = Octave(5), noteType = NoteType.Eighth),
      createPitch(Step.BFlat, octave = Octave(5), noteType = NoteType.Eighth),
      createPitch(Step.C, octave = Octave(5), noteType = NoteType.Eighth)
    )

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(
      NoteVectors(Vector(0.0, 26.5), Vector(50.0, 26.5)),
      NoteVectors(Vector(50.0, 27.5), Vector(100.0, 27.5)),
      NoteVectors(Vector(100.0, 28.0), Vector(150.0, 28.0)),
      NoteVectors(Vector(150.0, 29.0), Vector(200.0, 29.0)),
      NoteVectors(Vector(200.0, 30.0), Vector(250.0, 30.0)),
      NoteVectors(Vector(250.0, 31.0), Vector(300.0, 31.0)),
      NoteVectors(Vector(300.0, 31.5), Vector(350.0, 31.5)),
      NoteVectors(Vector(350.0, 32.5), Vector(400.0, 32.5))
    )
  }

  it should "create NoteVectors for chords" in {
    val noteOptions: Seq[NoteOption] = Seq(
      // G major
      Pitch(Step.G, noteType = NoteType.Quarter, duration = Duration(1), isNotePartOfChord = false),
      Pitch(Step.B, noteType = NoteType.Quarter, duration = Duration(1), isNotePartOfChord = true),
      Pitch(Step.D, noteType = NoteType.Quarter, duration = Duration(1), isNotePartOfChord = true),
      createRest(noteType = NoteType.Quarter),
      // D major
      Pitch(Step.D, noteType = NoteType.Quarter, duration = Duration(1), isNotePartOfChord = false),
      // Pitch(Step.FSharp, noteType = NoteType.Quarter, duration = Duration(1), isNotePartOfChord = true),
      Pitch(Step.A, noteType = NoteType.Quarter, duration = Duration(1), isNotePartOfChord = true),
      createRest(noteType = NoteType.Quarter)
    )

    val result: Seq[NoteVectors] = noteOptions.asNotesVectors

    result shouldBe Seq(
      NoteVectors(Vector(0.0, 30.0), Vector(100.0, 30.0)),
      NoteVectors(Vector(0.0, 26.0), Vector(100.0, 26.0)),
      NoteVectors(Vector(0.0, 27.5), Vector(100.0, 27.5)),
      NoteVectors(Vector(100.0, -7.5), Vector(200.0, -7.5)),
      NoteVectors(Vector(200.0, 27.5), Vector(300.0, 27.5)),
      NoteVectors(Vector(200.0, 25.0), Vector(300.0, 25.0)),
      NoteVectors(Vector(300.0, -7.5), Vector(400.0, -7.5))
    )
  }
}
