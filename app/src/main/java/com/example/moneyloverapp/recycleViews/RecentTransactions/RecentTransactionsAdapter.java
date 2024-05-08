package com.example.moneyloverapp.recycleViews.RecentTransactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListViewHolder;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class RecentTransactionsAdapter extends RecyclerView.Adapter<RecentTransactionsViewHolder> {

    Context context;
    List<Transaction> recentTransactions;

    public RecentTransactionsAdapter(Context context, List<Transaction> recentTransactions) {
        this.context = context;
        this.recentTransactions = recentTransactions;
    }

    @NonNull
    @Override
    public RecentTransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentTransactionsViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTransactionsViewHolder holder, int position) {
        holder.category.setText(recentTransactions.get(position).getCategory());
        holder.date.setText(DateTimeUltilities.FormatDate("dd/MM/yyyy" ,recentTransactions.get(position).getCreatedDate()));
        holder.amount.setText(NumberUltilities.FormatBalance(recentTransactions.get(position).getAmount()));
        holder.imageView.setImageResource(R.drawable.wallet_icon);
    }

    @Override
    public int getItemCount() {
        return recentTransactions.size();
    }
}
