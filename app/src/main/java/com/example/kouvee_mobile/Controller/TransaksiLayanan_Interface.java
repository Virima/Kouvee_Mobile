package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransaksiLayanan_Interface {
    @GET("tampiltransaksilayanan.php")
    Call<List<TransaksiLayanan_Model>> getTransaksiLayanan();

    @GET("tampiltransaksilayananbelumselesai.php")
    Call<List<TransaksiLayanan_Model>> getTransaksiLayananBelumSelesai();

    @GET("tampillayanantransaksilayanan.php")
    Call<List<TransaksiLayanan_Model>> getLayananTransaksiLayanan(
            @Query("id_transaksi_layanan") String id_transaksi_layanan
    );

    @FormUrlEncoded
    @POST("inserttransaksilayanan.php")
    Call<TransaksiLayanan_Model> createTransaksiLayanan(
            @Field("key") String key,
            @Field("id_hewan") String id_hewan,
            @Field("tanggal_transaksi_layanan") String tanggal_transaksi_layanan,
            @Field("total_transaksi_layanan") String total_transaksi_layanan,
            @Field("status_transaksi_layanan") String status_transaksi_layanan,
            @Field("user_transaksi_add") String user_transaksi_add
    );

    @FormUrlEncoded
    @POST("insertlayanantransaksilayanan.php")
    Call<TransaksiLayanan_Model> createLayananTransaksiLayanan(
            @Field("key") String key,
            @Field("id_transaksi_layanan") String id_transaksi_layanan,
            @Field("id_layanan") String id_layanan,
            @Field("jumlah_transaksi_layanan") String jumlah_transaksi_layanan,
            @Field("subtotal_transaksi_layanan") String subtotal_transaksi_layanan
    );

    @FormUrlEncoded
    @POST("edittransaksilayanan.php")
    Call<TransaksiLayanan_Model> editTransaksiLayanan(
            @Field("key") String key,
            @Field("id_transaksi_layanan") String id_transaksi_layanan,
            @Field("id_hewan") String id_hewan,
            @Field("tanggal_transaksi_layanan") String tanggal_transaksi_layanan,
            @Field("total_transaksi_layanan") String total_transaksi_layanan,
            @Field("tgl_ubah_transaksi_log") String tgl_ubah_transaksi_log,
            @Field("user_transaksi_edit") String user_transaksi_edit
    );

    @FormUrlEncoded
    @POST("editlayanantransaksilayanan.php")
    Call<TransaksiLayanan_Model> editLayananTransaksiLayanan(
            @Field("key") String key,
            @Field("id_transaksi_layanan") String id_transaksi_layanan,
            @Field("id_detail_layanan") String id_detail_layanan,
            @Field("jumlah_transaksi_layanan") String jumlah_transaksi_layanan,
            @Field("subtotal_transaksi_layanan") String subtotal_transaksi_layanan,
            @Query("subtotal_before") String subtotal_before
    );

    @FormUrlEncoded
    @POST("hapustransaksilayanan.php")
    Call<TransaksiLayanan_Model> hapusTransaksiLayanan(
            @Field("key") String key,
            @Field("id_transaksi_layanan") String id_transaksi_layanan,
            @Field("user_transaksi_delete") String user_transaksi_delete
    );

    @FormUrlEncoded
    @POST("hapuslayanantransaksilayanan.php")
    Call<TransaksiLayanan_Model> hapusLayananTransaksiLayanan(
            @Field("key") String key,
            @Field("id_transaksi_layanan") String id_transaksi_layanan,
            @Field("id_detail_layanan") String id_detail_layanan,
            @Field("subtotal_transaksi_layanan") String subtotal_transaksi_layanan
    );

    @FormUrlEncoded
    @POST("verifikasitransaksilayanan.php")
    Call<TransaksiLayanan_Model> verifikasiTransaksiLayanan(
            @Field("id_transaksi_layanan") String id_transaksi_layanan,
            @Field("status_transaksi_layanan") String status_transaksi_layanan,
            @Field("tgl_ubah_transaksi_log") String tgl_ubah_transaksi_log,
            @Field("user_transaksi_edit") String user_transaksi_edit
    );
}
