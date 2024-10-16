package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transactions {
    // Data fields
    LocalDate date;
    LocalTime time;
    String description;
    String vendor;
    float amount;

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, float amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    // Methods
    @Override
    public String toString(){
        // state explicitly to display seconds field, and 2 decimal places for amount, even if they are 00
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        return this.date + "|" + this.time.format(timeFormat) + "|" + this.description +
                        "|" + this.vendor + "|" + String.format("%.2f",this.amount);
    }
}