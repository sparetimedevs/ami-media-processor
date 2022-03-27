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

import scala.Array.emptyByteArray

/**
 * An extension method to “cast away” nullability.
 *
 * Method inspired by:
 * https://docs.scala-lang.org/scala3/reference/other-new-features/explicit-nulls.html#working-with-null
 */
extension [T](t: T | Null)
  inline def nullableAsOption: Option[T] =
    if (t != null) Some(t)
    else None

/**
 * An extension method to “cast away” nullability.
 *
 * Method inspired by:
 * https://docs.scala-lang.org/scala3/reference/other-new-features/explicit-nulls.html#working-with-null
 */
extension [A, B](b: B | Null)
  inline def nullableAsEither(a: A): Either[A, B] =
    if (b != null) Right(b)
    else Left(a)
