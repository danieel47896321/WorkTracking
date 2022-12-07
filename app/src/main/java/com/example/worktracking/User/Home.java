package com.example.worktracking.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worktracking.Adapters.HomeAdapter;
import com.example.worktracking.Class.Loading;
import com.example.worktracking.Class.Month;
import com.example.worktracking.Class.MyDate;
import com.example.worktracking.Class.User;
import com.example.worktracking.Class.MyYear;
import com.example.worktracking.MainActivity;
import com.example.worktracking.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Home extends AppCompatActivity {
    private ImageView BackIcon;
    private TextInputLayout TextInputLayoutCompany, TextInputLayoutDate, TextInputLayoutStartTime, TextInputLayoutEndTime;
    private Button ButtonAddDate, ButtonRemoveDate, ButtonAdd, ButtonCancel;
    private FloatingActionButton floatingActionButtonOpen;
    private ExtendedFloatingActionButton floatingActionButtonAdd, floatingActionButtonRemove;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private Boolean isOpen = false;
    private Loading loading;
    private Dialog dialog;
    private ArrayList<MyYear> myYears;
    private Context context;
    private TextView Title;
    private User user = new User();
    private RecyclerView recyclerView;
    private Intent intent;
    private Calendar calendar = Calendar.getInstance();
    private int Year = calendar.get(Calendar.YEAR), Month = calendar.get(Calendar.MONTH), Day = calendar.get(Calendar.DAY_OF_MONTH), UserYear, UserMonth, UserDay;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    public void init(){
        setID();
        SignOutIcon();
        setAddAndRemove();
        setTags();
    }
    private void setID(){
        intent = getIntent();
        myYears = new ArrayList<>();
        user = (User)intent.getSerializableExtra("user");
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.Home));
        floatingActionButtonOpen = findViewById(R.id.floatingActionButtonOpen);
        floatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        floatingActionButtonRemove = findViewById(R.id.floatingActionButtonRemove);
        recyclerView = findViewById(R.id.recyclerView);
        BackIcon = findViewById(R.id.BackIcon);
        BackIcon.setImageResource(R.drawable.signout);
        BackIcon.setRotation(180);
    }
    private void setTags(){
        loading = new Loading(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://worktracking-ba85c-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference reference = database.getReference().child("WorkDates").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myYears.clear();
                int indexYear = 0, indexMonth = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    myYears.add(new MyYear(Integer.valueOf(data.getKey())));
                    for(DataSnapshot month : snapshot.child(data.getKey()).getChildren()) {
                        myYears.get(indexYear).AddMonth(new Month(month.getKey()));
                        for (DataSnapshot day : snapshot.child(data.getKey()).child(month.getKey()).getChildren()) {
                            MyDate myDate = day.getValue(MyDate.class);
                            myYears.get(indexYear).getMonths().get(indexMonth).getDates().add(myDate);
                        }
                        indexMonth++;
                    }
                    Collections.sort(myYears.get(indexYear).getMonths());
                    indexYear++;
                    indexMonth = 0;
                }
                Collections.sort(myYears);
                loading.stop();
                ShowTags(myYears);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    private void ShowTags(ArrayList<MyYear> HomeList){
        HomeAdapter homeAdapter = new HomeAdapter(this,HomeList,user);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(homeAdapter);
    }
    private void setAddAndRemove(){
        context = this;
        floatingActionButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                rotateOpen = AnimationUtils.loadAnimation(context,R.anim.rotate_open);
                rotateClose = AnimationUtils.loadAnimation(context,R.anim.rotate_close);
                fromBottom = AnimationUtils.loadAnimation(context,R.anim.from_bottom);
                toBottom = AnimationUtils.loadAnimation(context,R.anim.to_bottom);
                if (isOpen) {
                    floatingActionButtonAdd.setVisibility(View.VISIBLE);
                    floatingActionButtonRemove.setVisibility(View.VISIBLE);
                    floatingActionButtonAdd.setAnimation(fromBottom);
                    floatingActionButtonRemove.setAnimation(fromBottom);
                    floatingActionButtonOpen.setAnimation(rotateOpen);
                    floatingActionButtonAdd.setClickable(true);
                    floatingActionButtonRemove.setClickable(true);
                    floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddDateDialog();
                        }
                    });
                    floatingActionButtonRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //RemoveDateDialog();
                        }
                    });
                } else {
                    floatingActionButtonAdd.setVisibility(View.INVISIBLE);
                    floatingActionButtonRemove.setVisibility(View.INVISIBLE);
                    floatingActionButtonAdd.setAnimation(toBottom);
                    floatingActionButtonRemove.setAnimation(toBottom);
                    floatingActionButtonOpen.setAnimation(rotateClose);
                    floatingActionButtonAdd.setClickable(false);
                    floatingActionButtonRemove.setClickable(false);
                }
            }
        });
    }
    private void AddDateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_date,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutCompany = dialogView.findViewById(R.id.TextInputLayoutCompany);
        TextInputLayoutDate = dialogView.findViewById(R.id.TextInputLayoutDate);
        TextInputLayoutStartTime = dialogView.findViewById(R.id.TextInputLayoutStartTime);
        TextInputLayoutEndTime = dialogView.findViewById(R.id.TextInputLayoutEndTime);
        ButtonAdd = dialogView.findViewById(R.id.ButtonAdd);
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DatePick();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ArrayList<MyDate> myDates = new ArrayList<>();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://worktracking-ba85c-default-rtdb.europe-west1.firebasedatabase.app");
                DatabaseReference reference = database.getReference().child("WorkDates").child(user.getUid()).child(UserYear + "").child(getMonth(UserMonth));
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myDates.clear();
                        for( DataSnapshot data : snapshot.getChildren()){
                            MyDate date = data.getValue(MyDate.class);
                            myDates.add(date);
                        }
                        myDates.add(new MyDate(UserMonth + "", TextInputLayoutCompany.getEditText().getText().toString(), UserDay + "", UserYear + "", "10:00", "18:00"));
                        reference.setValue(myDates);
                        alertDialog.cancel();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
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
    private void DatePick(){
        TextInputLayoutDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
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
    private void SignOutIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle(getResources().getString(R.string.SignOut)).setMessage(getResources().getString(R.string.AreYouSure)).setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(firebaseAuth.getCurrentUser() != null)
                            firebaseAuth.signOut();
                        startActivity(new Intent(Home.this, MainActivity.class));
                        finish();
                    }
                }).setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }).show();
    }
}