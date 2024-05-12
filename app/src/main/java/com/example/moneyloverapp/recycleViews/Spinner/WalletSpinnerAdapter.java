package com.example.moneyloverapp.recycleViews.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.models.Category;
import com.example.moneyloverapp.models.Wallet;

import java.util.List;

public class WalletSpinnerAdapter extends ArrayAdapter<Wallet> {
    private Context context;
    private List<Wallet> wallets;
    TextView walletId;
    TextView walletName;

    public WalletSpinnerAdapter(@NonNull Context context, List<Wallet> lists) {
        super(context,  0, lists);
        this.context = context;
        this.wallets = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_wallet_spinner, null);
        }
        final Wallet item = wallets.get(position);
        if(item != null) {
            walletId = v.findViewById(R.id.sp_wallet_id);
            walletId.setText(item.getId()+"");
            walletName = v.findViewById(R.id.sp_wallet_name);
            walletName.setText(item.getName());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_wallet_spinner, null);
        }
        final Wallet item = wallets.get(position);
        if(item != null) {
            walletId = v.findViewById(R.id.sp_wallet_id);
            walletId.setText(item.getId()+"");
            walletName = v.findViewById(R.id.sp_wallet_name);
            walletName.setText(item.getName());
        }
        return v;
    }
}
