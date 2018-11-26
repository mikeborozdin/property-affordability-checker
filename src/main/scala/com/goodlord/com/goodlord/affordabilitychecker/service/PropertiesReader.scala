package com.goodlord.com.goodlord.affordabilitychecker.service

import java.io.File

import com.goodlord.affordabilitychecker.model.Property
import kantan.csv._
import kantan.csv.ops._

import scala.io.Source

class PropertiesReader {

  def get(csvFileName: String): List[Property] = {
    implicit val propertyDecoder: HeaderDecoder[Property] =
      HeaderDecoder.decoder("Id", "Address", "Price (pcm)")(Property.apply _)

    val contents = Source.fromResource(csvFileName).getLines.mkString(System.lineSeparator())

    return contents.unsafeReadCsv[List, Property](rfc.withHeader)
  }
}
