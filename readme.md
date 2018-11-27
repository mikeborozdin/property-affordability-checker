Running Project
====
You can run the project using `sbt`

```
sbt compile
sbt run
```

Inputs
====
By default the app reads CSV files from `src/main/resources` 
and the income multipler is `2.25' (125%) (see assumptions).

However, you can easily pass your own parameters through the command line.

```
sbt run "[bank-statement path] [properties path] [income multiplier]" 
```

e.g.:

```
sbt run "my-bank-statement.csv my-properties.csv 1.5" 
```

Please, note that the app will only take those parameters into account, 
if you pass all 3 arguments.

Assumptions
====
The app makes the following assumptions:

* A net monthly income should be at least 2.25 times higher than a monthly rent.
    * That's an interpretations of the following requirement:  
    * *'A tenant is able to afford a property if their monthly recurring income exceeds their monthly recurring expenses by the total of the monthly rent times 125%.'*
* What is a net income from the previous statement?
    * It's a monthly recurring income minus monthly recurring expenses
* We don't need to include existing rent payments in recurring expenses
    * After all, a person is looking for a new property, so it doesn't make sense to include the existing rent
    * Plus, if we included the existing rent payments, then a net monthly income would be too low to be able to afford any properties
    * We assume that rent transactions always have 'Letting Service' in the `details` field
* Similarly, we could've excluded the utility bills (gas, electricity), as they vary by property. But we've included them for the full picture

How do we find recurring income and expenses?
----
* The key is to find all recurring transactions
* We assume that a transaction is recurring if it appears in every month
* And its payment type (e.g., 'Bank Credit'), details ('Salary') and amount (£1,000) are the same in every month
* As a result, our algorithm easily detects various recurring payments whether they are direct debits or regular transfers to a friend
* Why don't we include a date of the month in the recurring algorithm?
    * Indeed, recurring payments usually occur on the same day every month
    * However, that would complicate the algorithm
    * 'The same day' actually varies - a payment could be on the last day of the month (which could be anything from 28 to 31)
    * Or even on a specific date, unless it falls on a weekend (or a public holiday)
