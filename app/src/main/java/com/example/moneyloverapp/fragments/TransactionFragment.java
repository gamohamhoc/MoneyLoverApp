package com.example.moneyloverapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.activities.ChooseWalletActivity;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.models.TransactionsByDate;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.TransactionsByDate.TransactionsByDateAdapter;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {

    RecyclerView TransactionsByDateRV;
    TransactionsByDateAdapter transactionsByDateAdapter;
    TransactionDAO transactionDAO;
    TextView totalBalanceOfWallet;
    Wallet wallet;
    int walletId;
    WalletDAO walletDAO;
    TextView walletName;
    public TransactionFragment() {
        // Required empty public constructor
    }

    public static TransactionFragment newInstance() {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        walletDAO = new WalletDAO(getContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        //
        transactionDAO = new TransactionDAO(getContext());
        //
        TransactionsByDateRV = view.findViewById(R.id.transactions_by_date);
        totalBalanceOfWallet = view.findViewById(R.id.acc_balance);
        walletName = view.findViewById(R.id.wallet_acc_name);
        //
        getWallet();
        GetTransactionsByDateRV();

        //
        view.findViewById(R.id.choose_wallet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChooseWalletActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume (){
        super.onResume();

        getWallet();

        List<TransactionsByDate> transactionsByDates = getTransactionsByDate();
        transactionsByDateAdapter.setList(transactionsByDates);
    }

    void GetTransactionsByDateRV(){
        TransactionsByDateRV.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        transactionsByDateAdapter = new TransactionsByDateAdapter(getTransactionsByDate(), getActivity());
        TransactionsByDateRV.setAdapter(transactionsByDateAdapter);
    }
    void getWallet(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        walletId = preferences.getInt("walletId", 0);
        if(walletId == 0){
            walletId = 1;
        }
        wallet = walletDAO.GetById(walletId);
        totalBalanceOfWallet.setText(NumberUltilities.FormatBalanceWithCurrency(wallet.getBalance()));
        walletName.setText(wallet.getName());
    }

    List<TransactionsByDate> getTransactionsByDate(){
        List<Transaction> transactions;
        if(walletId == 1){
            transactions = transactionDAO.GetAll();
        }else{
            transactions = transactionDAO.GetTransactionsbyWalletId(wallet.getId());
        }

        List<TransactionsByDate> transactionsByDates = new ArrayList<>();

        HashMap<Date, List<Transaction>> resultMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            Date dateKey = transaction.getDate();

            if (!resultMap.containsKey(dateKey)) {
                resultMap.put(dateKey, new ArrayList<Transaction>());
            }

            resultMap.get(dateKey).add(transaction);
        }

        float totalAmount;
        for(Date dateKey : resultMap.keySet()){
            totalAmount = 0;
            TransactionsByDate transactionsByDate = new TransactionsByDate();
            List<Transaction> transactionList = resultMap.get(dateKey);

            Collections.sort(transactionList, new Comparator<Transaction>() {
                public int compare(Transaction o1, Transaction o2) {
                    if(o2.getDate().compareTo(o1.getDate()) == 0){
                        if(o1.getId() < o2.getId()){
                            return 1;
                        };
                        return -1;
                    }
                    return o2.getDate().compareTo(o1.getDate());
                }
            });

            transactionsByDate.setTransactions(transactionList);
            transactionsByDate.setDate(dateKey);

            for (Transaction transaction : transactionList) {
                totalAmount += transaction.getAmount();
            }
            transactionsByDate.setTotalAmount(totalAmount);

            transactionsByDates.add(transactionsByDate);
        }

        Collections.sort(transactionsByDates, new Comparator<TransactionsByDate>() {
            public int compare(TransactionsByDate o1, TransactionsByDate o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return transactionsByDates;
    }
}