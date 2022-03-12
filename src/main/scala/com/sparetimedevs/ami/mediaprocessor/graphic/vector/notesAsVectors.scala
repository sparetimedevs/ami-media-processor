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

import com.sparetimedevs.ami.core.{Note, NoteOption, NoteType, Pitch, Rest, Step, Unpitched}
import com.sparetimedevs.ami.mediaprocessor.graphic.vector.Vector
import com.sparetimedevs.ami.mediaprocessor.graphic.{GraphicProperties, XProperties, YProperties}

import scala.annotation.tailrec

extension (noteOptions: Seq[NoteOption])
  private[graphic] def asNotesVectors: Seq[NoteVectors] =
    vectors(Nil, noteOptions)

@tailrec
private[graphic] def vectors(acc: Seq[NoteVectors], noteOptions: Seq[NoteOption]): Seq[NoteVectors] = {
  if (noteOptions.isEmpty) return acc

  val noteOption = noteOptions.head
  val (startX: Double, endX: Double) =
    if acc.isEmpty then (0d, vectorComponentXAtEnd(noteOption, 0d))
    else if noteOption.isNotePartOfChord then (acc.last.start.x, acc.last.end.x)
    else (acc.last.end.x, vectorComponentXAtEnd(noteOption, acc.last.end.x))

  val y = noteOption match {
    case pitch: Pitch         => vectorComponentY(pitch)
    case rest: Rest           => -7.5d
    case unpitched: Unpitched => 570d
  }

  val newAcc = acc ++ List(NoteVectors(Vector(startX, y), Vector(endX, y)))

  vectors(newAcc, noteOptions.tail)
}

private[graphic] def vectorComponentXAtEnd(noteOption: NoteOption, currentX: Double): Double =
  currentX + (noteOption.noteType.value * 400)

private[graphic] def vectorComponentY(pitch: Pitch): Double =
  pitch.step match {
    case Step.A => 1
    case Step.B => 2
    case Step.C => 2.5
    case Step.D => 3.5
    case Step.E => 4.5
    case Step.F => 5
    case Step.G => 6
  }
