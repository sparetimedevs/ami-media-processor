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
import com.sparetimedevs.ami.mediaprocessor.{Errors, IOEitherErrorsOr, ValidationError, asIOEitherErrorsOrT}

import java.io.{ByteArrayInputStream, File, FileInputStream, InputStream}
import java.net.URI
import javax.xml.XMLConstants
import javax.xml.catalog.{CatalogFeatures, CatalogManager, CatalogResolver}
import javax.xml.transform.sax.{SAXResult, SAXSource}
import javax.xml.validation.{Schema, SchemaFactory, Validator}
import scala.util.Using
import scala.xml.parsing.NoBindingFactoryAdapter
import scala.xml.{InputSource, Node}

def validate(musicXmlData: Array[Byte], xsdPath: String): IOEitherErrorsOr[Node] =
  IO {
    Using
      .Manager { (use: Using.Manager) =>
        val features: CatalogFeatures = CatalogFeatures.defaults()
        val catalogFileUri: URI = new File("src/main/resources/catalog.xml").toURI
        val catalogResolver: CatalogResolver = CatalogManager.catalogResolver(features, catalogFileUri)

        val factory: SchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)

        factory.setResourceResolver(catalogResolver)

        val schema: Schema = factory.newSchema(new File(xsdPath))

        val adapter = new NoBindingFactoryAdapter
        val saxResult = new SAXResult(adapter)

        val inputStream: InputStream = use(new ByteArrayInputStream(musicXmlData))
        val inputSource: InputSource = new InputSource(inputStream)
        val source: SAXSource = new SAXSource(inputSource)

        val validator: Validator = schema.newValidator()
        validator.validate(source, saxResult)
        adapter.rootElem
      }
      .toEither
      .left
      .map { (t: Throwable) => ValidationError(t.getMessage).asOnlyError }
  }.asIOEitherErrorsOrT