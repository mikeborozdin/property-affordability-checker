package com.goodlord.affordabilitychecker.app

import com.goodlord.affordabilitychecker.model.Property
import com.goodlord.affordabilitychecker.service._

import scala.io.{BufferedSource, Source}

object Main extends App {

  val (transactionFile, propertyFile, incomeMultiplier) = if (args.length == 3) getParamsFromCmd(args) else getDefaultParams()

  val transactions = new TransactionsReader().get(transactionFile)
  val monthlyNetIncome = new IncomeAnalyser(new DefaultTransactionAnalyser()).getMonthlyNetIncome(transactions)

  val allProperties = new PropertiesReader().get(propertyFile)

  val affordableProperties = new AffordablePropertyFinder(incomeMultiplier).find(monthlyNetIncome, allProperties)

  printResults(monthlyNetIncome, incomeMultiplier, affordableProperties)

  private def getDefaultParams(): (BufferedSource, BufferedSource, BigDecimal) = {
    return (Source.fromResource("bank_statement.csv"), Source.fromResource("properties.csv"), IncomeMultiplier.Default)
  }

  private def getParamsFromCmd(args: Array[String]): (BufferedSource, BufferedSource, BigDecimal) = {
    return (Source.fromFile(args(0)), Source.fromFile(args(1)), BigDecimal(args(2)))
  }

  private def printResults(monthlyNetIncome: BigDecimal, incomeMultiplier: BigDecimal, affordableProperties: Iterable[Property]):Unit = {
    println()
    println()
    println()

    println(s"Net monthly income: £$monthlyNetIncome")
    println(s"Can afford properties up to: £ ${monthlyNetIncome / incomeMultiplier} (pcm) based on the multiplier of $incomeMultiplier")

    println()
    println("PROPERTIES")
    println("====")
    affordableProperties.foreach(println _)
  }
}
