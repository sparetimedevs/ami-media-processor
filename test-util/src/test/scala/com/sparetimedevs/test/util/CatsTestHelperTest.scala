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

package com.sparetimedevs.test.util

import cats.data.EitherT
import cats.effect.IO
import cats.effect.unsafe.IORuntime
import com.sparetimedevs.test.util.{getLeftResultForTest, getRightResultForTest}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class CatsTestHelperTest extends AnyFunSpec with Matchers {

  private given runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  describe("getRightResultForTest on Either[A, B]") {
    it("should return the value when right contains a value") {
      val x: ATestType = new ATestType
      val right: Either[TestErrorType, ATestType] = Right(x)

      val result: ATestType = right.getRightResultForTest

      result shouldBe x
    }

    it("should throw an Exception when right does not contain a value") {
      val left: Either[TestErrorType, ATestType] = Left(new TestErrorType)

      assertThrows[RuntimeException] {
        left.getRightResultForTest
      }
    }
  }

  describe("getLeftResultForTest on Either[A, B]") {
    it("should return the value when left contains a value") {
      val x: TestErrorType = new TestErrorType
      val left: Either[TestErrorType, ATestType] = Left(x)

      val result: TestErrorType = left.getLeftResultForTest

      result shouldBe x
    }

    it("should throw an Exception when left does not contain a value") {
      val right: Either[TestErrorType, ATestType] = Right(new ATestType)

      assertThrows[RuntimeException] {
        right.getLeftResultForTest
      }
    }
  }

  describe("getRightResultForTest on EitherT[IO, A, B]") {
    it("should return the value when right contains a value") {
      val x: ATestType = new ATestType
      val right: EitherT[IO, TestErrorType, ATestType] = EitherT(IO.pure(Right(x)))

      val result: ATestType = right.getRightResultForTest

      result shouldBe x
    }

    it("should throw an Exception when right does not contain a value") {
      val left: EitherT[IO, TestErrorType, ATestType] = EitherT(IO.pure(Left(new TestErrorType)))

      assertThrows[RuntimeException] {
        left.getRightResultForTest
      }
    }
  }

  describe("getLeftResultForTest on EitherT[IO, A, B]") {
    it("should return the value when left contains a value") {
      val x: TestErrorType = new TestErrorType
      val left: EitherT[IO, TestErrorType, ATestType] = EitherT(IO.pure(Left(x)))

      val result: TestErrorType = left.getLeftResultForTest

      result shouldBe x
    }

    it("should throw an Exception when left does not contain a value") {
      val right: EitherT[IO, TestErrorType, ATestType] = EitherT(IO.pure(Right(new ATestType)))

      assertThrows[RuntimeException] {
        right.getLeftResultForTest
      }
    }
  }

  private class ATestType
  private class TestErrorType
}
