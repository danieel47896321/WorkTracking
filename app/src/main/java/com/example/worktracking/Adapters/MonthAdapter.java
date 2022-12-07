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
import com.example.worktracking.R;

import java.util.ArrayList;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Month> months;
    private ArrayList<Boolean> isClicked;
    private User user;
    public MonthAdapter(Context context, ArrayList<Month> months, User user) {
        this.context = context;
        this.months = months;
        this.user = user;
        isClicked = new ArrayList<>();
        for(int i=0; i < months.size(); i++)
            isClicked.add(false);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Month, NumberOfDays;
        ImageView YearArrow;
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Month = itemView.findViewById(R.id.Month);
            NumberOfDays = itemView.findViewById(R.id.NumberOfDays);
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
        holder.Month.setText(getMonth(months.get(position).getMonth()));
        holder.NumberOfDays.setText(context.getResources().getString(R.string.NumberOfDays) + " " + months.get(position).getDates().size());
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClicked.set(position,!isClicked.get(position));
                if(isClicked.get(position)) {
                    holder.YearArrow.setImageResource(R.drawable.arrow_up);
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    ArrayList<MyDate> d = new ArrayList<>();
                    //MonthAdapter monthAdapter = new MonthAdapter(context,d,user);
                   // holder.recyclerView.setAdapter(monthAdapter);
                } else {
                    holder.YearArrow.setImageResource(R.drawable.arrow_down);
                    holder.linearLayout.setVisibility(View.GONE);
                }
            }
        });
    }
    private String getMonth(String month){
        if(month.equals("January")){
            return context.getResources().getString(R.string.January);
        }else if(month.equals("February")){
            return context.getResources().getString(R.string.February);
        }else if(month.equals("March")){
            return context.getResources().getString(R.string.March);
        }else if(month.equals("April")){
            return context.getResources().getString(R.string.April);
        }else if(month.equals("May")){
            return context.getResources().getString(R.string.May);
        }else if(month.equals("June")){
            return context.getResources().getString(R.string.June);
        }else if(month.equals("July")){
            return context.getResources().getString(R.string.July);
        }else if(month.equals("August")){
            return context.getResources().getString(R.string.August);
        }else if(month.equals("September")){
            return context.getResources().getString(R.string.September);
        }else if(month.equals("October")){
            return context.getResources().getString(R.string.October);
        }else if(month.equals("November")){
            return context.getResources().getString(R.string.November);
        }else if(month.equals("December")){
            return context.getResources().getString(R.string.December);
        }
        return context.getResources().getString(R.string.January);
    }
    public int getItemCount() { return months.size(); }
}
