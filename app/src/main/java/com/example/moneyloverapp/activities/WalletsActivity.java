package com.example.moneyloverapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PermissionInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.MyWalletsList.MyWalletsListAdapter;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListAdapter;

import java.util.List;

public class WalletsActivity extends AppCompatActivity {

    RecyclerView myWalletsRV;
    WalletDAO walletDAO;
    MyWalletsListAdapter myWalletsListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallets_actitvity);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        walletDAO = new WalletDAO(this);
        myWalletsRV = findViewById(R.id.my_wallet_list);

        getMyWalletsRV();

        findViewById(R.id.my_wallet_exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.wallet_detail_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletsActivity.this, WalletDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        List<Wallet> walletList = walletDAO.GetAll();
        myWalletsListAdapter.setWalletList(walletList.subList(1,walletList.size()));
    }

    private void getMyWalletsRV() {
        List<Wallet> walletList = walletDAO.GetAll();

        myWalletsListAdapter = new MyWalletsListAdapter(getApplicationContext(),
                walletList.subList(1,walletList.size()),
                this);
        myWalletsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myWalletsRV.setAdapter(myWalletsListAdapter);
    }
}