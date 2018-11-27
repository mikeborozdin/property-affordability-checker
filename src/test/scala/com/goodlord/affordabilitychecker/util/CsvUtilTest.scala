package com.goodlord.affordabilitychecker.util

import java.time.LocalDate

import org.scalatest.FunSuite

class CsvUtilTest extends FunSuite {

  test("getDateFormatter() returns a date formatter that parses dates like '6th October 2016") {
    val expected = LocalDate.of(2016, 10, 6)
    val actual = LocalDate.parse("6th October 2016", CsvUtil.getDateFormatter())

    assert(actual === expected)
  }

  test("parseMoney() converts a text reprsentation of an amount (£1,000) into BigDecimal") {
    val expected = BigDecimal(1000)
    val actual = CsvUtil.parseMoney("£1,000")

    assert(actual === expected)
  }
}
