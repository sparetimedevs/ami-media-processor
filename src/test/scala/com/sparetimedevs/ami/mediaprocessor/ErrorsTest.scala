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

import com.sparetimedevs.ami.mediaprocessor.{Errors, ValidationError, XmlParseError}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ErrorsTest extends AnyFlatSpec with Matchers {

  it should "list all MediaProcessorError when getAll is invoked" in {
    val errors: Errors = Errors(XmlParseError("XML parse error 1"), List(XmlParseError("XML parse error 2"), XmlParseError("XML parse error 3")))

    val result: List[MediaProcessorError] = errors.getAll

    result should be(List(XmlParseError("XML parse error 1"), XmlParseError("XML parse error 2"), XmlParseError("XML parse error 3")))
  }

  it should "combine two Errors when plus is invoked" in {
    val errorsOne: Errors = Errors(XmlParseError("XML parse error 1"), List(XmlParseError("XML parse error 2"), XmlParseError("XML parse error 3")))
    val errorsTwo: Errors = Errors(ValidationError("validation error 1"), List(ValidationError("validation error 2"), ValidationError("validation error 3")))

    val result: Errors = errorsOne.plus(errorsTwo)

    result should be(
      Errors(
        XmlParseError("XML parse error 1"),
        List(
          XmlParseError("XML parse error 2"),
          XmlParseError("XML parse error 3"),
          ValidationError("validation error 1"),
          ValidationError("validation error 2"),
          ValidationError("validation error 3")
        )
      )
    )
  }
}
