package com.example.moneyloverapp.recycleViews.MyWalletsList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.activities.WalletDetailActivity;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class MyWalletsListAdapter  extends RecyclerView.Adapter<MyWalletsListViewHolder> {
    Context context;
    List<Wallet> walletList;
    Activity activity;

    public MyWalletsListAdapter(Context context, List<Wallet> walletList) {
        this.context = context;
        this.walletList = walletList;
    }

    public MyWalletsListAdapter(Context context, List<Wallet> walletList, Activity activity) {
        this.context = context;
        this.walletList = walletList;
        this.activity = activity;
    }

    public void setWalletList(List<Wallet> wallets){
        this.walletList = wallets;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyWalletsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(context).inflate(R.layout.item_my_wallets, parent, false);
        final MyWalletsListViewHolder mViewHolder = new MyWalletsListViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WalletDetailActivity.class);
                WalletDAO walletDAO = new WalletDAO(activity);
                Wallet wallet = walletDAO.GetByName(mViewHolder.walletName.getText().toString());
                intent.putExtra("walletId", wallet.getId());

                activity.startActivity(intent);
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyWalletsListViewHolder holder, int position) {
        Wallet wallet = walletList.get(position);

        holder.walletName.setText(wallet.getName());
        holder.walletBalance.setText(NumberUltilities.FormatBalanceWithCurrency(wallet.getBalance()));
        holder.imageView.setImageResource(R.drawable.wallet_icon);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }
}
