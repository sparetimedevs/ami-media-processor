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

import cats.data.EitherT
import cats.effect.IO
import cats.effect.implicits.*
import cats.implicits.*
import com.sparetimedevs.ami.core.*
import com.sparetimedevs.ami.mediaprocessor.*
import doodle.core.{Color, *}
import doodle.image.*

import scala.annotation.tailrec

private[mediaprocessor] def createImagesForParts(measuresForParts: Map[String, Seq[Measure]]): IOEitherErrorsOr[Map[String, Image]] =
  measuresForParts
    .parUnorderedTraverse { measures => createImage(measures: Seq[Measure]).value }
    .map { (map: Map[String, Either[Errors, Image]]) =>
      map.toList.traverse({ case (k, either) => either.map(v => (k, v)) }).map { _.toMap }
    }
    .asIOEitherErrorsOrT

private[mediaprocessor] def createImage(measures: Seq[Measure]): IOEitherErrorsOr[Image] = {
  createImages(measures)
    .map((images: Seq[Image]) => groupImagesPerRow(images))
    .map((imagesGroupedPerRow: Seq[Seq[Image]]) => createOneImagePerRow(imagesGroupedPerRow))
    .map((imagesPerRow: Seq[Image]) => createOneImage(imagesPerRow))
}

private[mediaprocessor] def createImages(measures: Seq[Measure]): IOEitherErrorsOr[Seq[Image]] =
  measures.parTraverse((measure: Measure) => EitherT(IO.pure(createImage(measure))))

private[mediaprocessor] def createImage(measure: Measure): Either[Errors, Image] = {
  val measureImage: Image = Image.rectangle(598.0, 298.0).strokeColor(Color.lightGray).strokeWidth(2.0)
  depictMusicData(measure.musicData, measureImage)
}

private[mediaprocessor] def groupImagesPerRow(images: Seq[Image]): Seq[Seq[Image]] = {
  val amountOfImagesInOneRow = 4

  @tailrec
  def groupRemainingImages(acc: Seq[Seq[Image]], images: Seq[Image]): Seq[Seq[Image]] =
    if images.isEmpty then acc
    else {
      val firstImage: Image = images.head
      val newAcc: Seq[Seq[Image]] =
        if acc.isEmpty then List(List(firstImage))
        else if acc.last.size < amountOfImagesInOneRow then acc.updated(acc.size - 1, acc.last ++ List(firstImage))
        else acc.appended(List(firstImage))
      groupRemainingImages(newAcc, images.tail)
    }

  groupRemainingImages(Nil, images)
}

private[mediaprocessor] def createOneImagePerRow(images: Seq[Seq[Image]]): Seq[Image] =
  images.fold(Nil)((acc: Seq[Image], i: Seq[Image]) => {
    val zzz: Image = i.fold(Image.empty)((accImage: Image, image: Image) => {
      accImage beside image
    })
    acc ++ List(zzz)
  })

private[mediaprocessor] def createOneImage(images: Seq[Image]): Image =
  images.fold(Image.empty)((acc: Image, i: Image) => {
    acc above i
  })

private[mediaprocessor] def depictMusicData(musicData: Seq[MusicComponent], image: Image): Either[Errors, Image] = {

  val (notes: Seq[Note], attributesTypes: Seq[AttributesType], notImplementedMusicComponents: Seq[NotImplementedMusicComponent]) = separate(musicData)

  val pathCoordinates: Seq[(Double, Double, Double, Double)] = paths(Nil, notes.map((note: Note) => note.option))
  val pathElements: Seq[PathElement] = toPathElements(pathCoordinates)

  val newImage = Image.openPath(pathElements).at(-298.0, -148.0).strokeWidth(2.0).on(image)

  Right(newImage)
}

private[mediaprocessor] val noMusicData: (List[Note], List[AttributesType], List[NotImplementedMusicComponent]) = (Nil, Nil, Nil)

