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
}
