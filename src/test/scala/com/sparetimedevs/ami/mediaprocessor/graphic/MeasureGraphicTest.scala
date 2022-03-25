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

package com.sparetimedevs.ami.mediaprocessor.graphic

import cats.Eval
import cats.effect.unsafe.IORuntime
import com.sparetimedevs.ami.core.{Measure, MusicComponent}
import com.sparetimedevs.ami.mediaprocessor.test.getBoundingBox
import com.sparetimedevs.test.util.getRightResultForTest
import doodle.algebra.generic.{ContextTransform, Renderable}
import doodle.algebra.{Algebra, Picture, Size}
import doodle.core.{BoundingBox, Color}
import doodle.effect.Writer
import doodle.image.Image.Elements.Debug
import doodle.image.Image.{Elements, compile}
import doodle.image.{Image, Path}
import doodle.java2d.algebra.Algebra
import doodle.java2d.algebra.reified.Reification
import doodle.java2d.effect.Java2d
import doodle.java2d.{Drawing, Frame}
import doodle.language.Basic
import doodle.syntax.all.circle
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import java.text.Format

class MeasureGraphicTest extends AnyFlatSpec with Matchers {

  private given runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  it should "create one image with the right dimensions for one measure" in {
    val musicData: Seq[MusicComponent] = Nil
    val number: String = "A1"
    val measure: Measure = Measure(musicData = musicData, number = number)

    val result: Image = createImage(measure).getRightResultForTest

    val boundingBox: BoundingBox = result.getBoundingBox

    boundingBox.width should be(600.0)
    boundingBox.height should be(300.0)
  }

  it should "create one image with the right dimensions for two measures" in {
    val musicData1: Seq[MusicComponent] = Nil
    val number1: String = "A1"
    val measure1: Measure = Measure(musicData = musicData1, number = number1)
    val musicData2: Seq[MusicComponent] = Nil
    val number2: String = "A1"
    val measure2: Measure = Measure(musicData = musicData2, number = number2)
    val measures: Seq[Measure] = List(measure1, measure2)

    val result: Image = createImage(measures).getRightResultForTest

    val boundingBox: BoundingBox = result.getBoundingBox

    boundingBox.width should be(1200.0)
    boundingBox.height should be(300.0)
  }

  it should "create one image with the right dimensions for five measures" in {
    val musicData1: Seq[MusicComponent] = Nil
    val number1: String = "A1"
    val measure1: Measure = Measure(musicData = musicData1, number = number1)
    val musicData2: Seq[MusicComponent] = Nil
    val number2: String = "A1"
    val measure2: Measure = Measure(musicData = musicData2, number = number2)
    val musicData3: Seq[MusicComponent] = Nil
    val number3: String = "A3"
    val measure3: Measure = Measure(musicData = musicData3, number = number3)
    val musicData4: Seq[MusicComponent] = Nil
    val number4: String = "A1"
    val measure4: Measure = Measure(musicData = musicData4, number = number4)
    val musicData5: Seq[MusicComponent] = Nil
    val number5: String = "A1"
    val measure5: Measure = Measure(musicData = musicData5, number = number5)
    val measures: Seq[Measure] = List(measure1, measure2, measure3, measure4, measure5)

    val result: Image = createImage(measures).getRightResultForTest

    val boundingBox: BoundingBox = result.getBoundingBox

    boundingBox.width should be(2400.0)
    boundingBox.height should be(600.0)
  }

  it should "create one image with the right dimensions for seven measures" in {
    val musicData1: Seq[MusicComponent] = Nil
    val number1: String = "A1"
    val measure1: Measure = Measure(musicData = musicData1, number = number1)
    val musicData2: Seq[MusicComponent] = Nil
    val number2: String = "A1"
    val measure2: Measure = Measure(musicData = musicData2, number = number2)
    val musicData3: Seq[MusicComponent] = Nil
    val number3: String = "A3"
    val measure3: Measure = Measure(musicData = musicData3, number = number3)
    val musicData4: Seq[MusicComponent] = Nil
    val number4: String = "A1"
    val measure4: Measure = Measure(musicData = musicData4, number = number4)
    val musicData5: Seq[MusicComponent] = Nil
    val number5: String = "A1"
    val measure5: Measure = Measure(musicData = musicData5, number = number5)
    val musicData6: Seq[MusicComponent] = Nil
    val number6: String = "A1"
    val measure6: Measure = Measure(musicData = musicData6, number = number6)
    val musicData7: Seq[MusicComponent] = Nil
    val number7: String = "A1"
    val measure7: Measure = Measure(musicData = musicData7, number = number7)
    val measures: Seq[Measure] = List(measure1, measure2, measure3, measure4, measure5, measure6, measure7)

    val result: Image = createImage(measures).getRightResultForTest

    val boundingBox: BoundingBox = result.getBoundingBox

    boundingBox.width should be(2400.0)
    boundingBox.height should be(600.0)
  }
}
