package com.example.worktracking.User;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worktracking.Adapters.HomeAdapter;
import com.example.worktracking.Adapters.MonthAdapter;
import com.example.worktracking.Class.Loading;
import com.example.worktracking.Class.Month;
import com.example.worktracking.Class.MyDate;
import com.example.worktracking.Class.PopUpMSG;
import com.example.worktracking.Class.User;
import com.example.worktracking.Class.Year;
import com.example.worktracking.MainActivity;
import com.example.worktracking.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Response;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
    private ArrayList<Year> years;
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
        years = new ArrayList<>();
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
        years.add(new Year("2022"));
        years.add(new Year("2021"));
        years.add(new Year("2020"));
        years.add(new Year("2019"));
        years.get(0).AddMonth(new Month("Jan"));
        years.get(0).AddMonth(new Month("March"));
        years.get(1).AddMonth(new Month("Sep"));
        ShowTags(years);
    }
    private void ShowTags(ArrayList<Year> HomeList){
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

            }
        });
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