package com.example.bykeaapplication.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bykeaapplication.R;
import com.example.bykeaapplication.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class activity_home extends AppCompatActivity {

    ActivityHomeBinding binding;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        //tool bar
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // Click listener for "Delivery" card
        binding.cardDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the DeliveryActivity when the "Delivery" card is clicked
                startActivity(new Intent(activity_home.this, DeliveryActivity.class));
            }
        });

        // Click listener for "Ride" card
        binding.rideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MapsActivity when the "Ride" card is clicked
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        // Click listener for "Khareed Lao" card
        binding.khareedLaoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Khareed_LaoActivity when the "Khareed Lao" card is clicked
                startActivity(new Intent(activity_home.this, Khareed_LaoActivity.class));
            }
        });

        // Create an AlertDialog for Logout confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_home.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to Logout?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> {
            // Handle logout with a toast message
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
            // You can add your logout logic here
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            // Handle canceling logout with a toast message
            Toast.makeText(this, "Logout Unsuccessful", Toast.LENGTH_SHORT).show();
        });

        // Show the AlertDialog when the activity starts
        builder.show();
    }

}
