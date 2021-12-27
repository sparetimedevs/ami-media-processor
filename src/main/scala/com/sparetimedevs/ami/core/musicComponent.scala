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

sealed trait MusicComponent

case class NotImplementedMusicComponent() extends MusicComponent {}

case class Note(
  id: String = null,
  option: NoteOption,
  printLeger: YesNo = YesNo.No,
  dynamics: BigDecimal = null,
  endDynamics: BigDecimal = null,
  attack: BigDecimal = null,
  release: BigDecimal = null,
  timeOnly: String = null,
  pizzicato: YesNo = YesNo.No,
  color: String = null,
  fontFamily: String = null,
  fontStyle: FontStyle = null,
  fontSize: String = null,
  fontWeight: FontWeight = null,
  printDot: YesNo = YesNo.No,
  printLyric: YesNo = YesNo.No,
  printObject: YesNo = YesNo.No,
  printSpacing: YesNo = YesNo.No,
  defaultX: BigDecimal = null,
  defaultY: BigDecimal = null,
  relativeX: BigDecimal = null,
  relativeY: BigDecimal = null
) extends MusicComponent

case class AttributesType(
  editorialSequence: EditorialSequence,
  divisions: Option[Divisions] = None,
  key: Seq[Key] = Nil,
  time: Seq[Time] = Nil,
  staves: Option[Staves] = None,
  partSymbol: Option[PartSymbol] = None,
  instruments: Option[Instruments] = None,
  clef: Seq[Clef] = Nil,
  staffDetails: Seq[StaffDetails] = Nil,
  transpose: Seq[Transpose] = Nil,
  directive: Seq[Directive] = Nil,
  measureuStyle: Seq[MeasureStyle] = Nil
) extends MusicComponent
