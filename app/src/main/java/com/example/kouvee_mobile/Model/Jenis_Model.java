package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Jenis_Model {
    @SerializedName("id_jenis")
    private int id_jenis;
    @SerializedName("nama_jenis")
    private String nama_jenis;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_jenis_log")
    private String tanggal_tambah_jenis_log;
    @SerializedName("tanggal_ubah_jenis_log")
    private String tanggal_ubah_jenis_log;

    public int getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(int id_jenis) {
        this.id_jenis = id_jenis;
    }

    public String getNama_jenis() {
        return nama_jenis;
    }

    public void setNama_jenis(String nama_jenis) {
        this.nama_jenis = nama_jenis;
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
        return tanggal_tambah_jenis_log;
    }

    public void setTanggalTambah(String tanggal_tambah_jenis_log) {
        this.tanggal_tambah_jenis_log = tanggal_tambah_jenis_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_jenis_log;
    }

    public void setTanggalUbah(String tanggal_ubah_jenis_log) {
        this.tanggal_ubah_jenis_log = tanggal_ubah_jenis_log;
    }
}
