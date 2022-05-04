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

import cats.effect.IO
import cats.syntax.all.*
import com.sparetimedevs.ami.core.util.{getMessage, nullableAsOption}
import com.sparetimedevs.ami.mediaprocessor.{Errors, IOEitherErrorsOr, ValidationError, asIOEitherErrorsOrT}

import java.io.{ByteArrayInputStream, File, FileInputStream, InputStream}
import java.net.{URI, URL}
import javax.xml.XMLConstants
import javax.xml.catalog.{CatalogFeatures, CatalogManager, CatalogResolver}
import javax.xml.transform.sax.{SAXResult, SAXSource}
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import scala.util.Using
import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml.{InputSource, Node}

def validate(musicXmlData: Array[Byte]): IOEitherErrorsOr[Node] =
  IO {
    Using
      .Manager { (use: Using.Manager) =>
        val maybeFeatures: Option[CatalogFeatures] = CatalogFeatures.defaults().nullableAsOption
        val maybeCatalogFileUri: Option[URI] = getClass.getResource("/musicxml_4_0/schema/catalog.xml").nullableAsOption.flatMap(_.toURI.nullableAsOption)
        def catalogResolver(features: CatalogFeatures, catalogFileUri: URI): CatalogResolver | Null = CatalogManager.catalogResolver(features, catalogFileUri)
        val maybeCatalogResolver: Option[CatalogResolver] = (maybeFeatures, maybeCatalogFileUri)
          .apWith { Some(catalogResolver) }
          .flatMap { catalogResolver => catalogResolver.nullableAsOption }

        val maybeFactory: Option[SchemaFactory] = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).nullableAsOption

        (maybeCatalogResolver, maybeFactory).mapN((catalogResolver, factory) => factory.setResourceResolver(catalogResolver))

        val adapter = new NoBindingFactoryAdapter
        val saxResult = new SAXResult(adapter)

        val inputStream: InputStream = use(new ByteArrayInputStream(musicXmlData))
        val inputSource: InputSource = new InputSource(inputStream)
        val source: SAXSource = new SAXSource(inputSource)

        val maybeXsd: Option[URL] = getClass.getResource("/musicxml_4_0/schema/musicxml.xsd").nullableAsOption

        maybeFactory
          .flatMap { factory => maybeXsd.flatMap { xsd => factory.newSchema(xsd).nullableAsOption } }
          .flatMap { schema => schema.newValidator().nullableAsOption }
          .foreach { validator => validator.validate(source, saxResult) }
        adapter.rootElem
      }
      .toEither
      .left
      .map { (t: Throwable) => ValidationError(getMessage(t)).asOnlyError }
  }.asIOEitherErrorsOrT
