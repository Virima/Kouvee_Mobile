package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Hewan_Model {
    @SerializedName("id_hewan")
    private int id_hewan;
    @SerializedName("nama_hewan")
    private String nama_hewan;
    @SerializedName("tgl_lahir_hewan")
    private String tgl_lahir_hewan;
    @SerializedName("id_jenis")
    private String id_jenis;
    @SerializedName("id_ukuran")
    private String id_ukuran;
    @SerializedName("id_customer")
    private String id_customer;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_hewan_log")
    private String tanggal_tambah_hewan_log;
    @SerializedName("tanggal_ubah_hewan_log")
    private String tanggal_ubah_hewan_log;
    @SerializedName("user_hewan_log")
    private String user_hewan_log;

    public int getIdHewan() {
        return id_hewan;
    }

    public void setId_hewan(int id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getTgl_lahir_hewan() {
        return tgl_lahir_hewan;
    }

    public void setTgl_lahir_hewan(String tgl_lahir_hewan) {
        this.tgl_lahir_hewan = tgl_lahir_hewan;
    }

    public String getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(String id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getId_ukuran() {
        return id_ukuran;
    }

    public void setId_ukuran(String id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    /////////////////////////////////////////////////////////////////
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
        return tanggal_tambah_hewan_log;
    }

    public void setTanggalTambah(String tanggal_tambah_hewan_log) {
        this.tanggal_tambah_hewan_log = tanggal_tambah_hewan_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_hewan_log;
    }

    public void setTanggalUbah(String tanggal_ubah_hewan_log) {
        this.tanggal_ubah_hewan_log = tanggal_ubah_hewan_log;
    }

    public String getUser_hewan_log() {
        return user_hewan_log;
    }

    public void setUser_hewan_log(String user_hewan_log) {
        this.user_hewan_log = user_hewan_log;
    }
}
