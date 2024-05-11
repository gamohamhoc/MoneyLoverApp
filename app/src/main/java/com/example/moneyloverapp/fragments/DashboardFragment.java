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
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.RecentTransactions.RecentTransactionsAdapter;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    WalletDAO walletDAO;
    TransactionDAO transactionDAO;
    RecyclerView walletListRV;
    RecyclerView recentTransactionsRV;
    public DashboardFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //tao moi DAO object
        walletDAO = new WalletDAO(getContext());
        transactionDAO = new TransactionDAO(getContext());
        //get recycler view
        walletListRV = view.findViewById(R.id.wallet_list);
        recentTransactionsRV = view.findViewById(R.id.recent_transaction_list);

        GetWalletListRV();
        GetRecentTransactionsRV();
        // Inflate the layout for this fragment
        return view;
    }

    void GetWalletListRV(){
        walletListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        walletListRV.setAdapter(new WalletListAdapter(getContext(), walletDAO.GetAll().subList(1,3)));
    }

    void GetRecentTransactionsRV(){
        recentTransactionsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        recentTransactionsRV.setAdapter(new RecentTransactionsAdapter(getContext(), transactionDAO.GetAll()));
    }
}