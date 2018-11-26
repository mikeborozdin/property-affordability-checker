package com.goodlord.affordabilitycheck.service

import com.goodlord.affordabilitychecker.model.Property
import com.goodlord.com.goodlord.affordabilitychecker.service.AffordabilityChecker
import org.scalatest.FunSuite

class AffordabilityCheckerTest extends FunSuite {

  test("Returns a list of affordable properties") {
    val unaffordableProperties = List(
      Property(id = 1, address = "some unaffordable property #1", 1500),
      Property(id = 2, address = "some unaffordable property #2", 2500))

    val expectedAffordableProperites = List(
      Property(id = 3, address = "affordable property #1", 500),
      Property(id = 4, address = "affordable property #2", 400)
    )

    val netIncome = 1000

    val allProperties = unaffordableProperties ++ expectedAffordableProperites

    val testee = new AffordabilityChecker();
    val actualAffordableFlats = testee.getAffordableProperties(allProperties, netIncome);

    assert(actualAffordableFlats === expectedAffordableProperites)
  }
}
