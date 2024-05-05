package com.example.moneyloverapp.recycleViews.WalletList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;

public class WalletListViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView walletName;
    TextView walletBalance;

    public WalletListViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.wallet_image);
        walletName = itemView.findViewById(R.id.wallet_name);
        walletBalance = itemView.findViewById(R.id.wallet_balance);
    }
}
