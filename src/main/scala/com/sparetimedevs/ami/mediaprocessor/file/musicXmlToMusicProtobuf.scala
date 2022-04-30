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

import com.sparetimedevs.ami.core.{AttributesType, BeatType, Beats, Duration, EditorialSequence, Measure, MusicComponent, NotImplementedMusicComponent, Note, NoteOption, NoteType, Pitch, Rest, Step, Time, TimeOption, TimeSignature, Unpitched}
import com.sparetimedevs.ami.musicpb.score_partwise
import com.sparetimedevs.ami.musicpb.score_partwise.{Part, ScorePartwise}

// TODO create a similar implementation to xmlModelToDomainModel file.

private[mediaprocessor] def toMusicProtobuf(scorePartwiseXml: musicxml.Scoreu45partwise): ScorePartwise = {
  val parts: Seq[score_partwise.Part] = scorePartwiseXml.part.map((partXml: musicxml.Part) => toMusicProtobuf(partXml))
  score_partwise.ScorePartwise.of(parts = parts)
}

private def toMusicProtobuf(partXml: musicxml.Part): Part = {
  val measures: Seq[Measure] = partXml.measure.map((measureXml: musicxml.Measure) => toMusicProtobuf(measureXml))
  com.sparetimedevs.ami.core.Part(id = partXml.id, measures = measures)
  Part.of(id = "part-1", measures = List("measure-1", "measure-2", "measure-3"))
}

private def toMusicProtobuf(measureXml: musicxml.Measure): Measure = {
  val musicData: Seq[MusicComponent] = measureXml.musicu45dataOption11.map((musicDataRecord: scalaxb.DataRecord[musicxml.Musicu45dataOption1]) => {
    val musicDataOption: musicxml.Musicu45dataOption1 = musicDataRecord.as[musicxml.Musicu45dataOption1]
    musicDataOption match {
      case attributesTypeXml: musicxml.AttributesType                                                                              => toMusicProtobuf(attributesTypeXml)
      case musicxml.Backup(durationSequence1, editorialSequence2)                                                                  => NotImplementedMusicComponent()
      case musicxml.Barline(baru45style, editorialSequence2, wavyu45line, segno2, coda2, fermata, ending, repeat, attributes)      => NotImplementedMusicComponent()
      case musicxml.Bookmark(attributes)                                                                                           => NotImplementedMusicComponent()
      case musicxml.Direction(directionu45type, offset, editorialu45voiceu45directionSequence3, staffSequence4, sound, attributes) => NotImplementedMusicComponent()
      case musicxml.Figuredu45bass(figure, durationSequence2, editorialSequence3, attributes)                                      => NotImplementedMusicComponent()
      case musicxml.Forward(durationSequence1, editorialu45voiceSequence2, staffSequence3)                                         => NotImplementedMusicComponent()
      case musicxml.Grouping(feature, attributes)                                                                                  => NotImplementedMusicComponent()
      case musicxml.Harmony(harmonyu45chordSequence1, frame, offset, editorialSequence4, staffSequence5, attributes)               => NotImplementedMusicComponent()
      case musicxml.Link(attributes)                                                                                               => NotImplementedMusicComponent()
      case noteXml: musicxml.Note                                                                                                  => toMusicProtobuf(noteXml)
      case musicxml.Print(layoutSequence1, measureu45layout, measureu45numbering, partu45nameu45display, partu45abbreviationu45display, attributes) =>
        NotImplementedMusicComponent()
      case musicxml.Sound(soundsequence1, offset, attributes) => NotImplementedMusicComponent()
    }
  })

  Measure(
    number = measureXml.number,
    musicData = musicData
  )
}

