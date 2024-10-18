/*
An application that a user can use to track all financial transactions for a business or for personal use.
 */
package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccountingLedgerApp {
    // Create an arrayList to hold transactions
    static ArrayList<Transactions> transactionsArrayList = new ArrayList<>();
    // Create a scanner object to read user input
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        // Continue to run until the user chooses to exit
        while(true){
            // Home screen: Give the user options on what to do in the app
            System.out.print("""
                    Welcome to your Accounting Ledger's Home page! Please select an option to continue.
                    (D) Add Deposit
                    (P) Make Payment (Debit)
                    (L) Ledger
                    (X) Exit
                    Select an option (D,P,L,or X): \
                    """);
            String option = scanner.nextLine().trim();

            // Match user's option to a case and perform what the user selected
            switch(option.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledger();
                    break;
                case "X":
                    scanner.close();
                    System.exit(0); // Terminates the application
                default:
                    System.out.println("Invalid input. Please enter a valid option(D,P,L, or X).");
                    break;
            }
        }
    }

    // Read the transactions.csv file and add transactions to the ArrayList
    private  static void loadTransactions(){

        try{
            // Read the transactions.csv file
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Skip first line with the header
            bufferedReader.readLine();

            // Read the file a line at a time
            String input;
            while((input = bufferedReader.readLine()) != null){

                // Split string at each bar ( | ) character
                String[] tokens = input.split("\\|");

                // Create a transaction from each line and add it to the arrayList
                transactionsArrayList.add(new Transactions(LocalDate.parse(tokens[0],formatDate),
                        LocalTime.parse(tokens[1],formatTime), tokens[2], tokens[3], Float.parseFloat(tokens[4])));
            }
            bufferedReader.close();
        }catch(IOException e){
            System.out.println("Error. An issue has occurred when reading the file.");
        }
    }

    // Write to the transactions.csv file the transactions logged by the user (Deposit or debit)
    private static void writeToFile(String transactionType){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

            // Prompt the user for the date, time, deposit/debit description, vendor, and amount
            System.out.print("Enter the date in the format yyyy-MM-dd: ");
            bufferedWriter.write(scanner.nextLine().trim() + "|");

            System.out.print("Enter the time (in the format HH:mm:ss): ");
            bufferedWriter.write(scanner.nextLine().trim() + "|");

            System.out.print("Enter the " + transactionType + " description:");
            bufferedWriter.write(scanner.nextLine().trim() + "|");

            System.out.print("Enter the vendor: ");
            bufferedWriter.write(scanner.nextLine().trim() + "|");

            System.out.print("Enter the amount: ");
            float amount = scanner.nextFloat();
            scanner.nextLine();     // Catch the new line character that was left by the float

            // If it's a debit make the amount negative if not already
            if(transactionType.equals("debit") && amount > 0){
                amount = -amount;
            }
            // write a formatted float so that displays with 2 decimal places
            bufferedWriter.write(String.format("%.2f\n", amount));

            // Close the buffered writer
            bufferedWriter.close();

        }catch(IOException e){
            System.out.println("Error. An issue has occurred when writing to the file.");
        }
    }

    // Prompt user for the deposit information and save it to the csv file
    private static void addDeposit() {
        String option;
        do{
            System.out.println("\nTo add a deposit, enter the following information.");

            // Write the deposit information given by the user to the file
            writeToFile("deposit");

            System.out.println("Your deposit has been added successfully!");
            // Ask user if they would like to add another deposit or exit
            System.out.print("Would you like to add another deposit? (Press any key for yes, or X to exit): ");
            option = scanner.nextLine().trim();

            System.out.println();   // Make a new line to make more space

        }while(!option.equalsIgnoreCase("X"));
    }

    // Prompt user for the debit information and save it to the csv file
    private static void makePayment() {
        String option;
        do{
            System.out.println("To add a payment/debit, enter the following information.");
            // Write the debit information given by the user to the file
            writeToFile("debit");

            System.out.println("Your debit/payment has been added successfully!");
            // Ask user if they would like to add another debit or exit
            System.out.print("Would you like to add another debit? (Press any key for yes, or X to exit): ");
            option = scanner.nextLine().trim();

            System.out.println();   // Make a new line to make more space

        }while(!option.equalsIgnoreCase("X"));
    }

    // Shows all entries (newest entries first)
    private static void ledger() {
        // Read the transactions.csv file and add transactions to the arrayList, to read from it
        loadTransactions();

        // sort the transactions by the date
        Collections.sort(transactionsArrayList, new Comparator<>() {
            @Override
            public int compare(Transactions o1, Transactions o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        // Display ledger options and prompt the user to select one
        while (true) {
            System.out.print("""
                   This is the Ledger page. Which entries would you like to display?
                    (A) All
                    (D) Deposits
                    (P) Payments
                    (R) Reports
                    (H) Home
                    Please select an option (A,D,P,R, or H): \
                    """);
            String option = scanner.nextLine().trim();

            switch (option.toUpperCase()) {
                case "A":   // Display all entries with header using an arrayList (newest entries first)
                    System.out.println("date|time|description|vendor|amount");
                    for(Transactions transaction: transactionsArrayList){
                        System.out.println(transaction);
                    }
                    break;
                case "D":   // Display all deposits (newest entries first)
                    System.out.println("date|time|description|vendor|amount");
                    for(Transactions transaction : transactionsArrayList){
                        if(transaction.getAmount() > 0){
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "P":    // Display only negative entries or payments (newest entries first)
                    System.out.println("date|time|description|vendor|amount");
                    for(Transactions transaction : transactionsArrayList){
                        if(transaction.getAmount() < 0){
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "R":   // Run pre-defined reports or run a custom search
                    ledgerReports();
                    break;
                case "H":   // Go back to the home page
                    return;
                default:    // Let user know they entered a non-option and should try again
                    System.out.println("Invalid input. Please select one of the options(A,D,P,R, or H)");
                    break;
            }
            System.out.println();   // New line for space
        }
    }
    // A new screen that allows user to run pre-defined reports or run a custom search
    private static void ledgerReports() {
        while(true) {
            try {
                System.out.print("""
                        This is the Ledger Reports screen. Please select a display option to continue.
                        (1) Month To Date
                        (2) Previous Month
                        (3) Year To Date
                        (4) Previous Year
                        (5) Search by Vendor
                        (0) Back
                        Enter an option (1,2,3,4,5, or 0): \
                        """);
                int option = scanner.nextInt();
                scanner.nextLine(); // Catches next line character

                // Current date to compare with entry dates
                LocalDate currentDate = LocalDate.now();

                switch (option) {
                    case 1:     // Month To Date
                        System.out.println("Ledger Entries in Month To Date: ");
                        System.out.println("date|time|description|vendor|amount");
                        for (Transactions transaction : transactionsArrayList) {
                            if (transaction.getDate().getMonth() == currentDate.getMonth() &&
                                    transaction.getDate().getYear() == currentDate.getYear()) {
                                System.out.println(transaction);
                            }
                        }
                        break;
                    case 2:     // Previous Month
                        System.out.println("Ledger Entries of the Previous Month: ");
                        System.out.println("date|time|description|vendor|amount");
                        for (Transactions transaction : transactionsArrayList) {
                            if (transaction.getDate().getMonth() == currentDate.getMonth().minus(1) &&
                                    transaction.getDate().getYear() == currentDate.getYear()) {
                                System.out.println(transaction);
                            }
                        }
                        break;
                    case 3:     // Year To Date
                        System.out.println("Ledger Entries of the Year To Date: ");
                        for (Transactions transaction : transactionsArrayList) {
                            if (transaction.getDate().getYear() == currentDate.getYear()) {
                                System.out.println(transaction);
                            }
                        }
                        break;
                    case 4:     // Previous Year
                        System.out.println("Ledger Entries of the Previous Year: ");
                        for (Transactions transaction : transactionsArrayList) {
                            if (transaction.getDate().getYear() == currentDate.minusYears(1).getYear()) {
                                System.out.println(transaction);
                            }
                        }
                        break;
                    case 5:     // Search by Vendor (display newest entries first)
                        System.out.print("To search by vendor, please enter the vendor name: ");
                        String vendorName = scanner.nextLine().trim();
                        for (Transactions transaction : transactionsArrayList) {
                            if ((transaction.getVendor().equalsIgnoreCase(vendorName))) {
                                System.out.println(transaction);
                            }
                        }
                        break;
                    case 0:     // Back
                        return;
                    default:
                        System.out.println("Invalid input. Please enter a valid option (1,2,3,4,5, or 0).");
                }
                System.out.println();
            }catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Error. Enter a numeric value from the options given. (1,2,3,4,5, or 0).\n");
            }
        }
    }
}