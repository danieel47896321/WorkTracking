package com.example.worktracking.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        TextView Year, NumberOfMonths;
        ImageView YearArrow;
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Year = itemView.findViewById(R.id.Year);
            NumberOfMonths = itemView.findViewById(R.id.NumberOfMonths);
            YearArrow = itemView.findViewById(R.id.YearArrow);
            linearLayout = itemView.findViewById(R.id.linearLayout);
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
        holder.Year.setText(years.get(position).getYear());
        holder.NumberOfMonths.setText(context.getResources().getString(R.string.NumberOfMonths) + " " + years.get(position).getMonths().size());
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                years.get(position).setClicked(!years.get(position).getClicked());
                if(years.get(position).getClicked()) {
                    holder.YearArrow.setImageResource(R.drawable.arrow_up);
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    MonthAdapter monthAdapter = new MonthAdapter(context,years.get(position).getMonths(),user);
                    holder.recyclerView.setAdapter(monthAdapter);
                } else {
                    holder.YearArrow.setImageResource(R.drawable.arrow_down);
                    holder.linearLayout.setVisibility(View.GONE);
                }
            }
        });
    }
    public int getItemCount() { return years.size(); }
}
