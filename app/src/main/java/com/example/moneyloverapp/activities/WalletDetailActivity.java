package com.example.moneyloverapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Category;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.ultilities.NumberUltilities;

public class WalletDetailActivity extends AppCompatActivity {

    int walletId;
    WalletDAO walletDAO;
    Wallet globalWallet;
    TransactionDAO transactionDAO;
    EditText WalletNameInput;
    EditText WalletAmountInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);

        walletDAO = new WalletDAO(this);
        transactionDAO = new TransactionDAO(this);
        globalWallet = walletDAO.GetById(1);

        WalletAmountInput = findViewById(R.id.amount_input);
        WalletNameInput = findViewById(R.id.wallet_name_input);
        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        findViewById(R.id.wallet_detail_exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            walletId = intent.getIntExtra("walletId", 0);
            if(walletId == 0){
                ((TextView)findViewById(R.id.wallet_content)).setText("Thêm ví");
            }else{
                ((TextView)findViewById(R.id.wallet_content)).setText("Sửa ví");
                Wallet wallet = walletDAO.GetById(walletId);
                WalletNameInput.setText(wallet.getName());
                WalletAmountInput.setText(wallet.getBalance() + "");
            }
        }

        findViewById(R.id.wallet_detail_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(walletId != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WalletDetailActivity.this);

                    builder.setMessage("Confirm delete this wallet? All the transaction from this wallet will be deleted too!!")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Wallet deleteWallet = walletDAO.GetById(walletId);
                                    globalWallet.setBalance(globalWallet.getBalance() - deleteWallet.getBalance());

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    int currentChosenWalletId = preferences.getInt("walletId", 0);
                                    if(walletId == currentChosenWalletId){
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putInt("walletId", 1);
                                        editor.apply();
                                    }

                                    walletDAO.Delete(deleteWallet);
                                    walletDAO.Update(globalWallet);
                                    transactionDAO.DeletebyWalletId(walletId);

                                    finish();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });

                    AlertDialog deleteConfirmDiaglog = builder.create();
                    deleteConfirmDiaglog.show();
                }
            }
        });

        findViewById(R.id.wallet_detail_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(walletId == 0){
                    if(WalletAmountInput.getText().toString().trim().length() == 0 ||
                            WalletNameInput.getText().toString().trim().length() == 0){
                        Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }else{
                        try{
                            float amount = Float.parseFloat(WalletAmountInput.getText().toString().trim());
                            if(amount < 0){
                                throw new Exception();
                            }
                            Wallet wallet = new Wallet();
                            wallet.setBalance(amount);
                            wallet.setName(WalletNameInput.getText().toString().trim());
                            wallet.setIsIncluded(1);

                            globalWallet.setBalance(globalWallet.getBalance() + amount);

                            walletDAO.Add(wallet);
                            walletDAO.Update(globalWallet);
                            finish();

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Vui lòng điền số tiền hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    try{
                        float amount = Float.parseFloat(WalletAmountInput.getText().toString().trim());
                        if(amount < 0){
                            throw new Exception();
                        }
                        Wallet wallet = walletDAO.GetById(walletId);

                        float oldBalance = wallet.getBalance();

                        wallet.setBalance(amount);
                        wallet.setName(WalletNameInput.getText().toString().trim());
                        wallet.setIsIncluded(1);

                        globalWallet.setBalance(globalWallet.getBalance() + amount - oldBalance);

                        walletDAO.Update(wallet);
                        walletDAO.Update(globalWallet);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Vui lòng điền số tiền hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}