package com.example.moneyloverapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListAdapter;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.List;

public class ChooseWalletActivity extends AppCompatActivity {

    RecyclerView chooseWalletRV;
    WalletDAO walletDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wallet);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //
        walletDAO = new WalletDAO(getApplicationContext());
        //
        chooseWalletRV = findViewById(R.id.choose_wallet_rv);
        GetWalletListRV();
        //
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int walletId = preferences.getInt("walletId", 1);
        Wallet wallet = walletDAO.GetById(walletId);
        ((TextView)findViewById(R.id.chosen_wallet_txt)).setText("Ví đang được chọn: " + wallet.getName());
        //
        Wallet globalWallet = walletDAO.GetById(1);
        ((TextView)findViewById(R.id.wallet_global_name)).setText(globalWallet.getName());
        ((TextView)findViewById(R.id.wallet_global_amount)).setText(
                NumberUltilities.FormatBalance(globalWallet.getBalance())
        );
        //
        findViewById(R.id.choose_wallet_exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    void GetWalletListRV(){
        List<Wallet> walletList = walletDAO.GetAll();

        chooseWalletRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chooseWalletRV.setAdapter(new WalletListAdapter(getApplicationContext(),
                walletList.subList(1,walletList.size()),
                this));
    }
}