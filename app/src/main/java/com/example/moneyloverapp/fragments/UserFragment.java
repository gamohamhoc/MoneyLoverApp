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
import com.example.moneyloverapp.recycleViews.RecentTransactions.RecentTransactionsAdapter;
import com.example.moneyloverapp.recycleViews.UserOptions.UserOptionsAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        //user options RV
        RecyclerView userOptionsRV = view.findViewById(R.id.list_options);

        List<String> options = new ArrayList<>();
        options.add("Ví của tôi");
        options.add("Khám phá Money Lover");
        options.add("Hỗ trợ");
        options.add("Cài đặt");
        options.add("Giới thiệu");

        userOptionsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        userOptionsRV.setAdapter(new UserOptionsAdapter(view.getContext(), options, getActivity()));

        return view;
    }
}