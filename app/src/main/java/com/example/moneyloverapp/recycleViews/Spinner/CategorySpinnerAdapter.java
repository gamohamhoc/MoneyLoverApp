package com.example.moneyloverapp.recycleViews.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moneyloverapp.R;
import com.example.moneyloverapp.models.Category;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {

    private Context context;
    private List<Category> categories;
    TextView CateId;
    TextView CateName;

    public CategorySpinnerAdapter(@NonNull Context context, List<Category> lists) {
        super(context,  0, lists);
        this.context = context;
        this.categories = lists;
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_category_spinner, null);
        }
        final Category item = categories.get(position);
        if(item != null) {
            CateId = v.findViewById(R.id.category_id);
            CateId.setText(item.getId()+"");
            CateName = v.findViewById(R.id.category_name);
            CateName.setText(item.getName());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_category_spinner, null);
        }
        final Category item = categories.get(position);
        if(item != null) {
            CateId = v.findViewById(R.id.category_id);
            CateId.setText(item.getId() +". ");
            CateName = v.findViewById(R.id.category_name);
            CateName.setText(item.getName());
        }
        return v;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
