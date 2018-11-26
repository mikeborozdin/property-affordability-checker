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

e.g.

```
sbt run "my-bank-statement.csv my-properties.csv 1.5" 
```

Please, note that the app will only take those parameters into account, 
if you pass all 3 arguments.

Assumptions
====
The app makes the following assumptions

*  'A tenant is able to afford a property 
    if their monthly recurring income exceeds their monthly 
    recurring expenses by the total of the monthly rent times 125%.'
    is interpreted as that net income should be 2.25 times higher than rent
* What is a net income from the previous statement?
* * It's a monthly recurring income minus monthly recurring expenses
* How do we find recurring income and expenses?
* * First, we identify all recurring transactions
* * We do that by going through all transactions
* * And if a transaction is present in all the months, then it's a recurring transaction
* *  