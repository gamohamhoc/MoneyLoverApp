package com.example.moneyloverapp.recycleViews.UserOptions;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneyloverapp.R;

public class UserOptionsViewHolder  extends RecyclerView.ViewHolder {
    ImageView Icon1;
    TextView Name;

    public UserOptionsViewHolder(@NonNull View itemView) {
        super(itemView);

        Icon1 = itemView.findViewById(R.id.option_icon_1);
        Name = itemView.findViewById(R.id.option_name);
    }
}
