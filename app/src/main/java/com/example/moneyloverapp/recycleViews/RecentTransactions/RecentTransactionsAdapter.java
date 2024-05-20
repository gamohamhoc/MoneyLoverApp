package com.example.moneyloverapp.recycleViews.RecentTransactions;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.CategoryDAO;
import com.example.moneyloverapp.models.Category;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;
import java.util.ListIterator;

public class RecentTransactionsAdapter extends RecyclerView.Adapter<RecentTransactionsAdapter.RecentTransactionsViewHolder> {

    List<Transaction> recentTransactions;
    TransactionListener listener;
    String type;

    public RecentTransactionsAdapter( List<Transaction> recentTransactions, TransactionListener listener) {
        this.recentTransactions = recentTransactions;
        this.listener = listener;
    }

    public RecentTransactionsAdapter( List<Transaction> recentTransactions, String type) {
        this.recentTransactions = recentTransactions;
        this.type = type;
    }

    public void setListener(TransactionListener listener){
        this.listener = listener;
    }

    public void setRecentTransactions(List<Transaction> transactions){
        recentTransactions = transactions;
        notifyDataSetChanged();
    }

    public Transaction getTransasction(int position){
        return recentTransactions.get(position);
    }

    @NonNull
    @Override
    public RecentTransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new RecentTransactionsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTransactionsViewHolder holder, int position) {
        Transaction transaction = recentTransactions.get(position);

        holder.id.setText(transaction.getId()+"");
        holder.category.setText(transaction.getCategory());
        holder.date.setText(DateTimeUltilities.FormatDate("dd/MM/yyyy" ,transaction.getDate()));
        holder.amount.setText(NumberUltilities.FormatBalanceWithCurrency(transaction.getAmount()));
        holder.imageView.setImageResource(R.drawable.wallet_icon);

        if(type == "description"){
            holder.date.setText(transaction.getDescription());
        }

        if(transaction.getAmount() < 0){//chi
            holder.amount.setTextColor(Color.RED);
        }else {//thu
            holder.amount.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return recentTransactions.size();
    }
    public class RecentTransactionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView category;
        TextView date;
        TextView amount;
        TextView id;

        public RecentTransactionsViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            imageView = itemView.findViewById(R.id.transaction_img);
            category = itemView.findViewById(R.id.transaction_category);
            date = itemView.findViewById(R.id.transaction_date);
            amount = itemView.findViewById(R.id.transaction_amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null)
                view.setOnClickListener(v -> listener.onTransactionClick(v, getAdapterPosition()));
        }
    }

    public interface TransactionListener{
        void onTransactionClick(View view, int position);
    }
}
