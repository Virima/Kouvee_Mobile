package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Login_Model;
import com.example.kouvee_mobile.Model.Pegawai_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Login_Interface {
    @GET("tampilpegawai.php")
    Call<List<Pegawai_Model>> getPegawai();

    @FormUrlEncoded
    @POST("login.php")
    Call<Login_Model> loginRequest(
            @Field("username") String username,
            @Field("password") String password
    );
}
