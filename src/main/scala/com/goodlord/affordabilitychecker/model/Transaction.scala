package com.goodlord.affordabilitychecker.model

import java.time.LocalDate

case class Transaction (
                         date: LocalDate,
                         transactionType: String,
                         details: String,
                         amount: BigDecimal
                       )
