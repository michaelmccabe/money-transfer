
I went a bit off-scope in this exercise (including creating a full transaction history) 

To compile and run, open a terminal and run the h2 db with this command:
mvn com.edugility:h2-maven-plugin:1.0:spawn

I wasn't sure how to automatically get it to start without spring. 

Then run the service with this command:
mvn compile exec:java -Dexec.mainClass="MoneyTransfer"
Or run it in an IDE

run the rundemo.sh shell script in another terminal then to show how to use the API


The TransactionService is the only one unit tested. 

The Dao classes need to be refactored and tested but the API works to create news users and accounts and all db calls 
are transactional.

