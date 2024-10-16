/*
An application that a user can use to track all financial transactions for a
business or for personal use.
 */
package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AccountingLedgerApp {
    // Create a hashmap to hold transactions
    static HashMap<String, Transactions> transactionsHashMap = new HashMap<>();
    // Create an arrayList to hold transactions
    static ArrayList<Transactions> transactionsArrayList = new ArrayList<>();
    public static void main(String[] args) {

        // Create a scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Continue to run until the user chooses to exit
        while(true){
            // Home screen: Give the user options on what to do in the app
            System.out.print("""
                    Welcome to your Accounting Ledger! Please select an option to continue.
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
                    addDeposit(scanner);
                    break;
                case "P":
                    makePayment(scanner);
                    break;
                case "L":
                    ledger(scanner);
                    break;
                case "X":
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please enter a valid option(D,P,L, or X).");
                    break;
            }
        }
    }

    // Read the transactions.csv file and add transactions to the HashMap and ArrayList
    private  static void loadTransactions(HashMap<String, Transactions> transactionsHashMap, ArrayList<Transactions> transactionsArrayList){

        try{
            // Read the transactions.csv file
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            // Skip first line with the header
            bufferedReader.readLine();

            // Read the file a line at a time
            String input;
            while((input = bufferedReader.readLine()) != null){

                // Split string at each bar ( | ) character
                String[] tokens = input.split("\\|");

                // Create a transaction from each line and add it to the hashmap, description is the key
                transactionsHashMap.put(tokens[2], new Transactions(LocalDate.parse(tokens[0]), LocalTime.parse(tokens[1]),
                                                                tokens[2], tokens[3], Float.parseFloat(tokens[4])));

                // Create a transaction from each line and add it to the arrayList
                transactionsArrayList.add(new Transactions(LocalDate.parse(tokens[0]), LocalTime.parse(tokens[1]),
                                                            tokens[2], tokens[3], Float.parseFloat(tokens[4])));

            }
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Write to the transactions.csv file the transactions logged by the user (Deposit or debit)
    private static void writeToFile(Scanner scanner, String transactionType){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Write the date and time
            bufferedWriter.write(LocalDate.now() + "|");
            bufferedWriter.write(LocalTime.now().format(format) + "|");

            System.out.print("Enter the " + transactionType + " description:");
            bufferedWriter.write(scanner.nextLine().trim() + "|");

            System.out.print("Enter the vendor: ");
            bufferedWriter.write(scanner.nextLine().trim() + "|");

            System.out.print("Enter the amount: ");
            float amount = scanner.nextFloat();
            // Catch the new line character that was left by the float
            scanner.nextLine();

            // If it's a debit make the amount negative if not already
            if(transactionType.equals("debit") && amount > 0){
                amount = -amount;
            }
            // write a formatted float so that displays with 2 decimal places
            bufferedWriter.write(String.format("%.2f\n", amount));

            // Close the buffered writer
            bufferedWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Prompt user for the deposit information and save it to the csv file
    private static void addDeposit(Scanner scanner) {
        String option;
        do{
            // Write the deposit information given by the user to the file
            writeToFile(scanner,"deposit");

            // Ask user if they would like to add another deposit or exit
            System.out.print("Would you like to add another deposit? (Press any key for yes, or X to exit): ");
            option = scanner.nextLine().trim();

            // Make a new line to make more space
            System.out.println();

        }while(!option.equalsIgnoreCase("X"));
    }

    // Prompt user for the debit information and save it to the csv file
    private static void makePayment(Scanner scanner) {
        String option;
        do{
            // Write the debit information given by the user to the file
            writeToFile(scanner,"debit");

            // Ask user if they would like to add another debit or exit
            System.out.print("Would you like to add another debit? (Press any key for yes, or X to exit): ");
            option = scanner.nextLine().trim();

            // Make a new line to make more space
            System.out.println();

        }while(!option.equalsIgnoreCase("X"));
    }

    // Shows all entries (newest entries first)
    private static void ledger(Scanner scanner) {
        // Read the transactions.csv file and add transactions to the HashMap & arrayList, to read from it
        loadTransactions(transactionsHashMap, transactionsArrayList);

        while (true) {
            System.out.print("""
                   Ledger. Which entries would you like to display?
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
                    for (int i = transactionsArrayList.size() - 1; i >= 0 ; i--) {
                        System.out.println(transactionsArrayList.get(i).toString());
                    }
                    break;
                case "D":   // Display all deposits (newest entries first)
                    System.out.println("date|time|description|vendor|amount");
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if(transactionsArrayList.get(i).getAmount() > 0) {
                            System.out.println(transactionsArrayList.get(i).toString());
                        }
                    }
                    break;
                case "P":    // Display only negative entries or payments (newest entries first)
                    System.out.println("date|time|description|vendor|amount");
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if(transactionsArrayList.get(i).getAmount() < 0) {
                            System.out.println(transactionsArrayList.get(i).toString());
                        }
                    }
                    break;
                case "R":   // Run pre-defined reports or run a custom search
                    ledgerReports(scanner);
                    break;
                case "H":   // Go back to the home page
                    return;
                default:    // Let user know they entered a non-option and should try again
                    System.out.println("Invalid input. Please select one of the options(A,D,P,R, or H)");
                    break;
            }
            System.out.println();
        }
    }
    // A new screen that allows user to run pre-defined reports or run a custom search
    private static void ledgerReports(Scanner scanner) {
        while(true) {
            System.out.print("""
                    Ledger Reports. Please select a display option to continue.
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
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if(transactionsArrayList.get(i).getDate().getMonth() == currentDate.getMonth() &&
                            transactionsArrayList.get(i).getDate().getYear() == currentDate.getYear()) {
                            System.out.println(transactionsArrayList.get(i).toString());
                        }
                    }
                    break;
                case 2:     // Previous Month
                    System.out.println("Ledger Entries of the Previous Month: ");
                    System.out.println("date|time|description|vendor|amount");
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if(transactionsArrayList.get(i).getDate().getMonth() == currentDate.getMonth().minus(1) &&
                                transactionsArrayList.get(i).getDate().getYear() == currentDate.getYear()) {
                            System.out.println(transactionsArrayList.get(i).toString());
                        }
                    }
                    break;
                case 3:     // Year To Date
                    System.out.println("Ledger Entries of the Year To Date: ");
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if(transactionsArrayList.get(i).getDate().getYear() == currentDate.getYear()) {
                            System.out.println(transactionsArrayList.get(i).toString());
                        }
                    }
                    break;
                case 4:     // Previous Year
                    System.out.println("Ledger Entries of the Previous Year: ");
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if(transactionsArrayList.get(i).getDate().getYear() == currentDate.minusYears(1).getYear()) {
                            System.out.println(transactionsArrayList.get(i).toString());
                        }
                    }
                    break;
                case 5:     // Search by Vendor (display newest entries first)
                    System.out.print("To search by vendor, please enter the vendor name: ");
                    String vendorName = scanner.nextLine();
                    for(int i = transactionsArrayList.size() - 1; i >= 0 ; i--){
                        if((transactionsArrayList.get(i).getVendor().equals(vendorName))){
                            System.out.println(transactionsArrayList.get(i));
                        }
                    }
                    break;
                case 0:     // Back
                    return;
                default:
                    System.out.println("Invalid input. Please enter a valid option (1,2,3,4,5, or 0).");
            }
            System.out.println();
        }
    }
}