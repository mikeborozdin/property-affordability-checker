package com.goodlord.affordabilitychecker.util

import java.time.format.DateTimeFormatter

object CsvUtil {

  def getDateFormatter(): DateTimeFormatter = DateTimeFormatter.ofPattern("d['st']['nd']['rd']['th'] MMMM y")

  def parseMoney(moneyString: String): BigDecimal = {
    return if (moneyString.length > 0) BigDecimal(removeNumberFormatting(moneyString)) else BigDecimal(0)
  }

  private def removeNumberFormatting(s:String): String = {
    return s.replaceAll("£", "").replaceAll(",", "")
  }
}
