package com.example.moneyloverapp.recycleViews.RecentTransactions;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;

public class RecentTransactionsViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView category;
    TextView date;
    TextView amount;


    public RecentTransactionsViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.transaction_img);
        category = itemView.findViewById(R.id.transaction_category);
        date = itemView.findViewById(R.id.transaction_date);
        amount = itemView.findViewById(R.id.transaction_amount);
    }
}
