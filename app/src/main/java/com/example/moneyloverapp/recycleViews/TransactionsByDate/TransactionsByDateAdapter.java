package com.example.moneyloverapp.recycleViews.TransactionsByDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.models.TransactionsByDate;
import com.example.moneyloverapp.recycleViews.RecentTransactions.RecentTransactionsAdapter;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class TransactionsByDateAdapter extends RecyclerView.Adapter<TransactionsByDateViewHolder> {

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();

    private List<TransactionsByDate> list;


    public TransactionsByDateAdapter(List<TransactionsByDate> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TransactionsByDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_transactions_by_date, parent, false);

        return new TransactionsByDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsByDateViewHolder holder, int position) {
        TransactionsByDate transactionsByDate = list.get(position);

        String date="";
        if(transactionsByDate.getDate().getDate() < 10){
            date = "0" + transactionsByDate.getDate().getDate();
        }else{
            date = transactionsByDate.getDate().getDate() + "";
        }

        holder.TransactionByDate_Date.setText(date);

        holder.TransactionByDate_Day.setText(DateTimeUltilities.IntToDay( transactionsByDate.getDate().getDay()));
        holder.TransactionByDate_DetailDate.setText("tháng " + (transactionsByDate.getDate().getMonth()+1) + " " + (transactionsByDate.getDate().getYear()+1900));
        holder.TransactionByDate_TotalAmount.setText(NumberUltilities.FormatBalance(transactionsByDate.getTotalAmount()));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                        holder.Transaction.getContext(), LinearLayoutManager.VERTICAL,false);

        layoutManager.setInitialPrefetchItemCount(transactionsByDate.getTransactions().size());

        RecentTransactionsAdapter childItemAdapter
                = new RecentTransactionsAdapter(holder.Transaction.getContext() ,transactionsByDate.getTransactions());
        holder.Transaction.setLayoutManager(layoutManager);
        holder.Transaction.setAdapter(childItemAdapter);
        holder.Transaction.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
