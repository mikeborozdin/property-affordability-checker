package com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Transaction

trait TransactionAnalyser {

  def getRecurring(transactions: Iterable[Transaction]): Iterable[Transaction]
}