private[mediaprocessor] def separate(musicData: Seq[MusicComponent]): (List[Note], List[AttributesType], List[NotImplementedMusicComponent]) = {
  musicData.foldRight(noMusicData) { case (component, (notes, attributesTypes, notImplementedMusicComponents)) =>
    component match {
      case note: Note                                                 => (note :: notes, attributesTypes, notImplementedMusicComponents)
      case attributesType: AttributesType                             => (notes, attributesType :: attributesTypes, notImplementedMusicComponents)
      case notImplementedMusicComponent: NotImplementedMusicComponent => (notes, attributesTypes, notImplementedMusicComponent :: notImplementedMusicComponents)
    }
  }
}

@tailrec
private[mediaprocessor] def paths(acc: Seq[(Double, Double, Double, Double)], noteOptions: Seq[NoteOption]): Seq[(Double, Double, Double, Double)] = {
  /* Tuple4[Double, Double, Double, Double] = startX, startY, endX, endY */

  if (noteOptions.isEmpty) return acc
  /* arbitrary offsets, probably should be configurable */
  val offsetX: Double = 20
  val offsetY: Double = 60
  // For now a whole step is 4 a half step is 2
  // This should be a dynamic setting / parameter.
  // For instance, it should also be possible to use 8 for whole; 4 for half. etc.
  val wholeStepExpressedInY: Double = 4

  val noteOption = noteOptions.head
  val (startX: Double, endX: Double) =
    if acc.isEmpty then (offsetX, pathX(noteOption, offsetX))
    else if noteOption.isNotePartOfChord then (acc.last._1, acc.last._3)
    else (acc.last._3, pathX(noteOption, acc.last._3))

  val y = noteOption match {
    case pitch: Pitch         => pathY(pitch, offsetY, wholeStepExpressedInY)
    case rest: Rest           => 30
    case unpitched: Unpitched => 570
  }

  val newAcc = acc ++ List((startX, y, endX, y))

  paths(newAcc, noteOptions.tail)
}

private[mediaprocessor] def pathX(noteOption: NoteOption, offsetX: Double): Double = {
  val noteTypeDistanceValue = noteOption.noteType match {
    case _: NoteType.Maxima.type  => 600 // How big should this be?
    case _: NoteType.Long.type    => 600 // How big should this be?
    case _: NoteType.Breve.type   => 600 // How big should this be?
    case _: NoteType.Whole.type   => 400
    case _: NoteType.Half.type    => 200
    case _: NoteType.Quarter.type => 100
    case _: NoteType.Eighth.type  => 50
    case _: NoteType._16th.type   => 25
    case _: NoteType._32nd.type   => 12.5
    case _: NoteType._64th.type   => 6.25
    case _: NoteType._128th.type  => 3.125
    case _: NoteType._256th.type  => 1.5625
    case _: NoteType._512th.type  => 0.78125
    case _: NoteType._1024th.type => 0.390625
  }
  noteTypeDistanceValue + offsetX
}

private[mediaprocessor] def pathY(pitch: Pitch, offsetY: Double, wholeStepExpressedInY: Double): Double = {
  val y: Double = pitch.step match {
    case Step.A => 1 * wholeStepExpressedInY
    case Step.B => 2 * wholeStepExpressedInY
    case Step.C => 2.5 * wholeStepExpressedInY
    case Step.D => 3.5 * wholeStepExpressedInY
    case Step.E => 4.5 * wholeStepExpressedInY
    case Step.F => 5 * wholeStepExpressedInY
    case Step.G => 6 * wholeStepExpressedInY
  }
  y + offsetY
}

private[mediaprocessor] def toPathElements(pathCoordinates: Seq[(Double, Double, Double, Double)]): Seq[PathElement] =
  pathCoordinates.flatMap(coordinates => { List(PathElement.moveTo(coordinates._1, coordinates._2), PathElement.lineTo(coordinates._3, coordinates._4)) })
