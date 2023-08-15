package com.example.bykeaapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bykeaapplication.R;

public class MainActivity extends AppCompatActivity {

    ImageView imageView; // ImageView for splash screen animation
    private Animation blinkAnimation; // Animation for the blink effect

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ImageView for the splash screen animation
        imageView = findViewById(R.id.imageView3);

        // Load the blink animation from the animation resource file
        blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.animation);

        // Start the blink animation on the ImageView
        imageView.startAnimation(blinkAnimation);

        // Use a Handler to delay the transition to the home activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the home activity after a delay of 5000 milliseconds (5 seconds)
                startActivity(new Intent(MainActivity.this, activity_home.class));
            }
        }, 5000); // Delay time in milliseconds (5000ms = 5 seconds)
    }
}
