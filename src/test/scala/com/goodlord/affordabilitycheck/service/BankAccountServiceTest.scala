package com.goodlord.affordabilitycheck.service

import java.time.LocalDate

import com.goodlord.affordabilitychecker.model.{Transaction, TransactionType}
import com.goodlord.com.goodlord.affordabilitychecker.service.BankAccountService
import org.scalatest.FunSuite

class BankAccountServiceTest extends FunSuite {

  test("Returns a net income based on an account statement") {
    val expectedNetIncome = 900

    val allTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), TransactionType.BankCredit,  "Salary", 1000),
      Transaction(LocalDate.of(2018, 2, 1), TransactionType.DirectDebit, "Credit Card Bill", -100)
    )

    val testee = new BankAccountService(allTransactions)
    val actualNetIncome = testee.getNetIncome()

    assert(expectedNetIncome === actualNetIncome)
  }

  test("Returns a list of recurring transaction based on their type, details, and amount") {
    val expectedRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), TransactionType.BankCredit, "Salary", 1000),
      Transaction(LocalDate.of(2018, 2, 1), TransactionType.BankCredit, "Salary", 1000),
      Transaction(LocalDate.of(2018, 3, 1), TransactionType.BankCredit, "Salary", 1000)
    )

    val nonRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), TransactionType.CardPayment, "Tesco", 12.23),
      Transaction(LocalDate.of(2018, 2, 1), TransactionType.BankCredit, "Tesco", 9.43),
      Transaction(LocalDate.of(2018, 1, 1), TransactionType.BankCredit, "Dividends", 500),
      Transaction(LocalDate.of(2018, 2, 1), TransactionType.BankCredit, "Dividends", 500)
    )

    val allTransactions = expectedRecurringTransactions ++ nonRecurringTransactions

    val testee = new BankAccountService(allTransactions)

    val actualRecurringTransactions = testee.getRecurringTransactions()

    assert(actualRecurringTransactions === expectedRecurringTransactions)
  }

  test("Don't consider a transaction recurring if it occurs a few times in the same month") {
    val expectedRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), TransactionType.BankCredit, "Salary", 1000),
      Transaction(LocalDate.of(2018, 2, 1), TransactionType.BankCredit, "Salary", 1000)
    )

    val nonRecurringTransactions = List(
      Transaction(LocalDate.of(2018, 1, 1), TransactionType.BankCredit, "same transaction hash, but not recurring", 10),
      Transaction(LocalDate.of(2018, 1, 2), TransactionType.BankCredit, "same transaction hash, but not recurring", 10),
      Transaction(LocalDate.of(2018, 2, 2), TransactionType.BankCredit, "something else", 10)
    )

    val allTransactions = expectedRecurringTransactions ++ nonRecurringTransactions

    val testee = new BankAccountService(nonRecurringTransactions)

    val actualRecurringTransactions = testee.getRecurringTransactions()

    assert(actualRecurringTransactions.length === 0)
  }
}
