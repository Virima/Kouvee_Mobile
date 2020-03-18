package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Supplier_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Supplier_Interface {
    @GET("tampilsupplier.php")
    Call<List<Supplier_Model>> getSupplier();

    @FormUrlEncoded
    @POST("insertsupplier.php")
    Call<Supplier_Model> createSupplier(
            @Field("key") String key,
            @Field("nama_supplier") String nama_supplier,
            @Field("alamat_supplier") String alamat_supplier,
            @Field("telepon_supplier") String telp
    );

    @FormUrlEncoded
    @POST("editsupplier.php")
    Call<Supplier_Model> editSupplier(
            @Field("key") String key,
            @Field("id_supplier") String id_supplier,
            @Field("nama_supplier") String nama_supplier,
            @Field("alamat_supplier") String alamat_supplier,
            @Field("telepon_supplier") String telp
    );

    @FormUrlEncoded
    @POST("hapussupplier.php")
    Call<Supplier_Model> hapusSupplier(
            @Field("key") String key,
            @Field("id_supplier") String id_supplier
    );
}
