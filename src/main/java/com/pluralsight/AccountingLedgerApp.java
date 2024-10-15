/*
An application that a user can use to track all financial transactions for a
business or for personal use.
 */
package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class AccountingLedgerApp {

    // Create a hashmap to hold transactions
    static HashMap<String, Transactions> transactions = new HashMap<>();
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

            String option = scanner.nextLine();

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
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please enter a valid option(D,P,L, or X).");
                    break;
            }
        }

    }

    // Write to file
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
            bufferedWriter.write(scanner.nextFloat() + "\n");
            // Catch the new line character
            scanner.nextLine();

            // Close the buffered writer
            bufferedWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Prompt user for the deposit information and save it to the csv file
    private static void addDeposit(Scanner scanner) {
        String transactionType = "deposit";

        String option;
        do{
            // Write the deposit information given by the user to the file
            writeToFile(scanner,transactionType);

            // Ask user if they would like to add another deposit or exit
            System.out.print("Would you like to add another deposit? (Press any key for yes, or X to exit): ");
            option = scanner.nextLine();

            // Make a new line to make more space
            System.out.println();

        }while(!option.equalsIgnoreCase("X"));
    }

    // Prompt user for the debit information and save it to the csv file
    private static void makePayment(Scanner scanner) {
        String transactionType = "debit";

        String option;
        do{
            // Write the debit information given by the user to the file
            writeToFile(scanner,transactionType);

            // Ask user if they would like to add another debit or exit
            System.out.print("Would you like to add another debit? (Press any key for yes, or X to exit): ");
            option = scanner.nextLine();

            // Make a new line to make more space
            System.out.println();

        }while(!option.equalsIgnoreCase("X"));
    }

    // Shows all entries (newest entries first)
    private static void ledger(Scanner scanner) {

        while(true) {
            System.out.print("""
                    Ledger. Which entries would you like to display?
                    (A) All
                    (D) Deposits
                    (P) Payments
                    (R) Reports
                    (H) Home
                    Please select an option (A,D,P,R, or H): \
                    """);
            String option = scanner.nextLine();

            switch (option.toUpperCase()) {
                case "A":

                    break;
                case "D":
                    break;
                case "P":
                    break;
                case "R":
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Invalid input. Please select one of the options(A,D,P,R, or H)");
                    break;
            }

        }
    }
}