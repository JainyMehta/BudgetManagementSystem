package com.example.budgetmanagementsystem.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementsystem.Models.Budget;
import com.example.budgetmanagementsystem.Models.BudgetProgress;
import com.example.budgetmanagementsystem.R;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private List<BudgetProgress> listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(List<BudgetProgress> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recyclerview_budget, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BudgetProgress myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getName());
        holder.pb.setMax(listdata.get(position).getBudget());
        holder.pb.setProgress(listdata.get(position).getProgress());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pb;
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.pb = (ProgressBar) itemView.findViewById(R.id.simpleProgressBar);
            this.textView = (TextView) itemView.findViewById(R.id.categoryName);
        }
    }
}
