package com.goodlord.com.goodlord.affordabilitychecker.service

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.goodlord.affordabilitychecker.model.Transaction
import kantan.csv._
import kantan.csv.ops._

import scala.io.BufferedSource

class TransactionsReader {

  def get(csvFile: BufferedSource): List[Transaction] = {
    val fileContents = csvFile.getLines.mkString(System.lineSeparator())

    val transactionsCsv = getTransactionCsv(fileContents)

    return parseCsv(transactionsCsv)
  }

  private def getTransactionCsv(fileContents: String):String = {
    val statementOpeningBalancePos = fileContents.indexOf("STATEMENT OPENING BALANCE");
    val newLinePos = fileContents.indexOf(System.lineSeparator(), statementOpeningBalancePos);

    val transactionsCsv = fileContents.substring(newLinePos + System.lineSeparator().length)

    return transactionsCsv
  }

  private def parseCsv(csvContents: String): List[Transaction] = {
    implicit val dateDecoder: CellDecoder[LocalDate] = {
      CellDecoder.from(s => DecodeResult(LocalDate.parse(s, getDateFormatter())))
    }

    implicit val transactionDecoder: RowDecoder[Transaction] = RowDecoder.ordered {
      (
        date: LocalDate,
        transactionType: String,
        details: String,
        moneyOutString: String,
        moneyInString: String) =>
      {
        val amount = parseMoney(moneyInString) - parseMoney(moneyOutString)

        Transaction(date, transactionType, details, amount)
      }
    }

    return csvContents.unsafeReadCsv[List, Transaction](rfc.withoutHeader)
  }

  private def getDateFormatter():DateTimeFormatter = DateTimeFormatter.ofPattern("d['st']['nd']['rd']['th'] MMMM y")

  private def parseMoney(moneyString: String): BigDecimal = {
    return if (moneyString.length > 0) BigDecimal(removeNumberFormatting(moneyString)) else BigDecimal(0)
  }

  private def removeNumberFormatting(s:String): String = {
    return s.replaceAll("£", "").replaceAll(",", "")
  }
}
