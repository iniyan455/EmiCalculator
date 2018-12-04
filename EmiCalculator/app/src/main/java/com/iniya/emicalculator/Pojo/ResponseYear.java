package com.iniya.emicalculator.Pojo;

import java.util.List;

public class ResponseYear {

    private List<com.iniya.emicalculator.Pojo.Yearly> yearly;
    private double total_Interest ;
    private double payable_Amount ;
    private double monthly_EMI ;
    private double interest_Percentage;
    public ResponseYear(List<com.iniya.emicalculator.Pojo.Yearly> yearly, double total_Interest, double payable_Amount, double monthly_EMI, double interest_Percentage) {
        this.yearly = yearly;
        this.total_Interest = total_Interest;
        this.payable_Amount = payable_Amount;
        this.monthly_EMI = monthly_EMI;
        this.interest_Percentage = interest_Percentage;
    }

    public List<com.iniya.emicalculator.Pojo.Yearly> getYearly() {
        return yearly;
    }

    public void setYearly(List<com.iniya.emicalculator.Pojo.Yearly> yearly) {
        this.yearly = yearly;
    }

    public double getTotal_Interest() {
        return total_Interest;
    }

    public void setTotal_Interest(double total_Interest) {
        this.total_Interest = total_Interest;
    }

    public double getPayable_Amount() {
        return payable_Amount;
    }

    public void setPayable_Amount(double payable_Amount) {
        this.payable_Amount = payable_Amount;
    }

    public double getMonthly_EMI() {
        return monthly_EMI;
    }

    public void setMonthly_EMI(double monthly_EMI) {
        this.monthly_EMI = monthly_EMI;
    }

    public double getInterest_Percentage() {
        return interest_Percentage;
    }

    public void setInterest_Percentage(double interest_Percentage) {
        this.interest_Percentage = interest_Percentage;
    }
}

