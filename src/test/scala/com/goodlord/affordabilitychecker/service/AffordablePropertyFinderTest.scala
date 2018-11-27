package com.goodlord.affordabilitychecker.service

import com.goodlord.affordabilitychecker.model.Property
import org.scalatest.FunSuite

class AffordablePropertyFinderTest extends FunSuite {

  test("Returns a list of affordable properties with the default income multipler") {
    val unaffordableProperties = Seq(
      Property(id = 1, address = "some unaffordable property #1", 1500),
      Property(id = 2, address = "some unaffordable property #2", 2500))

    val expected = Seq(
      Property(id = 3, address = "affordable property #1", 500),
      Property(id = 4, address = "affordable property #2", 400)
    )

    val netIncome = 1200

    val allProperties = unaffordableProperties ++ expected

    val testee = new AffordablePropertyFinder()
    val actual = testee.find(netIncome, allProperties)

    assert(actual === expected)
  }

  test("Returns a list of affordable properties with a different multiplier") {
    val unaffordableProperties = Seq(
      Property(id = 1, address = "some unaffordable property #1", 1500),
      Property(id = 2, address = "some unaffordable property #2", 2500))

    val expected = Seq(
      Property(id = 3, address = "affordable property #1", 500),
      Property(id = 4, address = "affordable property #2", 400)
    )

    val netIncome = 1000
    val incomeMultiplier = 2

    val allProperties = unaffordableProperties ++ expected

    val testee = new AffordablePropertyFinder(incomeMultiplier)
    val actual = testee.find(netIncome, allProperties)

    assert(actual === expected)
  }
}
