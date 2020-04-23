package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Customer_Model {
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("nama_customer")
    private String nama_customer;
    @SerializedName("alamat_customer")
    private String alamat_customer;
    @SerializedName("telepon_customer")
    private String telepon_customer;
    @SerializedName("tgl_lahir_customer")
    private String tgl_lahir_customer;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_customer_log")
    private String tanggal_tambah_customer_log;
    @SerializedName("tanggal_ubah_customer_log")
    private String tanggal_ubah_customer_log;
    @SerializedName("user_customer_log")
    private String user_customer_log;

    public int getIdCustomer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getTelepon_customer() {
        return telepon_customer;
    }

    public void setTelepon_customer(String telepon_customer) {
        this.telepon_customer = telepon_customer;
    }

    public String getTgl_Lahir_customer() {
        return tgl_lahir_customer;
    }

    public void setTgl_lahir_customer(String tgl_lahir_customer) {
        this.tgl_lahir_customer = tgl_lahir_customer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // LOG //
    public String getTanggalTambah() {
        return tanggal_tambah_customer_log;
    }

    public void setTanggalTambah(String tanggal_tambah_customer_log) {
        this.tanggal_tambah_customer_log = tanggal_tambah_customer_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_customer_log;
    }

    public void setTanggalUbah(String tanggal_ubah_customer_log) {
        this.tanggal_ubah_customer_log = tanggal_ubah_customer_log;
    }

    public String getUser_customer_log() {
        return user_customer_log;
    }

    public void setUser_customer_log(String user_customer_log) {
        this.user_customer_log = user_customer_log;
    }
}
