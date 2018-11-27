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
* * It's a monthly recurring income minus monthly recurring expenses

How do we find recurring income and expenses?
----
* The key is to find all recurring transactions
* We assume that a transaction is recurring if it appears in every month
* And its payment type (e.g., 'Bank Credit'), details ('Salary') and amount (£1,000) are the same in every month
* Also, we don't include current rent payments in the calculations of a montly net income. Indeed, if someone is applying for a rent, 
it doesn't make sense to include the current one. We assume that rent transactions always have 'Letting Service' in the `details` field
* In theory, we could've excluded utility payments, as well, as their amount may vary by property