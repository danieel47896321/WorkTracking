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

import com.example.worktracking.Class.MyDate;
import com.example.worktracking.Class.User;
import com.example.worktracking.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MyDate> dates;
    private User user;
    public DayAdapter(Context context, ArrayList<MyDate> dates, User user) {
        this.context = context;
        this.dates = dates;
        this.user = user;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView CompanyName;
        TextView Date;
        TextView StartTime;
        TextView EndTime;
        TextView TotalWorkTime;
        ExtendedFloatingActionButton floatingActionButtonEdit;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CompanyName = itemView.findViewById(R.id.CompanyName);
            Date = itemView.findViewById(R.id.Date);
            StartTime = itemView.findViewById(R.id.StartTime);
            EndTime = itemView.findViewById(R.id.EndTime);
            TotalWorkTime = itemView.findViewById(R.id.TotalWorkTime);
            floatingActionButtonEdit = itemView.findViewById(R.id.floatingActionButtonEdit);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public DayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.workday_view,parent,false);
        return new DayAdapter.MyViewHolder(view);
    }
    @SuppressLint("ResourceType")
    public void onBindViewHolder(@NonNull DayAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.CompanyName.setText(context.getResources().getString(R.string.Company) + ": " + dates.get(position).getCompany());
        holder.Date.setText(context.getResources().getString(R.string.Date) + ": " + dates.get(position).getDate());
        holder.StartTime.setText(context.getResources().getString(R.string.StartTime) + ": " + dates.get(position).getStartTime());
        holder.EndTime.setText(context.getResources().getString(R.string.EndTime) + ": " + dates.get(position).getEndTime());
        String str = (Integer.valueOf(dates.get(position).getEndTime().substring(0,2)) - Integer.valueOf(dates.get(position).getStartTime().substring(0,2))) + "";
        String rest = (Integer.valueOf(dates.get(position).getEndTime().substring(3,5)) - Integer.valueOf(dates.get(position).getStartTime().substring(3,5))) + "";
        if((Integer.valueOf(dates.get(position).getEndTime().substring(3,5)) - Integer.valueOf(dates.get(position).getStartTime().substring(3,5))) < 0) {
            rest = (60 + (Integer.valueOf(dates.get(position).getEndTime().substring(3, 5)) - Integer.valueOf(dates.get(position).getStartTime().substring(3, 5)))) + "";
            str = ((Integer.valueOf(dates.get(position).getEndTime().substring(0,2)) - Integer.valueOf(dates.get(position).getStartTime().substring(0,2))) - 1) + "";
        }
        if(str.length() == 1)
            str = 0 + str;
        str += ":";
        if(rest.length() == 1)
            rest = 0 + rest;
        str += rest;
        if(Integer.valueOf(str.substring(0,2)) > 00)
            holder.TotalWorkTime.setText(context.getResources().getString(R.string.TotalWorkTime) + ": " + str + " " + context.getResources().getString(R.string.Hours));
        else
            holder.TotalWorkTime.setText(context.getResources().getString(R.string.TotalWorkTime) + ": " + str + " " + context.getResources().getString(R.string.Minutes));
    }
    public int getItemCount() { return dates.size(); }
}
