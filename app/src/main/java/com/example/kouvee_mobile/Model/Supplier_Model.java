package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Supplier_Model {
    @SerializedName("id_supplier")
    private int id;
    @SerializedName("nama_supplier")
    private String nama_supplier;
    @SerializedName("alamat_supplier")
    private String alamat_supplier;
    @SerializedName("telepon_supplier")
    private String telepon_supplier;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("tanggal_tambah_supplier_log")
    private String tanggal_tambah_supplier_log;
    @SerializedName("tanggal_ubah_supplier_log")
    private String tanggal_ubah_supplier_log;

    public int getId_supplier() {
        return id;
    }

    public void setId_supplier(int id_supplier) {
        this.id = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getTelepon_supplier() {
        return telepon_supplier;
    }

    public void setTelepon_supplier(String telp) {
        this.telepon_supplier = telp;
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
        return tanggal_tambah_supplier_log;
    }

    public void setTanggalTambah(String tanggal_tambah_supplier_log) {
        this.tanggal_tambah_supplier_log = tanggal_tambah_supplier_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_supplier_log;
    }

    public void setTanggalUbah(String tanggal_ubah_supplier_log) {
        this.tanggal_ubah_supplier_log = tanggal_ubah_supplier_log;
    }
}
