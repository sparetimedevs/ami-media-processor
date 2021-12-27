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

/**
 * Time signatures are represented by the beats element for the numerator and
 * the beat-type element for the denominator. The symbol attribute is used
 * indicate common and cut time symbols as well as a single number display.
 * Multiple pairs of beat and beat-type elements are used for composite time
 * signatures with multiple denominators, such as 2/4 + 3/8. A composite such as
 * 3+2/8 requires only one beat/beat-type pair. The print-object attribute
 * allows a time signature to be specified but not printed, as is the case for
 * excerpts from the middle of a score. The value is "yes" if not present. The
 * optional number attribute refers to staff numbers within the part. If absent,
 * the time signature applies to all staves in the part.
 */
case class Time(
  id: String = null,
  timeOption: TimeOption,
  attributes: Seq[TimeAttributes] = Nil,
  number: BigInt = null,
  symbol: TimeSymbol = null,
  separator: TimeSeparator = null,
  defaultX: DefaultX = null,
  defaultY: DefaultY = null,
  relativeX: RelativeX = null,
  relativeY: RelativeY = null,
  fontFamily: FontFamily = null,
  fontStyle: FontStyle = null,
  fontSize: FontSize = null,
  fontWeight: FontWeight = null,
  color: Color = null,
  horizontalAlign: HorizontalAlign = null,
  verticalAlign: VerticalAlign = null,
  printObject: YesNo = YesNo.No
)
