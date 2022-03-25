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

extension [A, B](either: Either[A, B]) def getRightResultForTest: B = either.getOrElse(throw new RuntimeException("Test case should yield a Right!"))
extension [A, B](either: Either[A, B]) def getLeftResultForTest: A = either.swap.getOrElse(throw new RuntimeException("Test case should yield a Left!"))

extension [A, B](e: EitherT[IO, A, B])
  def getRightResultForTest(implicit runtime: IORuntime): B = e.value.unsafeRunSync()(runtime).getOrElse(throw new RuntimeException("Test case should yield a Right!"))
extension [A, B](e: EitherT[IO, A, B])
  def getLeftResultForTest(implicit runtime: IORuntime): A = e.value.unsafeRunSync()(runtime).swap.getOrElse(throw new RuntimeException("Test case should yield a Left!"))
