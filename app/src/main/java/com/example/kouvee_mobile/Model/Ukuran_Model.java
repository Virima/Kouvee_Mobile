package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Ukuran_Model {
    @SerializedName("id_ukuran")
    private int id_ukuran;
    @SerializedName("nama_ukuran")
    private String nama_ukuran;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_ukuran_log")
    private String tanggal_tambah_ukuran_log;
    @SerializedName("tanggal_ubah_ukuran_log")
    private String tanggal_ubah_ukuran_log;
    @SerializedName("user_ukuran_log")
    private String user_ukuran_log;

    public int getId_ukuran() {
        return id_ukuran;
    }

    public void setId_ukuran(int id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getNama_ukuran() {
        return nama_ukuran;
    }

    public void setNama_ukuran(String nama_ukuran) {
        this.nama_ukuran = nama_ukuran;
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
        return tanggal_tambah_ukuran_log;
    }

    public void setTanggalTambah(String tanggal_tambah_ukuran_log) {
        this.tanggal_tambah_ukuran_log = tanggal_tambah_ukuran_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_ukuran_log;
    }

    public void setTanggalUbah(String tanggal_ubah_ukuran_log) {
        this.tanggal_ubah_ukuran_log = tanggal_ubah_ukuran_log;
    }

    public String getUser_ukuran_log() {
        return user_ukuran_log;
    }

    public void setUser_ukuran_log(String user_ukuran_log) {
        this.user_ukuran_log = user_ukuran_log;
    }
}
