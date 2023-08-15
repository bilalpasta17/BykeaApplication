package com.example.bykeaapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bykeaapplication.Adapter.Adapter;
import com.example.bykeaapplication.Model.Model;
import com.example.bykeaapplication.R;

import java.util.ArrayList;

public class Khareed_LaoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;

    ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khareed_lao);

        // Initialize the back arrow ImageView and set its click listener
        back_arrow = findViewById(R.id.img_back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Go back when back arrow is clicked
            }
        });

        // Initialize the RecyclerView and set its layout manager
        recyclerView = findViewById(R.id.RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Create a list of data items for the RecyclerView
        ArrayList<Model> data = new ArrayList<>();
        data.add(new Model(R.drawable.naan, "Eat"));
        data.add(new Model(R.drawable.drinks, "Drinks"));
        data.add(new Model(R.drawable.icecream, "Ice Cream"));
        data.add(new Model(R.drawable.paan, "Paan Shop"));
        data.add(new Model(R.drawable.chips, "Chips & Nimco"));
        data.add(new Model(R.drawable.milkpowder, "Baby"));
        data.add(new Model(R.drawable.bakery, "Bakery"));
        data.add(new Model(R.drawable.dairy, "Dairy"));

        // Initialize the adapter and set it to the RecyclerView
        adapter = new Adapter(getApplicationContext(), data);
        recyclerView.setAdapter(adapter);
    }
}
