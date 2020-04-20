package com.example.kouvee_mobile.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_client {
    private static final String BASE_URL = "http://192.168.1.6:8181/api_android/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient() {

        if (retrofit==null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
