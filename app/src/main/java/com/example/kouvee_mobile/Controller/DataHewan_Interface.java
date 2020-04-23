package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Hewan_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

////BELOMMMMMMMMMMMMM JADIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII////

public interface DataHewan_Interface {
    @GET("tampilhewan.php")
    Call<List<Hewan_Model>> getHewan();

    @FormUrlEncoded
    @POST("inserthewan.php")
    Call<Hewan_Model> createHewan(
            @Field("key") String key,
            @Field("nama_hewan") String nama_hewan,
            @Field("tgl_lahir_hewan") String tgl_lahir_hewan,
            @Field("id_jenis") String id_jenis,
            @Field("id_ukuran") String id_ukuran,
            @Field("id_customer") String id_customer,
            @Field("user_hewan_log") String user_hewan_log
    );

    @FormUrlEncoded
    @POST("edithewan.php")
    Call<Hewan_Model> editHewan(
            @Field("key") String key,
            @Field("id_hewan") String id_hewan,
            @Field("nama_hewan") String nama_hewan,
            @Field("tgl_lahir_hewan") String tgl_lahir_hewan,
            @Field("id_jenis") String id_jenis,
            @Field("id_ukuran") String id_ukuran,
            @Field("id_customer") String id_customer,
            @Field("tgl_ubah_hewan_log") String tgl_ubah_hewan_log,
            @Field("user_hewan_log") String user_hewan_log
    );

    @FormUrlEncoded
    @POST("hapushewan.php")
    Call<Hewan_Model> hapusHewan(
            @Field("key") String key,
            @Field("id_hewan") String id_hewan,
            @Field("user_hewan_log") String user_hewan_log
    );
}
