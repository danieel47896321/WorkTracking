package com.example.worktracking.Class;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.worktracking.R;

public class PopUpMSG {
    private AlertDialog.Builder Builder;
    public PopUpMSG(Context context, String Title, String Message){
        Builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        Builder.setTitle(Title);
        Builder.setMessage(Message);
        Builder.setPositiveButton(context.getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { }
        });
        Builder.setCancelable(false);
        Builder.create().show();
    }
    public PopUpMSG(Context context, String Title, String Message, Class Destination){
        Builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        Builder.setTitle(Title);
        Builder.setMessage(Message);
        Builder.setPositiveButton(context.getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                context.startActivity(new Intent(context,Destination));
                ((Activity)context).finish();
            }
        });
        Builder.setCancelable(false);
        Builder.create().show();
    }
}