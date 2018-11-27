package com.goodlord.affordabilitychecker.service

import java.time.LocalDate

import com.goodlord.affordabilitychecker.model.Transaction
import org.scalatest.FunSuite

class DefaultTransactionAnalyserTest extends FunSuite {

  test("Returns a list of recurring transaction based on their type, details, and amount") {
    val recurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), "Bank Credit", "Salary", 1000),
      Transaction(LocalDate.of(2018, 2, 1), "Bank Credit", "Salary", 1000),
      Transaction(LocalDate.of(2018, 3, 1), "Bank Credit", "Salary", 1000)
    )

    val nonRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), "Card Payment", "Tesco", 12.23),
      Transaction(LocalDate.of(2018, 2, 1), "Card Payment", "Tesco", 9.43),
      Transaction(LocalDate.of(2018, 1, 1), "Bank Credit", "Dividends", 500),
      Transaction(LocalDate.of(2018, 2, 1), "Bank Credit", "Dividends", 500)
    )

    val allTransactions = recurringTransactions ++ nonRecurringTransactions

    val expectedUniqueRecurringTransactions = Set(Transaction(null, transactionType = "Bank Credit", "Salary", 1000))

    val testee = new DefaultTransactionAnalyser()

    val actualRecurringTransactions = testee.getRecurring(allTransactions)

    assert(actualRecurringTransactions === expectedUniqueRecurringTransactions)
  }

  test("Don't consider a transaction recurring if it occurs a few times in the same month") {
    val expectedRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), "Bank Credit", "Salary", 1000),
      Transaction(LocalDate.of(2018, 2, 1), "Bank Credit", "Salary", 1000)
    )

    val nonRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), "Bank Credit", "same transaction hash, but not recurring", 10),
      Transaction(LocalDate.of(2018, 1, 2), "Bank Credit", "same transaction hash, but not recurring", 10),
      Transaction(LocalDate.of(2018, 2, 2), "Bank Credit", "something else", 10)
    )

    val allTransactions = expectedRecurringTransactions ++ nonRecurringTransactions

    val testee = new DefaultTransactionAnalyser()

    val actualRecurringTransactions = testee.getRecurring(nonRecurringTransactions)

    assert(actualRecurringTransactions.size === 0)
  }
}
