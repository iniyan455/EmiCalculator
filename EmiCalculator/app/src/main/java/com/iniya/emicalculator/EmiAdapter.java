package com.iniya.emicalculator;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public class EmiAdapter extends RecyclerView.Adapter<EmiViewHolder> {


    private List<Emi> mArrayList;

    public EmiAdapter(List<Emi> arrayList) {
        mArrayList = arrayList;

    }

    @NonNull
    @Override
    public EmiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        return new EmiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmiViewHolder emiViewHolder, int position) {


        emiViewHolder.opening_balance.setText("" + mArrayList.get(position).getOpening_balance());
        emiViewHolder.closingbalance.setText("" + mArrayList.get(position).getClosing_balance());
        emiViewHolder.interest_balance.setText("" + mArrayList.get(position).getInterest_permonth());

        emiViewHolder.payamount.setText("" + mArrayList.get(position).getPrincipal_paidamount());
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }
}
