# Accounting Ledger Application
`By Edith Aguirre`

Accounting Ledger Application is a program that can track all financial transactions for a business or for personal use.
It tracks deposits, payments/debits, and displays all or a filtered amount of transaction entries based on transaction type,
date, or vendor.

## _Home page:_
![Ledger_Home_Screen](https://github.com/user-attachments/assets/094c8d11-2663-42fa-b484-44e311268e86)

## _Ledger Display page:_
![Ledger_LedgerDisplay_Screen](https://github.com/user-attachments/assets/b9a09ac5-37ee-4e29-ba24-48735dfd1bce)

## **_Override toString() method:_**

An interesting part of code from this application is that since the LocalTime variable's default toString()
method drops the seconds if they are 00, I had to explicitly state in an override toString() method to 
display the seconds 00 using the format HH:mm:ss. Similarly, the float variable amount's toString() needed to be
formatted to output 2 decimal places.

![Interesting_Code](https://github.com/user-attachments/assets/a2ccb5cd-d3cf-4327-9cab-048b95a5106c)

