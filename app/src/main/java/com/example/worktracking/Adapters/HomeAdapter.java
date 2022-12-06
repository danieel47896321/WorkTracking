package com.example.worktracking.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktracking.Class.User;
import com.example.worktracking.Class.Year;
import com.example.worktracking.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private List<Year> years;
    private User user;
    public HomeAdapter(Context context, List<Year> years, User user) {
        this.context = context;
        this.years = years;
        this.user = user;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Year;
        View view;
        RecyclerView recyclerView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Year = itemView.findViewById(R.id.Year);
            view = itemView.findViewById(R.id.view);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.year_view,parent,false);
        return new HomeAdapter.MyViewHolder(view);
    }
    @SuppressLint("ResourceType")
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setMonths(position);
        holder.Year.setText(years.get(position).getYear());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years.get(position).setClicked(!years.get(position).getClicked());
                if(years.get(position).getClicked()) {
                    holder.view.setVisibility(View.VISIBLE);
                    holder.recyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.view.setVisibility(View.GONE);
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }
    private void setMonths(int position){


    }
    public int getItemCount() { return years.size(); }
}
