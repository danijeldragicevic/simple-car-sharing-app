# Simple Car Sharing App
Java application who manages car renting(s).
Application is able to:
- create cars, companies and customers;
- customers are able to rent and return cars to belonging companies;

# Technology
- Java 11
- H2 database

# To run application:
There are no any build automation tool used. We have to compile and run manually. :)

To compile files, navigate to the project root directory and run following command:
> javac -cp ./libs/sqlite-jdbc-3.39.2.1.jar -d ./out/simple-atm-system -sourcepath . src/atm/*.java src/atm/*/*.java

To run application, navigate to the project root directory and run following command:
> java -cp ./libs/sqlite-jdbc-3.39.2.1.jar:./out/simple-atm-system atm.Main

# Examples
The symbol **>** represents the user input.
