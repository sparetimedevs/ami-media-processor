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

package com.sparetimedevs.ami.mediaprocessor.util

import com.sparetimedevs.ami.mediaprocessor.{Errors, ValidationError, XmlParseError}
import com.sparetimedevs.ami.test.util.{getLeftResultForTest, getRightResultForTest}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapWithEitherValuesTest extends AnyFlatSpec with Matchers {

  it should "return a Right containing a Map when invoked on a Map with no Left values" in {

    val testMap: Map[String, Either[Errors, String]] =
      Map(
        ("key1", Right("value1")),
        ("key2", Right("value2")),
        ("key3", Right("value3")),
        ("key4", Right("value4")),
        ("key5", Right("value5"))
      )

    val result: Map[String, String] = testMap.toEitherErrorsOrMap.getRightResultForTest

    result.size should be(5)
    result should contain("key1", "value1")
    result should contain("key2", "value2")
    result should contain("key3", "value3")
    result should contain("key4", "value4")
    result should contain("key5", "value5")
  }

  it should "return a Left containing Errors when invoked on a Map with one or more Left values" in {

    val testMap: Map[String, Either[Errors, String]] =
      Map(
        ("key1", Right("value1")),
        ("key2", Right("value2")),
        ("key3", Left(XmlParseError("XML parse error 1").asOnlyError)),
        ("key4", Left(ValidationError("validation error 1").asOnlyError)),
        ("key5", Right("value5"))
      )

    val result: Errors = testMap.toEitherErrorsOrMap.getLeftResultForTest

    result.getAll.size should be(2)
    result.getAll should contain(XmlParseError("XML parse error 1"))
    result.getAll should contain(ValidationError("validation error 1"))
  }

  it should "return a Left containing Errors when invoked on a Map with one or more Left values each containing Errors which contain more than one MediaProcessorError" in {

    val testMap: Map[String, Either[Errors, String]] =
      Map(
        ("key1", Right("value1")),
        ("key2", Right("value2")),
        ("key3", Left(Errors(XmlParseError("XML parse error 1"), List(XmlParseError("XML parse error 2"), XmlParseError("XML parse error 3"))))),
        ("key4", Left(Errors(ValidationError("validation error 1"), List(ValidationError("validation error 2"), ValidationError("validation error 3"))))),
        ("key5", Right("value5"))
      )

    val result: Errors = testMap.toEitherErrorsOrMap.getLeftResultForTest

    result.getAll.size should be(6)
    result.getAll should contain(XmlParseError("XML parse error 1"))
    result.getAll should contain(XmlParseError("XML parse error 2"))
    result.getAll should contain(XmlParseError("XML parse error 3"))
    result.getAll should contain(ValidationError("validation error 1"))
    result.getAll should contain(ValidationError("validation error 2"))
    result.getAll should contain(ValidationError("validation error 3"))
  }
}
