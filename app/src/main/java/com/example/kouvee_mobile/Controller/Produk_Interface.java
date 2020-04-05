package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Produk_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Produk_Interface {
    @GET("tampilproduk.php")
    Call<List<Produk_Model>> getProduk();

    @GET("tampilprodukhampirhabis.php")
    Call<List<Produk_Model>> getProdukHampirHabis();

    @FormUrlEncoded
    @POST("insertproduk.php")
    Call<Produk_Model> createProduk(
            @Field("key") String key,
            @Field("nama_produk") String nama_produk,
            @Field("satuan_produk") String satuan_produk,
            @Field("stok_produk") String stok_produk,
            @Field("stok_min_produk") String stok_min_produk,
            @Field("harga_produk") String harga_produk,
            @Field("image_path") String image_path
    );

    @FormUrlEncoded
    @POST("editproduk.php")
    Call<Produk_Model> editProduk(
            @Field("key") String key,
            @Field("id_produk") String id_produk,
            @Field("nama_produk") String nama_produk,
            @Field("satuan_produk") String satuan_produk,
            @Field("stok_produk") String stok_produk,
            @Field("stok_min_produk") String stok_min_produk,
            @Field("harga_produk") String harga_produk,
            //@Field("image_name") String image_name,
            //@Field("image_path") String image_path,
            @Field("tanggal_ubah_produk_log") String tanggal_ubah_produk_log
    );

    @FormUrlEncoded
    @POST("hapusproduk.php")
    Call<Produk_Model> hapusProduk(
            @Field("key") String key,
            @Field("id_produk") String id_produk
    );
}
