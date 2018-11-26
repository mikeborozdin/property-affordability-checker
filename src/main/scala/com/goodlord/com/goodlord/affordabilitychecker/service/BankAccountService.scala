package com.goodlord.com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Transaction

class BankAccountService(private val transactions: List[Transaction]) {

  def getNetIncome(): BigDecimal = {
    return transactions.foldLeft(BigDecimal(0))(_ + _.amount)
  }
}
