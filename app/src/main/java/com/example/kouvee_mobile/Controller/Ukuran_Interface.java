package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Ukuran_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Ukuran_Interface {
    @GET("tampilukuran.php")
    Call<List<Ukuran_Model>> getUkuran();

    @FormUrlEncoded
    @POST("editukuran.php")
    Call<Ukuran_Model> editUkuran(
            @Field("key") String key,
            @Field("id_ukuran") String id_ukuran,
            @Field("nama_ukuran") String nama_ukuran,
            @Field("tgl_ubah_ukuran_log") String tgl_ubah_ukuran_log
    );

    @FormUrlEncoded
    @POST("insertukuran.php")
    Call<Ukuran_Model> createUkuran(
            @Field("key") String key,
            @Field("nama_ukuran") String nama_ukuran
    );

    @FormUrlEncoded
    @POST("hapusukuran.php")
    Call<Ukuran_Model> hapusUkuran(
            @Field("key") String key,
            @Field("id_ukuran") String id_ukuran
    );
}
