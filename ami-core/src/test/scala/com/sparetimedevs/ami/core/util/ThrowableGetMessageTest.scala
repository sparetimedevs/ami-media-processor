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

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ThrowableGetMessageTest extends AnyFlatSpec with Matchers {

  it should "return a message when Throwable contains a message" in {
    val exception = new ATestExceptionWithMessage("a test message")

    val result: String = getMessage(exception)

    result shouldBe "a test message"
  }

  it should "return a default message when Throwable does not contain a message" in {
    val exception = new ATestExceptionWithoutMessage

    val result: String = getMessage(exception)

    result shouldBe "Throwable ATestExceptionWithoutMessage without message defined."
  }

  private class ATestExceptionWithMessage(message: String) extends Throwable(message)
  private class ATestExceptionWithoutMessage extends Throwable
}
