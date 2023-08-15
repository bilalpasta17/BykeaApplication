package com.example.bykeaapplication.Interface;

import android.service.autofill.UserData;

import com.example.bykeaapplication.Activity.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestUser  {
    @GET("/api/users/{uid}")
    Call<User> getUser(@Path("uid")String uid);
    // @path correspond to uid string uid





}