private def toMusicProtobuf(attributesTypeXml: musicxml.AttributesType): AttributesType = {
  val times: Seq[Time] = attributesTypeXml.time.map((timeXml: musicxml.Time) => {
    val timeSignatures: Seq[TimeSignature] = timeXml.timeoption
      .as[musicxml.TimeSequence1]
      .timeu45signatureSequence1
      .map((timeSignatures: musicxml.Timeu45signatureSequence) => {
        TimeSignature(Beats(timeSignatures.beats.toByte), BeatType(timeSignatures.beatu45type.toByte))
      })

    Time(timeOption = TimeOption(timeSignatures))
  })

  AttributesType(new EditorialSequence(), time = times)
}

private def toMusicProtobuf(noteXml: musicxml.Note): Note =
  toMusicProtobuf(noteXml.typeValue)
    .map { noteType =>
      val noteOption: NoteOption = noteXml.noteoption.as[musicxml.NoteOption] match {
        case musicxml.NoteSequence1(grace, noteoption2)                           => ???
        case musicxml.NoteSequence4(cue, fullu45noteSequence2, durationSequence3) => ???
        case musicxml.NoteSequence5(fullu45noteSequence1, durationSequenceXml, tie) =>
          val isNotePartOfChord = if fullu45noteSequence1.chord.isDefined then true else false
          fullu45noteSequence1.fullu45noteoption1.as[musicxml.Fullu45noteOption1] match {
            case pitchXml: musicxml.Pitch                                    => toMusicProtobuf(pitchXml, noteType, durationSequenceXml, isNotePartOfChord)
            case musicxml.Rest(displayu45stepu45octaveSequence1, attributes) => Rest(noteType, toMusicProtobuf(durationSequenceXml))
            case musicxml.Unpitched(displayu45stepu45octaveSequence1)        => Unpitched(noteType, toMusicProtobuf(durationSequenceXml), isNotePartOfChord = isNotePartOfChord)
          }
      }
      Note(option = noteOption)
    }
    .getOrElse(Note(option = Rest(NoteType.Whole, Duration(4))))

private def toMusicProtobuf(noteTypeValueXml: Option[musicxml.Noteu45type]): Option[NoteType] =
  noteTypeValueXml.map { noteType =>
    noteType.value match {
      case _: musicxml.Maxima.type        => NoteType.Maxima
      case _: musicxml.LongTypeValue.type => NoteType.Long
      case _: musicxml.Breve.type         => NoteType.Breve
      case _: musicxml.WholeValue2.type   => NoteType.Whole
      case _: musicxml.HalfValue4.type    => NoteType.Half
      case _: musicxml.Quarter.type       => NoteType.Quarter
      case _: musicxml.Eighth.type        => NoteType.Eighth
      case _: musicxml.Number16th.type    => NoteType._16th
      case _: musicxml.Number32nd.type    => NoteType._32nd
      case _: musicxml.Number64th.type    => NoteType._64th
      case _: musicxml.Number128th.type   => NoteType._128th
      case _: musicxml.Number256th.type   => NoteType._256th
      case _: musicxml.Number512th.type   => NoteType._512th
      case _: musicxml.Number1024th.type  => NoteType._1024th
    }
  }

private def toMusicProtobuf(pitchXml: musicxml.Pitch, noteType: NoteType, durationSequenceXml: musicxml.DurationSequence, isNotePartOfChord: Boolean): Pitch =
  val duration = Duration(durationSequenceXml.duration)
  pitchXml.step match {
    case musicxml.A      => Pitch(Step.A, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
    case musicxml.B      => Pitch(Step.B, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
    case musicxml.CValue => Pitch(Step.C, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
    case musicxml.D      => Pitch(Step.D, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
    case musicxml.E      => Pitch(Step.E, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
    case musicxml.FValue => Pitch(Step.F, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
    case musicxml.GValue => Pitch(Step.G, noteType = noteType, duration = duration, isNotePartOfChord = isNotePartOfChord)
  }

private def toMusicProtobuf(durationSequenceXml: musicxml.DurationSequence): Duration =
  Duration(durationSequenceXml.duration)
