# DATABASE_TESTS
t's necessary to deploy a mysql server locally and restore the database from the  . Then you need to implement 
dump software work with the database through interaction with representations in the form of models (CRUD).

|TC-1. Adding a new entry|
|---|
             
|Step| Expected result     |
|--------|-----------------------|
|1.Run any test (or several).| The test has completed.|
|2.Add a result of each completed test in the database as a new record in the corresponding table.| Information added|

|TC-2. Processing of test data|
|---|

 Precondition.
 |---|
|1.Select tests from the database where ID contains two random repeating digits, for example "11" or "77". But no more than 10 tests.|
|2.Copy these tests with an indication of the   project and the author.|

|Step| Expected result     |
|--------|-----------------------|
|Stimulate the launch of each tests. Update information about them in the database.| Tests are completed, information is updated.|

Postconditions.
|---|----|
|1.Delete the records created in from the database.|The records have been deleted|
|----|----|
