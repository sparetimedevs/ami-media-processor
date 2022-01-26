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
  id: Option[String] = None,
  option: NoteOption,
  printLeger: YesNo = YesNo.No,
  dynamics: Option[BigDecimal] = None,
  endDynamics: Option[BigDecimal] = None,
  attack: Option[BigDecimal] = None,
  release: Option[BigDecimal] = None,
  timeOnly: Option[String] = None,
  pizzicato: YesNo = YesNo.No,
  color: Option[String] = None,
  fontFamily: Option[String] = None,
  fontStyle: Option[FontStyle] = None,
  fontSize: Option[String] = None,
  fontWeight: Option[FontWeight] = None,
  printDot: YesNo = YesNo.No,
  printLyric: YesNo = YesNo.No,
  printObject: YesNo = YesNo.No,
  printSpacing: YesNo = YesNo.No,
  defaultX: Option[BigDecimal] = None,
  defaultY: Option[BigDecimal] = None,
  relativeX: Option[BigDecimal] = None,
  relativeY: Option[BigDecimal] = None
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
  measureStyle: Seq[MeasureStyle] = Nil
) extends MusicComponent
