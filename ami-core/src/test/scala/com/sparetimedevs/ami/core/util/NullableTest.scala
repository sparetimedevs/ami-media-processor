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

package com.sparetimedevs.ami.core.util

import com.sparetimedevs.test.util.{getLeftResultForTest, getRightResultForTest}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

import scala.runtime.Scala3RunTime.nn

class NullableTest extends AnyFunSpec with Matchers {

  describe("nullableAsOption") {
    it("should return Some value when x is not null") {
      val x: ATestType | Null = new ATestType

      val result: Option[ATestType] = x.nullableAsOption

      result.orNull shouldBe x.nn
    }

    it("should return None when x is null") {
      val x: ATestType | Null = null

      val result: Option[ATestType] = x.nullableAsOption

      result shouldBe None
    }
  }

  describe("nullableAsEither") {
    it("should return Some value when x is not null") {
      val x: ATestType | Null = new ATestType

      val result: Either[TestErrorType, ATestType] = x.nullableAsEither(new TestErrorType)

      result.getRightResultForTest shouldBe x.nn
    }

    it("should return None when x is null") {
      val x: ATestType | Null = null
      val y: TestErrorType = new TestErrorType

      val result: Either[TestErrorType, ATestType] = x.nullableAsEither(y)

      result.getLeftResultForTest shouldBe y
    }
  }

  private class ATestType
  private class TestErrorType
}
