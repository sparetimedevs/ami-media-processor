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

package com.sparetimedevs.ami.mediaprocessor.test

import doodle.algebra.Picture
import doodle.algebra.generic.Renderable
import doodle.core.BoundingBox
import doodle.image.Image
import doodle.java2d.Drawing
import doodle.java2d.algebra.Algebra
import doodle.java2d.algebra.reified.Reification
import doodle.java2d.effect.Java2d
import doodle.language.Basic
import com.sparetimedevs.ami.mediaprocessor.graphic.toPicture

import java.awt.Graphics2D
import java.awt.image.BufferedImage

extension (image: Image)
  def getBoundingBox: BoundingBox =
    val bufferedImage: BufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB)
    val graphics2D: Graphics2D = bufferedImage.createGraphics()
    val newGraphics2D: Graphics2D = Java2d.setup(graphics2D)
    implicit val algebra: Algebra = Algebra(newGraphics2D)
    val drawing: Drawing[Unit] = image.toPicture.apply
    val (boundingBox: BoundingBox, renderable: Renderable[Reification, Unit]) = drawing.runA(List.empty).value
    boundingBox
