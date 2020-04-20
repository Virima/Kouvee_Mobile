package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Jenis_Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Jenis_Interface {
    @GET("tampiljenis.php")
    Call<List<Jenis_Model>> getJenis();

    @FormUrlEncoded
    @POST("editjenis.php")
    Call<Jenis_Model> editJenis(
            @Field("key") String key,
            @Field("id_jenis") String id_jenis,
            @Field("nama_jenis") String nama_jenis,
            @Field("tgl_ubah_jenis_log") String tgl_ubah_jenis_log,
            @Field("user_jenis_log") String user_jenis_log
    );

    @FormUrlEncoded
    @POST("insertjenis.php")
    Call<Jenis_Model> createJenis(
            @Field("key") String key,
            @Field("nama_jenis") String nama_jenis,
            @Field("user_jenis_log") String user_jenis_log
    );

    @FormUrlEncoded
    @POST("hapusjenis.php")
    Call<Jenis_Model> hapusJenis(
            @Field("key") String key,
            @Field("id_jenis") String id_jenis,
            @Field("user_jenis_log") String user_jenis_log
    );
}
