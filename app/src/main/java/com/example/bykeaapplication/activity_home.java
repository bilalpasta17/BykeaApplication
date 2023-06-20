package com.example.bykeaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class activity_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


//        Dialog dialog=new Dialog(activity_home.this);
//        dialog.setContentView(R.layout.alert);
//        dialog.findViewById(R.id.button).setOnClickListener(b->{
//            Toast.makeText(activity_home.this,"Done",Toast.LENGTH_LONG).show();
//            dialog.setCancelable(false);
//            dialog.dismiss();
//        });
//        dialog.setCancelable(false);
//        dialog.show();


        AlertDialog.Builder builder = new AlertDialog.Builder(activity_home.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> {
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

            Toast.makeText(this, "Logout UnSuccessful", Toast.LENGTH_SHORT).show();
        });

        builder.show();


    }
}