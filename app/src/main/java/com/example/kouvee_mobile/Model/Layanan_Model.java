package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Layanan_Model {
    @SerializedName("id_layanan")
    private int id_layanan;
    @SerializedName("nama_layanan")
    private String nama_layanan;
    @SerializedName("harga_layanan")
    private String harga_layanan;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_layanan_log")
    private String tanggal_tambah_layanan_log;
    @SerializedName("tanggal_ubah_layanan_log")
    private String tanggal_ubah_layanan_log;
    @SerializedName("user_layanan_log")
    private String user_layanan_log;

    public int getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(int id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public String getHarga_layanan() {
        return harga_layanan;
    }

    public void setHarga_layanan(String harga_layanan) {
        this.harga_layanan = harga_layanan;
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
        return tanggal_tambah_layanan_log;
    }

    public void setTanggalTambah(String tanggal_tambah_layanan_log) {
        this.tanggal_tambah_layanan_log = tanggal_tambah_layanan_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_layanan_log;
    }

    public void setTanggalUbah(String tanggal_ubah_layanan_log) {
        this.tanggal_ubah_layanan_log = tanggal_ubah_layanan_log;
    }

    public String getUser_layanan_log() {
        return user_layanan_log;
    }

    public void setUser_layanan_log(String user_layanan_log) {
        this.user_layanan_log = user_layanan_log;
    }
}
