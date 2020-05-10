package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.TransaksiProduk_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransaksiProduk_Interface {
    @GET("tampiltransaksiproduk.php")
    Call<List<TransaksiProduk_Model>> getTransaksiProduk();

    @GET("tampilproduktransaksiproduk.php")
    Call<List<TransaksiProduk_Model>> getProdukTransaksiProduk(
            @Query("id_transaksi_produk") String id_transaksi_produk
    );
}
