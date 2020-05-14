package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Pengadaan_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Pengadaan_Interface {
    @GET("tampilpengadaan.php")
    Call<List<Pengadaan_Model>> getPengadaan();

    @GET("tampilprodukpengadaan.php")
    Call<List<Pengadaan_Model>> getProdukPengadaan(
            @Query("id_pengadaan") String id_pengadaan
    );

    @FormUrlEncoded
    @POST("insertpengadaan.php")
    Call<Pengadaan_Model> createPengadaan(
            @Field("key") String key,
            @Field("id_supplier") String id_supplier,
            @Field("tanggal_pengadaan") String tanggal_pengadaan,
            @Field("status_pengadaan") String status_pengadaan,
            @Field("total_pengadaan") String total_pengadaan,
            @Field("user_pengadaan_log") String user_pengadaan_log
    );

    @FormUrlEncoded
    @POST("insertprodukpengadaan.php")
    Call<Pengadaan_Model> createProdukPengadaan(
            @Field("key") String key,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("id_produk") String id_produk,
            @Field("jumlah_pengadaan") String jumlah_pengadaan,
            @Field("subtotal_pengadaan") String subtotal_pengadaan
    );

    @FormUrlEncoded
    @POST("editpengadaan.php")
    Call<Pengadaan_Model> editPengadaan(
            @Field("key") String key,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("id_supplier") String id_supplier,
            @Field("tanggal_pengadaan") String tanggal_pengadaan,
            @Field("status_pengadaan") String status_pengadaan,
            @Field("total_pengadaan") String total_pengadaan,
            @Field("tgl_ubah_pengadaan_log") String tgl_ubah_pengadaan_log,
            @Field("user_pengadaan_log") String user_pengadaan_log
    );

    @FormUrlEncoded
    @POST("editprodukpengadaan.php")
    Call<Pengadaan_Model> editProdukPengadaan(
            @Field("key") String key,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("id_detail_pengadaan") String id_detail_pengadaan,
            @Field("jumlah_pengadaan") String jumlah_pengadaan,
            @Field("subtotal_pengadaan") String subtotal_pengadaan,
            @Query("subtotal_before") String subtotal_before
    );

    @FormUrlEncoded
    @POST("hapuspengadaan.php")
    Call<Pengadaan_Model> hapusPengadaan(
            @Field("key") String key,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("user_pengadaan_log") String user_pengadaan_log
    );

    @FormUrlEncoded
    @POST("hapusprodukpengadaan.php")
    Call<Pengadaan_Model> hapusProdukPengadaan(
            @Field("key") String key,
            @Field("id_pengadaan") String id_pengadaan,
            @Field("id_detail_pengadaan") String id_detail_pengadaan,
            @Field("subtotal_pengadaan") String subtotal_pengadaan
    );

    @FormUrlEncoded
    @POST("verifikasipengadaan.php")
    Call<Pengadaan_Model> verifikasiPengadaan(
            @Field("id_pengadaan") String id_pengadaan,
            @Field("status_pengadaan") String status_pengadaan,
            @Field("tgl_ubah_pengadaan_log") String tgl_ubah_pengadaan_log,
            @Field("user_pengadaan_log") String user_pengadaan_log
    );
}
