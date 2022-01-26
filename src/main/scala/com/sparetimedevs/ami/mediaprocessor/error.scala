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

package com.sparetimedevs.ami.mediaprocessor

case class Errors(head: MediaProcessorError, tail: List[MediaProcessorError]) {

  def getAll: List[MediaProcessorError] = head :: tail

  def plus(other: Errors): Errors = Errors(this.head, this.tail ++ other.getAll)

}

sealed trait MediaProcessorError {

  val message: String

  def asOnlyError: Errors = Errors(this, Nil)

}

case class ValidationError(message: String) extends MediaProcessorError

case class XmlParseError(message: String) extends MediaProcessorError

case class DecodeError(message: String) extends MediaProcessorError
