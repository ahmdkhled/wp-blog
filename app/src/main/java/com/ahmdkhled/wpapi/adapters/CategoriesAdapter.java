package com.ahmdkhled.wpapi.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ahmdkhled.wpapi.R;
import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/28/2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>{
    private ArrayList<String> categories;


    public CategoriesAdapter(ArrayList<String> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_row,parent,false);
        return new CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        holder.category.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoriesHolder extends RecyclerView.ViewHolder{
        TextView category;
        public CategoriesHolder(View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.categoryItem);
        }
    }
}
