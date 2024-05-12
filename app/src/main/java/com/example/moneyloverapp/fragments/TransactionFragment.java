package com.example.moneyloverapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.models.TransactionsByDate;
import com.example.moneyloverapp.recycleViews.TransactionsByDate.TransactionsByDateAdapter;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListAdapter;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {

    RecyclerView TrnansactionsByDateRV;
    TransactionDAO transactionDAO;
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
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        //
        transactionDAO = new TransactionDAO(getContext());
        //
        TrnansactionsByDateRV = view.findViewById(R.id.transactions_by_date);
        //
        GetTransactionsByDateRV();

        // Inflate the layout for this fragment
        return view;
    }

    void GetTransactionsByDateRV(){
        TrnansactionsByDateRV.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Transaction> transactions = transactionDAO.GetAll();
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


            transactionsByDate.setTransactions(transactionList);
            transactionsByDate.setDate(dateKey);

            for (Transaction transaction : transactionList) {
                totalAmount += transaction.getAmount();
            }
            transactionsByDate.setTotalAmount(totalAmount);

            transactionsByDates.add(transactionsByDate);
        }

        TrnansactionsByDateRV.setAdapter(new TransactionsByDateAdapter(transactionsByDates));
    }
}