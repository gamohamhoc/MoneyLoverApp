package com.example.moneyloverapp.recycleViews.MyWalletsList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;

public class MyWalletsListViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView walletName;
    TextView walletBalance;

    public MyWalletsListViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.wallet_image1);
        walletName = itemView.findViewById(R.id.wallet_name1);
        walletBalance = itemView.findViewById(R.id.wallet_balance1);
    }
}
