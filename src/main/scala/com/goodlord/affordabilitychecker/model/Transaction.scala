package com.goodlord.affordabilitychecker.model

import java.time.LocalDate

object TransactionType extends Enumeration {
  val Atm, DirectDebit, CardPayment, BankCredit, StandingOrder = Value
}

case class Transaction (
                         date: LocalDate,
                         transactionType: TransactionType.Value,
                         details: String,
                         amount: BigDecimal
                       )
