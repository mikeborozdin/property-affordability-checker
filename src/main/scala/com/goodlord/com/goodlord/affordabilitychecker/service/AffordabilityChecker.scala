package com.goodlord.com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Property

class AffordabilityChecker {

  private val INCOME_MULTIPLIER = 1.25

  def getAffordableProperties(allProperties: List[Property], netIncome: BigDecimal): List[Property] = {
    return allProperties
        .filter(p => p.price <= netIncome * INCOME_MULTIPLIER)
  }
}
