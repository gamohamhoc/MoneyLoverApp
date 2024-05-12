package com.example.moneyloverapp.recycleViews.RecentTransactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.interfaces.CustomItemClickListener;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class RecentTransactionsAdapter extends RecyclerView.Adapter<RecentTransactionsViewHolder> {

    Context context;
    List<Transaction> recentTransactions;
    CustomItemClickListener listener;

    public RecentTransactionsAdapter(Context context, List<Transaction> recentTransactions) {
        this.context = context;
        this.recentTransactions = recentTransactions;
    }

    public RecentTransactionsAdapter(Context context, List<Transaction> recentTransactions, CustomItemClickListener listener) {
        this.context = context;
        this.recentTransactions = recentTransactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecentTransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        final RecentTransactionsViewHolder mViewHolder = new RecentTransactionsViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;

//        return new RecentTransactionsViewHolder(
//                LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTransactionsViewHolder holder, int position) {
        holder.id.setText(recentTransactions.get(position).getId()+"");
        holder.category.setText(recentTransactions.get(position).getCategory());
        holder.date.setText(DateTimeUltilities.FormatDate("dd/MM/yyyy" ,recentTransactions.get(position).getDate()));
        holder.amount.setText(NumberUltilities.FormatBalance(recentTransactions.get(position).getAmount()));
        holder.imageView.setImageResource(R.drawable.wallet_icon);
    }

    @Override
    public int getItemCount() {
        return recentTransactions.size();
    }
}
