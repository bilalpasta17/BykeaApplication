package com.example.bykeaapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.UserData;
import android.widget.TextView;

import com.example.bykeaapplication.Interface.RequestUser;
import com.example.bykeaapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi extends AppCompatActivity {

    //calling whatever we created in activity
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_api);
        textView = findViewById(R.id.txt_api);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        requestUser.getUser("").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                textView.setText(response.body().data.first_name);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }
    }
