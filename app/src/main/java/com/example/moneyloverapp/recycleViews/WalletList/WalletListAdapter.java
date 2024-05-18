package com.example.moneyloverapp.recycleViews.WalletList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListAdapter.WalletListViewHolder> {

    List<Wallet> walletList;
    ChooseWalletListener chooseWalletListener;

    public WalletListAdapter(List<Wallet> walletList) {
        this.walletList = walletList;
    }

    public void setWalletList(List<Wallet> wallets){
        this.walletList = wallets;
        notifyDataSetChanged();
    }

    public Wallet getWallet(int position){
        return walletList.get(position);
    }

    public void setChooseWalletListener(ChooseWalletListener chooseWalletListener){
        this.chooseWalletListener = chooseWalletListener;
    }

    @NonNull
    @Override
    public WalletListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallet, parent, false);
        final WalletListViewHolder mViewHolder = new WalletListViewHolder(mView);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListViewHolder holder, int position) {
        holder.walletName.setText(walletList.get(position).getName());
        holder.walletBalance.setText(NumberUltilities.FormatBalanceWithCurrency(walletList.get(position).getBalance()));
        holder.imageView.setImageResource(R.drawable.wallet_icon);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public class WalletListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView walletName;
        TextView walletBalance;

        public WalletListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.wallet_image);
            walletName = itemView.findViewById(R.id.wallet_name);
            walletBalance = itemView.findViewById(R.id.wallet_balance);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(chooseWalletListener != null)
                chooseWalletListener.OnWalletClick(view, getAdapterPosition());
        }
    }

    public interface ChooseWalletListener{
        void OnWalletClick(View view, int position);
    }
}
