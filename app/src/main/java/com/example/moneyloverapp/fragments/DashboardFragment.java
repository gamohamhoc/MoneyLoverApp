package com.example.moneyloverapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.activities.ChooseWalletActivity;
import com.example.moneyloverapp.activities.MainActivity;
import com.example.moneyloverapp.activities.SplashScreenActivity;
import com.example.moneyloverapp.activities.TransactionActivity;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.interfaces.CustomItemClickListener;
import com.example.moneyloverapp.models.Category;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.models.Wallet;
import com.example.moneyloverapp.recycleViews.RecentTransactions.RecentTransactionsAdapter;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListAdapter;
import com.example.moneyloverapp.ultilities.DateTimeUltilities;
import com.example.moneyloverapp.ultilities.NumberUltilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    TextView totalBalance;
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

        totalBalance = view.findViewById(R.id.total_balance);
        totalBalance.setText(NumberUltilities.FormatBalance( walletDAO.GetById(1).getBalance()));

        GetWalletListRV();
        GetRecentTransactionsRV();

        view.findViewById(R.id.see_all_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChooseWalletActivity.class);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    void GetWalletListRV(){
        walletListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        walletListRV.setAdapter(new WalletListAdapter(getContext(), walletDAO.GetAll().subList(1,3)));
    }

    void GetRecentTransactionsRV(){
        List<Transaction> recentTransactions = transactionDAO.GetAll();
        Collections.sort(recentTransactions, new Comparator<Transaction>() {
            public int compare(Transaction o1, Transaction o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        recentTransactionsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        RecentTransactionsAdapter adapter = new RecentTransactionsAdapter(
                getContext(),
                recentTransactions.subList(0,3),
                new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // do what ever you want to do with it
                Intent intent = new Intent(getActivity(), TransactionActivity.class);

                String val = "Chi tiết giao dịch";
                int transactionId = Integer.parseInt (((TextView)v.findViewById(R.id.id)).getText().toString());
                intent.putExtra("actionBarTitle", val);
                intent.putExtra("transactionId", transactionId);

                startActivity(intent);
            }
        });

//        recentTransactionsRV.setAdapter(new RecentTransactionsAdapter(getContext(), recentTransactions.subList(0,3)));
        recentTransactionsRV.setAdapter(adapter);
    }
}