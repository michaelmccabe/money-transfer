#!/bin/bash


#eval 'mvn com.edugility:h2-maven-plugin:1.0:spawn'
#eval 'mvn compile exec:java -Dexec.mainClass="MoneyTransfer"'
#echo ''
echo 'GET ALL ACCOUNTS'
echo ''
curl -X GET -H "Content-Type: application/json" http://localhost:4567/accounts
echo ''
echo ''
echo 'MAKE A DEPOSIT ("amount": 45.60) TO "accountId": "d477d4f6-4650-4909-88a6-d004d6825f94"'
echo ''
curl -X POST -H "Content-Type: application/json" -d "@json/deposit.json" http://localhost:4567/deposit
echo ''
echo ''
echo 'CHECK BALANCE OF "accountId": "d477d4f6-4650-4909-88a6-d004d6825f94"'
echo ''
curl -X GET -H "Content-Type: application/json" http://localhost:4567/accounts/d477d4f6-4650-4909-88a6-d004d6825f94
echo ''
echo ''
echo 'MAKE A WITHDRAWAL ("amount": 5.00) TO "accountId": "d477d4f6-4650-4909-88a6-d004d6825f94"'
echo ''
curl -X POST -H "Content-Type: application/json" -d "@json/withdrawal.json" http://localhost:4567/withdrawal
echo ''
echo ''
echo 'CHECK BALANCE OF "accountId": "d477d4f6-4650-4909-88a6-d004d6825f94"'
echo ''
curl -X GET -H "Content-Type: application/json" http://localhost:4567/accounts/d477d4f6-4650-4909-88a6-d004d6825f94
echo ''
echo ''
echo 'TRANSFER FUNDS BETWEEN ACCOUNTS  "accountId": "d477d4f6-4650-4909-88a6-d004d6825f94", "destinationAccountId": "73232944-2f8f-4102-af90-7505338eb0a5"'
echo ''
curl -X POST -H "Content-Type: application/json" -d "@json/transfer.json" http://localhost:4567/transfer
echo ''
echo ''
echo 'CHECK BALANCE OF  "d477d4f6-4650-4909-88a6-d004d6825f94" and 73232944-2f8f-4102-af90-7505338eb0a5'
echo ''
curl -X GET -H "Content-Type: application/json" http://localhost:4567/accounts/d477d4f6-4650-4909-88a6-d004d6825f94
echo ''
echo ''
curl -X GET -H "Content-Type: application/json" http://localhost:4567/accounts/73232944-2f8f-4102-af90-7505338eb0a5
echo ''
echo ''
echo 'VIEW ALL TRANSACTIONS'
echo ''
echo ''
curl -X GET -H "Content-Type: application/json" http://localhost:4567/transactions


#eval 'mvn com.edugility:h2-maven-plugin:1.0:stop'
