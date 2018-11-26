package com.goodlord.affordabilitycheck.app

import com.goodlord.com.goodlord.affordabilitychecker.service._

object Main extends App {

  val transactions = new TransactionsReader().get("bank_statement.csv")
  val monthlyNetIncome = new IncomeAnalyser(new DefaultTransactionAnalyser()).getMonthlyNetIncome(transactions)

  val allProperties = new PropertiesReader().get("properties.csv")

  val affordableProperties = new AffordablePropertyFinder().find(monthlyNetIncome, allProperties)

  println(affordableProperties)
}
