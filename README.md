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
> javac -cp ./libs/h2-2.1.214.jar -d ./out/production/simple-car-sharing-app -sourcepath . src/carsharing/*.java src/carsharing/*/*.java src/carsharing/*/*/*.java

To run application, navigate to the project root directory and run following command:
> java -cp ./libs/h2-2.1.214.jar:./out/production/simple-car-sharing-app carsharing.Main

# Examples
The symbol **>** represents the user input.

**Example 1:** Manager operations:
```
1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
>1

1. Company list
2. Create a company
0. Back
>1
The company list is empty!

1. Company list
2. Create a company
0. Back
>2

Enter the company name:
Car ToGo
The company was created.

1. Company list
2. Create a company
0. Back
> 2

Enter the company name:
Drive Now
The company was created.

1. Company list
2. Create a company
0. Back
> 1

Choose the company:
1. Car ToGo
2. Drive Now
0. Back
```

**Example 2:** Company operations:
```
Choose the company:
1. Car ToGo
2. Drive Now
0. Back
> 1

'Car ToGo' company
1. Car list
2. Create a car
0. Back
> 1

The car list is empty!

'Car ToGo' company
1. Car list
2. Create a car
0. Back
> 2

Enter the car name:
Ford Mustang
The car was created.

'Car ToGo' company
1. Car list
2. Create a car
0. Back
> 2

Enter the car name:
Dodge Viper
The car was created.

'Car ToGo' company
1. Car list
2. Create a car
0. Back
> 1

Car list:
1. Ford Mustang rented: false
2. Dodge Viper rented: false

'Car ToGo' company
1. Car list
2. Create a car
0. Back
> 0

1. Company list
2. Create a company
0. Back
> 0

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
```

**Example 3:** Customer operations:
```
1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
> 2
The customer list is empty!

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
> 3

Enter the customer name:
John Doe
The customer was created.

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
> 3

Enter the customer name:
Jane Doe
The customer was created.

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
> 2

Choose a customer:
1. John Doe
2. Jane Doe
0. Back
> 2

1. Rent a car
2. Return a rented car
3. My rented car
0. Back
> 1

Choose a company:
1. Car ToGo
2. Drive Now
0. Back
> 1

Choose a car:
1. Ford Mustang
2. Dodge Viper
> 1
You rented 'Ford Mustang'

1. Rent a car
2. Return a rented car
3. My rented car
0. Back
> 1
You've already rented a car!

1. Rent a car
2. Return a rented car
3. My rented car
0. Back
> 3
Your rented car:
Ford Mustang
Company:
Car ToGo

1. Rent a car
2. Return a rented car
3. My rented car
0. Back
> 2
You've returned a rented car: Ford Mustang.

1. Rent a car
2. Return a rented car
3. My rented car
0. Back
> 0

1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
> 0
```

# Licence
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)