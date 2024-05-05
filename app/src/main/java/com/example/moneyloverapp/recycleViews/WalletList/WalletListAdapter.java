package com.example.moneyloverapp.recycleViews.WalletList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListViewHolder> {

    Context context;
    List<Wallet> walletList;

    public WalletListAdapter(Context context, List<Wallet> walletList) {
        this.context = context;
        this.walletList = walletList;
    }

    @NonNull
    @Override
    public WalletListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_wallet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListViewHolder holder, int position) {
        holder.walletName.setText(walletList.get(position).getName());
        holder.walletBalance.setText(NumberUltilities.FormatBalance(walletList.get(position).getBalance()));
        holder.imageView.setImageResource(walletList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }
}
