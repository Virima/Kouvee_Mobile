package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Pegawai_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Pegawai_Interface {
    @GET("tampilpegawai.php")
    Call<List<Pegawai_Model>> getPegawai();
}
