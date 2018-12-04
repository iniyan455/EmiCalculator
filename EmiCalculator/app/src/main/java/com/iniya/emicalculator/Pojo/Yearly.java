package com.iniya.emicalculator.Pojo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Yearly implements Serializable {
    private static final long serialVersionUID = -7788619177798333712L;


    private double opening_balance;
    private double total_emiamount;
    private double interest_permonth;
    private double principal_paidamount;
    private double closing_balance;
    private double duration;
    private Date createdDate;
    private List<Monthly> monthly;


    public double getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(double opening_balance) {
        this.opening_balance = opening_balance;
    }

    public double getTotal_emiamount() {
        return total_emiamount;
    }

    public void setTotal_emiamount(double total_emiamount) {
        this.total_emiamount = total_emiamount;
    }

    public double getInterest_permonth() {
        return interest_permonth;
    }

    public void setInterest_permonth(double interest_permonth) {
        this.interest_permonth = interest_permonth;
    }

    public double getPrincipal_paidamount() {
        return principal_paidamount;
    }

    public void setPrincipal_paidamount(double principal_paidamount) {
        this.principal_paidamount = principal_paidamount;
    }

    public double getClosing_balance() {
        return closing_balance;
    }

    public void setClosing_balance(double closing_balance) {
        this.closing_balance = closing_balance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Monthly> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<Monthly> monthly) {
        this.monthly = monthly;
    }

    public Yearly(double opening_balance, double total_emiamount, double interest_permonth, double principal_paidamount, double closing_balance, double duration, Date createdDate, List<Monthly> monthly) {
        this.opening_balance = opening_balance;
        this.total_emiamount = total_emiamount;
        this.interest_permonth = interest_permonth;
        this.principal_paidamount = principal_paidamount;
        this.closing_balance = closing_balance;
        this.duration = duration;
        this.createdDate = createdDate;
        this.monthly = monthly;
    }
}
