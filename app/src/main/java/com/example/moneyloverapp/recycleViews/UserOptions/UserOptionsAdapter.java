package com.example.moneyloverapp.recycleViews.UserOptions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.activities.MainActivity;
import com.example.moneyloverapp.activities.WalletsActivity;
import com.example.moneyloverapp.fragments.UserFragment;
import com.example.moneyloverapp.recycleViews.WalletList.WalletListViewHolder;

import java.util.List;

public class UserOptionsAdapter extends RecyclerView.Adapter<UserOptionsViewHolder> {
    Context context;
    List<String> options;
    FragmentActivity activity;

    public UserOptionsAdapter(Context context, List<String> options, FragmentActivity activity) {
        this.context = context;
        this.options = options;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
        final UserOptionsViewHolder mViewHolder = new UserOptionsViewHolder(mView);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewHolder.Name.getText().toString() == "Ví của tôi"){
                    Intent intent = new Intent(activity, WalletsActivity.class);
                    activity.startActivity(intent);
                }
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOptionsViewHolder holder, int position) {
        holder.Name.setText(options.get(position));

        switch (options.get(position)){
            case "Ví của tôi":
                holder.Icon1.setImageResource(R.drawable.baseline_wallet_24);
                break;
            case "Khám phá Money Lover":
                holder.Icon1.setImageResource(R.drawable.explore_icon);
                break;
            case "Hỗ trợ":
                holder.Icon1.setImageResource(R.drawable.baseline_contact_support_24);
                break;
            case "Cài đặt":
                holder.Icon1.setImageResource(R.drawable.baseline_settings_24);
                break;
            case "Giới thiệu":
                holder.Icon1.setImageResource(R.drawable.baseline_info_24);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return options.size();
    }
}
