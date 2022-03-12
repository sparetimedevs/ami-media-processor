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

enum NoteType(val value: Double):
  case Maxima extends NoteType(8)
  case Long extends NoteType(4)
  case Breve extends NoteType(2)
  case Whole extends NoteType(1)
  case Half extends NoteType(0.5)
  case Quarter extends NoteType(0.25)
  case Eighth extends NoteType(0.125)
  case _16th extends NoteType(0.0625)
  case _32nd extends NoteType(0.03125)
  case _64th extends NoteType(0.015625)
  case _128th extends NoteType(0.0078125)
  case _256th extends NoteType(0.00390625)
  case _512th extends NoteType(0.001953125)
  case _1024th extends NoteType(0.0009765625)
