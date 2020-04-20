package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Produk_Model {
    @SerializedName("id_produk")
    private int id_produk;
    @SerializedName("nama_produk")
    private String nama_produk;
    @SerializedName("satuan_produk")
    private String satuan_produk;
    @SerializedName("stok_produk")
    private String stok_produk;
    @SerializedName("stok_min_produk")
    private String stok_min_produk;
    @SerializedName("harga_produk")
    private String harga_produk;
    @SerializedName("image_path")
    private String image_path;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_produk_log")
    private String tanggal_tambah_produk_log;
    @SerializedName("tanggal_ubah_produk_log")
    private String tanggal_ubah_produk_log;
    @SerializedName("user_produk_log")
    private String user_produk_log;

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getSatuan_produk() {
        return satuan_produk;
    }

    public void setSatuan_produk(String satuan_produk) {
        this.satuan_produk = satuan_produk;
    }

    public String getStok_produk() {
        return stok_produk;
    }

    public void setStok_produk(String stok_produk) {
        this.stok_produk = stok_produk;
    }

    public String getStok_min_produk() {
        return stok_min_produk;
    }

    public void setStok_min_produk(String stok_min_produk) {
        this.stok_min_produk = stok_min_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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
        return tanggal_tambah_produk_log;
    }

    public void setTanggalTambah(String tanggal_tambah_produk_log) {
        this.tanggal_tambah_produk_log = tanggal_tambah_produk_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_produk_log;
    }

    public void setTanggalUbah(String tanggal_ubah_produk_log) {
        this.tanggal_ubah_produk_log = tanggal_ubah_produk_log;
    }

    public String getUser_produk_log() {
        return user_produk_log;
    }

    public void setUser_produk_log(String user_jenis_log) {
        this.user_produk_log = user_produk_log;
    }
}
