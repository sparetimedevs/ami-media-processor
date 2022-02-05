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
import com.sparetimedevs.ami.mediaprocessor.graphic.vector.{NoteVectors, asNotesVectors}
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

  val graphicProperties: GraphicProperties = getGraphicProperties

  val notesVectors: Seq[NoteVectors] = notes.map((note: Note) => note.option).asNotesVectors
  val pathElements: Seq[PathElement] = toPathElements(notesVectors, graphicProperties)

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

private[mediaprocessor] def getGraphicProperties = {
  /* Arbitrary values, probably should be configurable. */
  val offsetX: Double = 20
  val cutOffXToReflectNoteIsEnding = 8
  val offsetY: Double = 60
  val wholeStepExpressedInY: Double = 4 // For now a whole step is 4 and a half step is 2. It should also be possible to use 8 for whole; 4 for half. etc.
  val xProperties = XProperties(offsetX, cutOffXToReflectNoteIsEnding)
  val yProperties = YProperties(offsetY, wholeStepExpressedInY)
  GraphicProperties(xProperties, yProperties)
}

private[mediaprocessor] def toPathElements(notesVectors: Seq[NoteVectors], graphicProperties: GraphicProperties): Seq[PathElement] =
  notesVectors.flatMap { vectors =>
    val startX: Double = vectors.start.x + graphicProperties.xProperties.offsetX
    val startY: Double = (vectors.start.y * graphicProperties.yProperties.wholeStepExpressedInY) + graphicProperties.yProperties.offsetY
    val endXWithCutOff: Double = vectors.end.x + graphicProperties.xProperties.offsetX - graphicProperties.xProperties.cutOffXToReflectNoteIsEnding
    val endY: Double = (vectors.end.y * graphicProperties.yProperties.wholeStepExpressedInY) + graphicProperties.yProperties.offsetY
    List(PathElement.moveTo(startX, startY), PathElement.lineTo(endXWithCutOff, endY))
  }
