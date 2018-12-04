package com.iniya.emicalculator;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EmiViewHolder extends RecyclerView.ViewHolder {


    public TextView opening_balance, closingbalance, interest_balance, payamount;

    public EmiViewHolder(@NonNull View itemView) {
        super(itemView);
        opening_balance = (TextView) itemView.findViewById(R.id.openingbalance);
        closingbalance = (TextView) itemView.findViewById(R.id.closingbalance);
        interest_balance = (TextView) itemView.findViewById(R.id.interest);
        payamount = (TextView) itemView.findViewById(R.id.payprincipal);


    }
}
