package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Login_Model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Login_Interface {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login_Model> loginRequest(
            @Field("username") String username,
            @Field("password") String password
    );
}
