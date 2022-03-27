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
 * The semitones type is a number representing semitones, used for chromatic
 * alteration. A value of -1 corresponds to a flat and a value of 1 to a sharp.
 * Decimal values like 0.5 (quarter tone sharp) are used for microtones.
 */
opaque type Semitones = Float

object Semitones:
  def apply(value: Float): Semitones = value

  val Default: Semitones = 0.0f
