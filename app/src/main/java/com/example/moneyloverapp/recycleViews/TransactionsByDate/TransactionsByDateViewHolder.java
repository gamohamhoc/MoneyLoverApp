package com.example.moneyloverapp.recycleViews.TransactionsByDate;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;

import org.w3c.dom.Text;

public class TransactionsByDateViewHolder  extends RecyclerView.ViewHolder {

    TextView TransactionByDate_Date;
    TextView TransactionByDate_Day;
    TextView TransactionByDate_DetailDate;
    TextView TransactionByDate_TotalAmount;
    RecyclerView Transaction;

    public TransactionsByDateViewHolder(@NonNull View itemView) {
        super(itemView);

        TransactionByDate_Date = itemView.findViewById(R.id.transaction_date_date);
        TransactionByDate_Day = itemView.findViewById(R.id.transaction_date_day);
        TransactionByDate_DetailDate = itemView.findViewById(R.id.transaction_date_month_year);
        TransactionByDate_TotalAmount = itemView.findViewById(R.id.transaction_date_amount);

        Transaction = itemView.findViewById(R.id.transaction_date_transaction);
    }
}
