package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Pengadaan_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Pengadaan_Interface {
    @GET("tampilpengadaan.php")
    Call<List<Pengadaan_Model>> getPengadaan();

    @FormUrlEncoded
    @POST("insertpengadaan.php")
    Call<Pengadaan_Model> createPengadaan(
            @Field("key") String key,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("id_produk") String id_produk,
            @Field("id_supplier") String id_supplier,
            @Field("tanggal_pengadaan") String tanggal_pengadaan,
            @Field("jumlah_pengadaan") String jumlah_pengadaan,
            @Field("subtotal_pengadaan") String subtotal_pengadaan,
            @Field("status_pengadaan") String status_pengadaan
    );

    @FormUrlEncoded
    @POST("editpengadaan.php")
    Call<Pengadaan_Model> editPengadaan(
            @Field("key") String key,
            @Field("id_detail_pengadaan") String id_detail_pengadaan,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("id_produk") String id_produk,
            @Field("id_supplier") String id_supplier,
            @Field("tanggal_pengadaan") String tanggal_pengadaan,
            @Field("jumlah_pengadaan") String jumlah_pengadaan,
            @Field("subtotal_pengadaan") String subtotal_pengadaan,
            @Field("status_pengadaan") String status_pengadaan
    );

    @FormUrlEncoded
    @POST("hapuspengadaan.php")
    Call<Pengadaan_Model> hapusPengadaan(
            @Field("key") String key,
            @Field("id_detail_pengadaan") String id_detail_pengadaan
    );
}
