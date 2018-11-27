package com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Transaction

class IncomeAnalyser(private val transactionAnalyser: TransactionAnalyser) {

  def getMonthlyNetIncome(transactions: Iterable[Transaction]): BigDecimal = {
    val recurringTransactions = transactionAnalyser.getRecurring(transactions)
    val recurringWithoutCurrentRent = recurringTransactions.filter(t => !t.details.contains("Letting Service"))

    return recurringWithoutCurrentRent.foldLeft(BigDecimal(0))(_ + _.amount)
  }
}
