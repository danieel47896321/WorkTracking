package com.example.worktracking.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktracking.Class.MyDate;
import com.example.worktracking.Class.User;
import com.example.worktracking.R;
import com.example.worktracking.User.Home;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MyDate> dates;
    private User user;
    private EditText Hours, Minutes;
    private Calendar calendar = Calendar.getInstance();
    private int Year = calendar.get(Calendar.YEAR), Month = calendar.get(Calendar.MONTH), Day = calendar.get(Calendar.DAY_OF_MONTH), UserYear, UserMonth, UserDay;
    private TextInputLayout TextInputLayoutCompany, TextInputLayoutDate, TextInputLayoutStartTime, TextInputLayoutEndTime;
    private Button ButtonAdd, ButtonCancel, Finish;
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
        holder.floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog(dates.get(position));
            }
        });
    }
    private void EditDialog(MyDate date){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_date,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutCompany = dialogView.findViewById(R.id.TextInputLayoutCompany);
        TextInputLayoutDate = dialogView.findViewById(R.id.TextInputLayoutDate);
        TextInputLayoutStartTime = dialogView.findViewById(R.id.TextInputLayoutStartTime);
        TextInputLayoutEndTime = dialogView.findViewById(R.id.TextInputLayoutEndTime);
        TextInputLayoutCompany.getEditText().setText(date.getCompany());
        TextInputLayoutDate.getEditText().setText(date.getDate());
        TextInputLayoutStartTime.getEditText().setText(date.getStartTime());
        TextInputLayoutEndTime.getEditText().setText(date.getEndTime());
        ButtonAdd = dialogView.findViewById(R.id.ButtonAdd);
        ButtonAdd.setText(context.getResources().getString(R.string.Edit));
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DatePick();
        StartTimePick();
        EndTimePick();
        TextInputLayoutCompany.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayoutCompany.setHelperText("");
                TextInputLayoutCompany.getEditText().setText("");
            }
        });
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(TextInputLayoutCompany.getEditText().getText().toString().equals(""))
                    TextInputLayoutCompany.setHelperText(context.getResources().getString(R.string.Required));
                else
                    TextInputLayoutCompany.setHelperText("");
                if(TextInputLayoutDate.getEditText().getText().toString().equals(""))
                    TextInputLayoutDate.setHelperText(context.getResources().getString(R.string.Required));
                else if(!checkDate(UserYear,UserMonth,UserDay))
                    TextInputLayoutDate.setHelperText(context.getResources().getString(R.string.InvalidDate));
                else
                    TextInputLayoutDate.setHelperText("");
                TimeCheck(TextInputLayoutStartTime);
                TimeCheck(TextInputLayoutEndTime);
                TimePickCheck(TextInputLayoutStartTime, TextInputLayoutEndTime);
                if(!(TextInputLayoutCompany.getEditText().getText().toString().equals("")) && !(TextInputLayoutDate.getEditText().getText().toString().equals("")) && checkDate(UserYear,UserMonth,UserDay)
                        && !(TextInputLayoutEndTime.getEditText().getText().toString().equals("")) && !(TextInputLayoutStartTime.getEditText().getText().toString().equals(""))
                        && TimeCheck(TextInputLayoutStartTime) && TimeCheck(TextInputLayoutEndTime) && TimePickCheck(TextInputLayoutStartTime, TextInputLayoutEndTime)) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://worktracking-ba85c-default-rtdb.europe-west1.firebasedatabase.app");
                    DatabaseReference reference = database.getReference().child("WorkDates").child(user.getUid()).child(UserYear + "").child(getMonth(UserMonth)).child(date.getId());
                    date.setCompany(TextInputLayoutCompany.getEditText().getText().toString());
                    date.setStartTime(TextInputLayoutStartTime.getEditText().getText().toString());
                    date.setEndTime(TextInputLayoutEndTime.getEditText().getText().toString());
                    date.setYear(UserYear + "");
                    date.setMonth(UserMonth + "");
                    date.setDay(UserDay + "");
                    date.setDate(UserDay + "/" + UserMonth + "/" + UserYear);
                    reference.setValue(date);
                    alertDialog.cancel();
                }
            }
        });
    }
    private void StartTimePick(){
        TextInputLayoutStartTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog(TextInputLayoutStartTime.getEditText());
            }
        });
    }
    private void EndTimePick(){
        TextInputLayoutEndTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog(TextInputLayoutEndTime.getEditText());
            }
        });
    }
    private void TimePickerDialog(TextView Time){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timepicker_view,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Minutes = dialogView.findViewById(R.id.Minutes);
        Hours = dialogView.findViewById(R.id.Hours);
        Finish = dialogView.findViewById(R.id.Finish);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Minutes.getText().toString().length() == 0)
                    Minutes.setText("00");
                else if(Minutes.getText().toString().length() == 1) {
                    Minutes.setText("0" + Minutes.getText().toString());
                }
                if(Hours.getText().toString().length() == 0)
                    Hours.setText("00");
                else if(Hours.getText().toString().length() == 1)
                    Hours.setText("0" + Hours.getText().toString());
                Time.setText(Hours.getText().toString() + ":" + Minutes.getText().toString());
                alertDialog.cancel();
            }
        });
    }
    private void DatePick(){
        TextInputLayoutDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        UserMonth = month;
                        UserYear = year;
                        UserDay = dayOfMonth;
                        String Date = dayOfMonth + "/" + month + "/" + year;
                        TextInputLayoutDate.getEditText().setText(Date);
                    }
                },Year, Month, Day);
                datePickerDialog.show();
            }
        });
    }
    private String getMonth(int month){
        if(month == 1){
            return "January";
        }else if(month == 2){
            return "February";
        }else if(month == 3){
            return "March";
        }else if(month == 4){
            return "April";
        }else if(month == 5){
            return "May";
        }else if(month == 6){
            return "June";
        }else if(month == 7){
            return "July";
        }else if(month == 8){
            return "August";
        }else if(month == 9){
            return "September";
        }else if(month == 10){
            return "October";
        }else if(month == 11){
            return "November";
        }else if(month == 12){
            return "December";
        }
        return "January";
    }
    private Boolean TimePickCheck(TextInputLayout startTime, TextInputLayout endTime){
        if(!(TextInputLayoutEndTime.getEditText().getText().toString().equals("")) && !(TextInputLayoutStartTime.getEditText().getText().toString().equals(""))){
            if(Integer.valueOf(startTime.getEditText().getText().toString().substring(0,2)) > Integer.valueOf(endTime.getEditText().getText().toString().substring(0,2))
                    || ( Integer.valueOf(startTime.getEditText().getText().toString().substring(0,2)) == Integer.valueOf(endTime.getEditText().getText().toString().substring(0,2))
                    && Integer.valueOf(startTime.getEditText().getText().toString().substring(3,5)) > Integer.valueOf(endTime.getEditText().getText().toString().substring(3,5)))) {
                startTime.setHelperText(context.getResources().getString(R.string.StartTimeError));
                return false;
            }
        }
        return true;
    }
    private Boolean TimeCheck(TextInputLayout time){
        if(time.getEditText().getText().toString().equals("")) {
            time.setHelperText(context.getResources().getString(R.string.Required));
            return false;
        } else if(Integer.valueOf(time.getEditText().getText().toString().substring(0,2)) > 24){
            time.setHelperText(context.getResources().getString(R.string.HourError));
            return false;
        } else if(Integer.valueOf(time.getEditText().getText().toString().substring(3,5)) > 59){
            time.setHelperText(context.getResources().getString(R.string.MinutesError));
            return false;
        }  else if(Integer.valueOf(time.getEditText().getText().toString().substring(0,2)) == 24 && Integer.valueOf(TextInputLayoutStartTime.getEditText().getText().toString().substring(3,5)) > 00){
            time.setHelperText(context.getResources().getString(R.string.TimePickError));
            return false;
        }else
            time.setHelperText("");
        return true;
    }
    private boolean checkDate(int userYear, int userMonth, int userDay) {
        if(userYear > Year) {
            return false;
        } else if(userYear == Year && userMonth > (Month + 1)) {
            return false;
        } else if(userYear == Year && userMonth == (Month + 1) && userDay > Day){
            return false;
        }
        return true;
    }
    public int getItemCount() { return dates.size(); }
}
