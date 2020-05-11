package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.TransaksiProduk_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransaksiProduk_Interface {
    @GET("tampiltransaksiproduk.php")
    Call<List<TransaksiProduk_Model>> getTransaksiProduk();

    @GET("tampilproduktransaksiproduk.php")
    Call<List<TransaksiProduk_Model>> getProdukTransaksiProduk(
            @Query("id_transaksi_produk") String id_transaksi_produk
    );

    @FormUrlEncoded
    @POST("inserttransaksiproduk.php")
    Call<TransaksiProduk_Model> createTransaksiProduk(
            @Field("key") String key,
            @Field("id_customer") String id_customer,
            @Field("tanggal_transaksi_produk") String tanggal_transaksi_produk,
            @Field("total_transaksi_produk") String total_transaksi_produk,
            @Field("user_transaksi_log") String user_transaksi_log
    );

    @FormUrlEncoded
    @POST("edittransaksiproduk.php")
    Call<TransaksiProduk_Model> editTransaksiProduk(
            @Field("key") String key,
            @Field("id_transaksi_produk") String id_transaksi_produk,
            @Field("id_customer") String id_customer,
            @Field("tanggal_transaksi_produk") String tanggal_transaksi_produk,
            @Field("total_transaksi_produk") String total_transaksi_produk,
            @Field("tgl_ubah_transaksi_log") String tgl_ubah_transaksi_log,
            @Field("user_transaksi_log") String user_transaksi_log
    );

    @FormUrlEncoded
    @POST("hapustransaksiproduk.php")
    Call<TransaksiProduk_Model> hapusTransaksiProduk(
            @Field("key") String key,
            @Field("id_transaksi_produk") String id_transaksi_produk,
            @Field("user_transaksi_log") String user_transaksi_log
    );
}
