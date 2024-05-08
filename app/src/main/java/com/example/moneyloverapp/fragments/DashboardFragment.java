package com.example.moneyloverapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //wallet recycler view
        RecyclerView walletListRV = view.findViewById(R.id.wallet_list);

        List<Wallet> walletList = new ArrayList<Wallet>();
        walletList.add(new Wallet("Lương", 8000000));
        walletList.add(new Wallet("Bố mẹ chu cấp", 2000000));
        walletList.add(new Wallet("Học bổng", 10000000));

        walletListRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        walletListRV.setAdapter(new WalletListAdapter(view.getContext(), walletList));

        //recent transaction recycler view
        RecyclerView recentTransactionRV = view.findViewById(R.id.recent_transaction_list);

        List<Transaction> recentTransactions = new ArrayList<>();
        recentTransactions.add(new Transaction("Ăn trưa", "Ăn uống", 60000, 1, new Date(2024 - 1900, 4, 8)));
        recentTransactions.add(new Transaction("Ăn sáng", "Ăn uống", 20000, 1, new Date(2024 - 1900, 4, 8)));
        recentTransactions.add(new Transaction("Ăn sáng", "Ăn uống", 15000, 1, new Date(2024 - 1900, 4, 7)));

        recentTransactionRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recentTransactionRV.setAdapter(new RecentTransactionsAdapter(view.getContext(), recentTransactions));

        // Inflate the layout for this fragment
        return view;
    }
}