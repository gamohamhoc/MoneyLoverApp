package com.example.moneyloverapp.fragments;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.moneyloverapp.activities.TransactionActivity;
import com.example.moneyloverapp.database.DAO.TransactionDAO;
import com.example.moneyloverapp.database.DAO.WalletDAO;
import com.example.moneyloverapp.models.Transaction;
import com.example.moneyloverapp.recycleViews.RecentTransactions.RecentTransactionsAdapter;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListAdapter;
import com.example.moneyloverapp.ultilities.NumberUltilities;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements RecentTransactionsAdapter.TransactionListener {

    WalletDAO walletDAO;
    TransactionDAO transactionDAO;
    RecyclerView walletListRV;
    WalletListAdapter walletListAdapter;
    RecyclerView recentTransactionsRV;
    RecentTransactionsAdapter recentTransactionsAdapter;
    TextView totalBalance;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
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
        totalBalance.setText(NumberUltilities.FormatBalanceWithCurrency( walletDAO.GetById(1).getBalance()));

        GetWalletListRV();
        GetRecentTransactionsRV();

        view.findViewById(R.id.see_all_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChooseWalletActivity.class);
                startActivity(intent);
            }
        });

        getBarChart(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void getBarChart(View view) {
        // initializing variable for bar chart.
        barChart = view.findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Geeks for Geeks");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.GRAY);
        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);
    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 4));
        barEntriesArrayList.add(new BarEntry(2f, 6));
    }

    @Override
    public void onResume(){
        super.onResume();

        //update recent transaction list
        recentTransactionsAdapter.setRecentTransactions(getRecentTransaction());
        //update total balance
        totalBalance.setText(NumberUltilities.FormatBalanceWithCurrency( walletDAO.GetById(1).getBalance()));
        //update wallet list
        walletListAdapter.setWalletList(walletDAO.GetAll().subList(1,3));
    }

    void GetWalletListRV(){
        walletListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        walletListAdapter = new WalletListAdapter( walletDAO.GetAll().subList(1,3));
        walletListRV.setAdapter(walletListAdapter);
    }

    void GetRecentTransactionsRV(){
        recentTransactionsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        recentTransactionsAdapter = new RecentTransactionsAdapter(
                getRecentTransaction(), this);

        recentTransactionsRV.setAdapter(recentTransactionsAdapter);
    }

    List<Transaction> getRecentTransaction(){
        List<Transaction> recentTransactions = transactionDAO.GetAll();
        Collections.sort(recentTransactions, new Comparator<Transaction>() {
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

        return recentTransactions.subList(0,3);
    }

    @Override
    public void onTransactionClick(View view, int position) {
        Intent intent = new Intent(getActivity(), TransactionActivity.class);

        String val = "Chi tiết giao dịch";
        int transactionId = recentTransactionsAdapter.getTransasction(position).getId();
        intent.putExtra("actionBarTitle", val);
        intent.putExtra("transactionId", transactionId);

        startActivity(intent);
    }
}