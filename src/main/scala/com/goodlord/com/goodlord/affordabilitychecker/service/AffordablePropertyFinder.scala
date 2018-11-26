package com.goodlord.com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Property

class AffordablePropertyFinder(private val incomeMultiplier: BigDecimal = IncomeMultiplier.Default) {

  def find(netMonthlyIncome: BigDecimal, allProperties: Iterable[Property]): Iterable[Property] = {
    return allProperties
        .filter(p => p.price * incomeMultiplier <= netMonthlyIncome)
  }
}

object IncomeMultiplier {
  val Default: BigDecimal = 2.25
}
