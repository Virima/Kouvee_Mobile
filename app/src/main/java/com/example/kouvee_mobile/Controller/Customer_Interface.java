package com.example.kouvee_mobile.Controller;

import com.example.kouvee_mobile.Model.Customer_Model;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Customer_Interface {
    @GET("tampilcustomer.php")
    Call<List<Customer_Model>> getCustomer();

    @FormUrlEncoded
    @POST("insertcustomer.php")
    Call<Customer_Model> createCustomer(
            @Field("key") String key,
            @Field("nama_customer") String nama_customer,
            @Field("alamat_customer") String alamat_customer,
            @Field("telepon_customer") String telepon_customer,
            @Field("tgl_lahir_customer") String tgl_lahir_customer,
            @Field("user_customer_log") String user_customer_log
    );

    @FormUrlEncoded
    @POST("editcustomer.php")
    Call<Customer_Model> editCustomer(
            @Field("key") String key,
            @Field("id_customer") String id_customer,
            @Field("nama_customer") String nama_customer,
            @Field("alamat_customer") String alamat_customer,
            @Field("telepon_customer") String telepon_customer,
            @Field("tgl_lahir_customer") String tgl_lahir_customer,
            @Field("tgl_ubah_customer_log") String tgl_ubah_customer_log,
            @Field("user_customer_log") String user_customer_log
    );

    @FormUrlEncoded
    @POST("hapuscustomer.php")
    Call<Customer_Model> hapusCustomer(
            @Field("key") String key,
            @Field("id_customer") String id_customer,
            @Field("user_customer_log") String user_customer_log
    );
}
