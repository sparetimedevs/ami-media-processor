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

import com.sparetimedevs.ami.core.util.nullableAsOption

def getMessage(t: Throwable): String =
  t.getMessage.nullableAsOption match {
    case Some(t) => t
    case None    => s"Throwable ${t.getClass.getSimpleName} without message defined."
  }
