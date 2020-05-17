package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransaksiLayanan_Interface {
    @GET("tampiltransaksilayanan.php")
    Call<List<TransaksiLayanan_Model>> getTransaksiLayanan();

    @GET("tampillayanantransaksilayanan.php")
    Call<List<TransaksiLayanan_Model>> getLayananTransaksiLayanan(
            @Query("id_transaksi_layanan") String id_transaksi_layanan
    );
}
