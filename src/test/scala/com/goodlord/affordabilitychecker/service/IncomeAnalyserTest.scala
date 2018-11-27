package com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Transaction
import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSuite

class IncomeAnalyserTest extends FunSuite with MockFactory {

  test("Returns monthly income which is a sum of all recurring transactions") {
    val expected = 900

    val allTransactions = List[Transaction]()
    val recurringTransactions = Set[Transaction](
      Transaction(null, "Bank Credit", "Salary", 1000),
      Transaction(null, "Direct Debit", "Credit Card Statement", -100)
    )

    val transactionAnalyser = stub[TransactionAnalyser]
    (transactionAnalyser.getRecurring _).when(allTransactions).returning(recurringTransactions)

    val testee = new IncomeAnalyser(transactionAnalyser)
    val actual = testee.getMonthlyNetIncome(allTransactions)

    assert(actual === expected)
  }

  test("Ignores the current letting payments") {
    val expected = 900

    val allTransactions = List[Transaction]()
    val recurringTransactions = Set[Transaction](
      Transaction(null, "Bank Credit", "Salary", 1000),
      Transaction(null, "Direct Debit", "Credit Card Statement", -100),
      Transaction(null, "Direct Debit", "Letting Service", -400)
    )

    val transactionAnalyser = stub[TransactionAnalyser]
    (transactionAnalyser.getRecurring _).when(allTransactions).returning(recurringTransactions)

    val testee = new IncomeAnalyser(transactionAnalyser)
    val actual = testee.getMonthlyNetIncome(allTransactions)

    assert(actual === expected)
  }
}
