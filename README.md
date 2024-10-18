# Accounting Ledger Application
`By Edith Aguirre`

Accounting Ledger Application is a program that can track all financial transactions for a business or for personal use.
It tracks deposits, payments/debits, and displays all or a filtered amount of transaction entries based on transaction type,
date, or vendor.

## _Home page:_
![Image](C:\Users\edith\OneDrive\Desktop\Pluralsight\java-development\LearnToCode_Capstones\Ledger_Home_Screen.jpg "icon")

## _Ledger Display page:_
![Image](C:\Users\edith\OneDrive\Desktop\Pluralsight\java-development\LearnToCode_Capstones\Ledger_LedgerDisplay_Screen.jpg "icon")

## **_Override toString() method:_**

An interesting part of code from this application is that since the LocalTime variable's default toString()
method drops the seconds if they are 00, I had to explicitly state in an override toString() method to 
display the seconds 00 using the format HH:mm:ss. Similarly, the float variable amount's toString() needed to be
formatted to output 2 decimal places.

![Image](C:\Users\edith\OneDrive\Desktop\Pluralsight\java-development\LearnToCode_Capstones\Interesting_Code.jpg "icon")
