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

import com.sparetimedevs.ami.mediaprocessor.{Errors, ValidationError}

extension [A](map: Map[String, Either[Errors, A]])
  private[mediaprocessor] def toEitherErrorsOrMap: Either[Errors, Map[String, A]] =
    map.partition((a: String, b: Either[Errors, A]) => b.isLeft) match {
      case (es: Map[String, Either[Errors, A]], as: Map[String, Either[Errors, A]]) if es.isEmpty =>
        Right(for ((string, Right(a)) <- as) yield (string, a))
      case (es: Map[String, Either[Errors, A]], _: Map[String, Either[Errors, A]]) =>
        val errors: Errors = (for ((string, Left(errors: Errors)) <- es) yield errors)
          .reduce { (acc, i) => acc.plus(i) }
        Left(errors)
    }
