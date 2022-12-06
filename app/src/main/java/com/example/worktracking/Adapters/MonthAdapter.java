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

import com.example.worktracking.Class.Month;
import com.example.worktracking.Class.MyDate;
import com.example.worktracking.Class.User;
import com.example.worktracking.Class.Year;
import com.example.worktracking.R;

import java.util.ArrayList;
import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Month> months;
    private User user;
    public MonthAdapter(Context context, ArrayList<Month> months, User user) {
        this.context = context;
        this.months = months;
        this.user = user;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Month;
        ImageView YearArrow;
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Month = itemView.findViewById(R.id.Month);
            YearArrow = itemView.findViewById(R.id.YearArrow);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
    public MonthAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.month_view,parent,false);
        return new MonthAdapter.MyViewHolder(view);
    }
    @SuppressLint("ResourceType")
    public void onBindViewHolder(@NonNull MonthAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Month.setText(months.get(position).getMonth());
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*years.get(position).setClicked(!years.get(position).getClicked());
                if(years.get(position).getClicked()) {
                    holder.YearArrow.setImageResource(R.drawable.arrow_up);
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    ArrayList<MyDate> d = new ArrayList<>();
                    MonthAdapter monthAdapter = new MonthAdapter(context,d,user);
                    holder.recyclerView.setAdapter(monthAdapter);
                } else {
                    holder.YearArrow.setImageResource(R.drawable.arrow_down);
                    holder.linearLayout.setVisibility(View.GONE);
                }*/
            }
        });
    }
    public int getItemCount() { return months.size(); }
}
