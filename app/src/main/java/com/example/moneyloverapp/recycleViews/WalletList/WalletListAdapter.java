package com.example.moneyloverapp.recycleViews.WalletList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.activities.MainActivity;
import com.example.moneyloverapp.activities.TransactionActivity;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.interfaces.CustomItemClickListener;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.RecentTransactions.RecentTransactionsViewHolder;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListViewHolder> {

    Context context;
    List<Wallet> walletList;
    Activity activity;

    public WalletListAdapter(Context context, List<Wallet> walletList) {
        this.context = context;
        this.walletList = walletList;
    }

    public WalletListAdapter(Context context, List<Wallet> walletList, Activity activity) {
        this.context = context;
        this.walletList = walletList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public WalletListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new WalletListViewHolder(
//                LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false));

        View mView = LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false);
        final WalletListViewHolder mViewHolder = new WalletListViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                int walletId = new WalletDAO(context).GetByName(mViewHolder.walletName.getText().toString()).getId();
                editor.putInt("walletId", walletId);
                editor.apply();

                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListViewHolder holder, int position) {
        holder.walletName.setText(walletList.get(position).getName());
        holder.walletBalance.setText(NumberUltilities.FormatBalance(walletList.get(position).getBalance()));
        holder.imageView.setImageResource(R.drawable.wallet_icon);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }
}
