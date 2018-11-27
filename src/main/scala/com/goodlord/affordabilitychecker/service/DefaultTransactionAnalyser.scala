package com.goodlord.affordabilitychecker.service

import java.time.Month

import com.goodlord.affordabilitychecker.model.Transaction

import scala.collection.mutable

class DefaultTransactionAnalyser extends TransactionAnalyser {

  def getRecurring(transactions: Iterable[Transaction]): Iterable[Transaction] = {
    val uniqueMonths = mutable.Set[Month]()

    val transactionOccurrences = mutable.Map[Transaction, mutable.Set[Month]]()

    for (transaction <- transactions) {
      val transactionMonth = transaction.date.getMonth()
      uniqueMonths += transactionMonth

      val transactionWithNoDate = Transaction(null, transaction.transactionType, transaction.details, transaction.amount)

      if (transactionOccurrences.contains(transactionWithNoDate) && !transactionOccurrences(transactionWithNoDate).contains(transactionMonth)) {
          transactionOccurrences(transactionWithNoDate) += transactionMonth
      } else {
        transactionOccurrences(transactionWithNoDate) = mutable.Set(transactionMonth)
      }
    }

    val recurring = transactionOccurrences.filter(keyValue => keyValue._2.size == uniqueMonths.size)

    return recurring.keys
  }
}
