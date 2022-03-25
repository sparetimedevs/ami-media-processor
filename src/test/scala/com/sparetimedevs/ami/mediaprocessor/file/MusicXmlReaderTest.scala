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

package com.sparetimedevs.ami.mediaprocessor.file

import cats.effect.unsafe.IORuntime
import com.sparetimedevs.ami.core.{AttributesType, Measure, NotImplementedMusicComponent, Note, Part, Pitch, Rest, ScorePartwise, Unpitched}
import com.sparetimedevs.test.util.getRightResultForTest
import doodle.core.*
import doodle.effect.Writer.*
import doodle.image.*
import doodle.image.syntax.*
import doodle.java2d.*
import org.scalatest.flatspec.AnyFlatSpec

import java.io.File
import java.nio.file.{Files, Paths}
import scala.runtime.Scala3RunTime.nn

class MusicXmlReaderTest extends AnyFlatSpec {

  private val xsdPath = "src/main/resources/musicxml_3_1/schema/musicxml.xsd"

  private given runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  it should "work with 0" in {
    val xmlPath = "src/test/resources/lilypond_2_20_regression_musicxml/43a-PianoStaff.xml"
    val musicXmlData: Array[Byte] = Files.readAllBytes(Paths.get(xmlPath)).nn

    val result: ScorePartwise = read(musicXmlData, xsdPath).getRightResultForTest

    val steps: Seq[String] = getNotePitchStep(result)

    val expectedSteps: Seq[String] = List(
      getString("1", "Beats: 4, BeatType: 4"),
      getString("1", "F"),
      getString("1", "not a note"),
      getString("1", "B")
    )

    println(steps.toString())
    assert(steps === expectedSteps)
  }

  it should "work with 1" in {
    val xmlPath = "src/test/resources/lilypond_2_20_regression_musicxml/46c-Midmeasure-Clef.xml"
    val musicXmlData: Array[Byte] = Files.readAllBytes(Paths.get(xmlPath)).nn

    val result: ScorePartwise = read(musicXmlData, xsdPath).getRightResultForTest

    val steps: Seq[String] = getNotePitchStep(result)

    val expectedSteps: Seq[String] = List(
      getString("1", "Beats: 4, BeatType: 4"),
      getString("1", "rest"),
      getString("2", "C"),
      getString("2", "C"),
      getString("2", "not a note"),
      getString("X1", "attributes but no time"),
      getString("X1", "C"),
      getString("X1", "C"),
      getString("3", "C"),
      getString("3", "C"),
      getString("3", "attributes but no time"),
      getString("3", "C"),
      getString("3", "C"),
      getString("3", "not a note")
    )

    println(steps.toString())
    assert(steps === expectedSteps)
  }

  private def getMeasures(scorePartwise: ScorePartwise): Map[String, Seq[Measure]] =
    scorePartwise.parts.map((part: Part) => { part.id -> part.measures }).toMap

  private def getNotePitchStep(scorePartwise: ScorePartwise): Seq[String] =
    scorePartwise.parts.flatMap((part: Part) => {
      part.measures.flatMap((measure: Measure) => {
        measure.musicData.map {
          case note: Note =>
            note.option match {
              case pitch: Pitch => getString(measure.number, pitch.step.toString)
              case _: Rest      => getString(measure.number, "rest")
              case _: Unpitched => getString(measure.number, "unpitched")
            }
          case attributesType: AttributesType => {
            if (attributesType.time.equals(Nil)) then getString(measure.number, "attributes but no time")
            else
              attributesType.time
                .map(time => {
                  time.timeOption.timeSignatures
                    .map(timeSignature => {
                      getString(measure.number, s"Beats: ${timeSignature.beats}, BeatType: ${timeSignature.beatType}")
                    })
                    .mkString(", ")
                })
                .mkString(", ")
          }
          case _: NotImplementedMusicComponent => getString(measure.number, "not a note")
        }
      })
    })

  private def getString(measureNumber: String, note: String) = s"measure: $measureNumber, note: $note"
}
