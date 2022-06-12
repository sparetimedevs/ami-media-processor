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

package com.sparetimedevs.ami.test.data

import com.sparetimedevs.ami.core.{Duration, NoteType, Octave, Pitch, Rest, Step}

def createPitch(step: Step, octave: Octave = Octave.Default, noteType: NoteType): Pitch =
  Pitch(
    step = step,
    octave = octave,
    noteType = noteType,
    duration = Duration(noteType.value * 4), // This assumes the denominator of the time signature is 4. For instance a time signature of 4/4.
    isNotePartOfChord = false
  )

def createRest(noteType: NoteType): Rest =
  Rest(
    noteType = noteType,
    duration = Duration(noteType.value * 4) // This assumes the denominator of the time signature is 4. For instance a time signature of 4/4.
  )
