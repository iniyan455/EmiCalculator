package com.iniya.emicalculator;

import com.iniya.emicalculator.Emi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmiFormula {
    /**
     * To Find Per Year Calculation For EMI
     **/


    public static double emi_CalculatorFormula(double p, double r, double t) {
        double emi;

        r = r / (12 * 100);
        t = t * 12;
        emi = (p * r * (float) Math.pow(1 + r, t))
                / (float) (Math.pow(1 + r, t) - 1);

        return (emi);
    }




    public static List<Emi> emiInterestCalculateMonthly(double p, double r, double emi, double duration) {

        List<Emi> monthlyEmiList=new ArrayList<>();
        double temp = p;
        double initialamount, interest, actual_amount;


        for (int i = 1; i <= duration; i++) {

            initialamount = (temp * (r / 100) / 12);
            interest = emi - initialamount;
            actual_amount = temp - interest;

            monthlyEmiList.add(new Emi(Math.round(temp), Math.round(emi * 12), Math.round(initialamount), Math.round(interest), Math.round(actual_amount), i, new Date()));

            temp = actual_amount;


        }
        return monthlyEmiList;
    }



}
