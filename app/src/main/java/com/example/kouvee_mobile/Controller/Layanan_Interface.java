package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Layanan_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Layanan_Interface {
    @GET("tampillayanan.php")
    Call<List<Layanan_Model>> getLayanan();

    @FormUrlEncoded
    @POST("insertlayanan.php")
    Call<Layanan_Model> createLayanan(
            @Field("key") String key,
            @Field("nama_layanan") String nama_layanan,
            @Field("harga_layanan") String harga_layanan,
            @Field("user_layanan_log") String user_layanan_log
    );

    @FormUrlEncoded
    @POST("editlayanan.php")
    Call<Layanan_Model> editLayanan(
            @Field("key") String key,
            @Field("id_layanan") String id_layanan,
            @Field("nama_layanan") String nama_layanan,
            @Field("harga_layanan") String harga_layanan,
            @Field("tgl_ubah_layanan_log") String tgl_ubah_layanan_log,
            @Field("user_layanan_log") String user_layanan_log
    );

    @FormUrlEncoded
    @POST("hapuslayanan.php")
    Call<Layanan_Model> hapusLayanan(
            @Field("key") String key,
            @Field("id_layanan") String id_layanan,
            @Field("user_layanan_log") String user_layanan_log
    );
}
