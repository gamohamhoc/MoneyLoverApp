package com.example.moneyloverapp.recycleViews.TransactionsByDate;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.activities.TransactionActivity;
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
    private FragmentActivity activity;

    public TransactionsByDateAdapter(List<TransactionsByDate> list, FragmentActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public void setList(List<TransactionsByDate> list){
        this.list = list;
        notifyDataSetChanged();
    }

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
        holder.TransactionByDate_TotalAmount.setText(NumberUltilities.FormatBalanceWithCurrency(transactionsByDate.getTotalAmount()));

        if(transactionsByDate.getTotalAmount() < 0){
            holder.TransactionByDate_TotalAmount.setTextColor(Color.RED);
        }else{
            holder.TransactionByDate_TotalAmount.setTextColor(Color.GREEN);
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                        holder.Transaction.getContext(), LinearLayoutManager.VERTICAL,false);

        layoutManager.setInitialPrefetchItemCount(transactionsByDate.getTransactions().size());

        RecentTransactionsAdapter childItemAdapter =
                new RecentTransactionsAdapter(transactionsByDate.getTransactions(), "description");

        childItemAdapter.setListener(new RecentTransactionsAdapter.TransactionListener() {
            @Override
            public void onTransactionClick(View view, int position) {
                Intent intent = new Intent(activity, TransactionActivity.class);
//
                String val = "Chi tiết giao dịch";
                int transactionId = Integer.parseInt (((TextView)view.findViewById(R.id.id)).getText().toString());
                intent.putExtra("actionBarTitle", val);
                intent.putExtra("transactionId", transactionId);

                activity.startActivity(intent);
            }
        });

        holder.Transaction.setLayoutManager(layoutManager);
        holder.Transaction.setAdapter(childItemAdapter);
        holder.Transaction.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
