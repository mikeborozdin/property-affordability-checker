package com.goodlord.com.goodlord.affordabilitychecker.service

import java.time.Month

import com.goodlord.affordabilitychecker.model.Transaction

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class BankAccountService(private val transactions: List[Transaction]) {

  def getRecurringTransactions(): List[Transaction] = {
    val recurringTransactionCandidates = scala.collection.mutable.Map[String, ListBuffer[Transaction]]()
    val transactionMonths = scala.collection.mutable.Map[String, scala.collection.mutable.Set[Month]]()
    val uniqueMonths = mutable.Set[Month]()

    for (transaction <- transactions) {
      val transactionHash = transaction.transactionType.toString() + transaction.details + transaction.amount.toString()
      val transactionMonth = transaction.date.getMonth()
      uniqueMonths += transactionMonth

      if(recurringTransactionCandidates.contains(transactionHash) && isDifferentMonth(transactionHash, transactionMonth, transactionMonths)) {
        recurringTransactionCandidates(transactionHash) += transaction
        transactionMonths(transactionHash) += transactionMonth
      } else {
        recurringTransactionCandidates(transactionHash) = ListBuffer(transaction)
        transactionMonths(transactionHash) = scala.collection.mutable.Set(transactionMonth)
      }
    }

    return recurringTransactionCandidates
      .values
      .filter(transactions => transactions.length == uniqueMonths.count(_ => true))
      .flatten
        .toList
  }

  private def isDifferentMonth(transactionHash: String, month: Month, transactionMonths: mutable.Map[String, mutable.Set[Month]]): Boolean = {
    return !transactionMonths(transactionHash).contains(month)
  }

  def getNetIncome(): BigDecimal = {
    return transactions.foldLeft(BigDecimal(0))(_ + _.amount)
  }
}
