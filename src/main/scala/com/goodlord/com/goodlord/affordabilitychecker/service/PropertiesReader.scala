package com.goodlord.com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Property
import kantan.csv._
import kantan.csv.ops._

import scala.io.BufferedSource

class PropertiesReader {

  def get(csvFile: BufferedSource): Iterable[Property] = {
    implicit val propertyDecoder: HeaderDecoder[Property] =
      HeaderDecoder.decoder("Id", "Address", "Price (pcm)")(Property.apply _)

    val contents = csvFile.getLines.mkString(System.lineSeparator())

    return contents.unsafeReadCsv[List, Property](rfc.withHeader)
  }
}
