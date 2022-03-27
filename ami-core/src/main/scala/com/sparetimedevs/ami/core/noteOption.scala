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

package com.sparetimedevs.ami.core

sealed trait NoteOption {
  val noteType: NoteType
  val duration: Duration
  val isNotePartOfChord: Boolean = false
}

case class Pitch(
  step: Step,
  alter: Semitones = Semitones.Default,
  octave: Octave = Octave.Default,
  override val noteType: NoteType,
  override val duration: Duration,
  override val isNotePartOfChord: Boolean
) extends NoteOption

case class Rest(
  override val noteType: NoteType,
  override val duration: Duration
) extends NoteOption

case class Unpitched(
  override val noteType: NoteType,
  override val duration: Duration,
  override val isNotePartOfChord: Boolean
) extends NoteOption
