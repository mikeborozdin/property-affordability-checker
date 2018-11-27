package com.goodlord.affordabilitychecker.service

import java.time.LocalDate

import com.goodlord.affordabilitychecker.model.Transaction
import com.goodlord.affordabilitychecker.util.CsvUtil
import kantan.csv._
import kantan.csv.ops._

import scala.io.BufferedSource

class TransactionsReader {

  def get(csvFile: BufferedSource): Iterable[Transaction] = {
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

  private def parseCsv(csvContents: String): Iterable[Transaction] = {
    implicit val dateDecoder: CellDecoder[LocalDate] = {
      CellDecoder.from(s => DecodeResult(LocalDate.parse(s, CsvUtil.getDateFormatter())))
    }

    implicit val transactionDecoder: RowDecoder[Transaction] = RowDecoder.ordered {
      (
        date: LocalDate,
        transactionType: String,
        details: String,
        moneyOutString: String,
        moneyInString: String) =>
      {
        val amount = CsvUtil.parseMoney(moneyInString) - CsvUtil.parseMoney(moneyOutString)

        Transaction(date, transactionType, details, amount)
      }
    }

    return csvContents.unsafeReadCsv[List, Transaction](rfc.withoutHeader)
  }
}
