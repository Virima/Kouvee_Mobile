package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Pengadaan_Model {
    @SerializedName("id_pengadaan")
    private int id_pengadaan;
    @SerializedName("id_detail_pengadaan")
    private int id_detail_pengadaan;
    @SerializedName("id_produk")
    private String id_produk;
    @SerializedName("satuan_produk")
    private String satuan_produk;
    @SerializedName("id_supplier")
    private String id_supplier;
    @SerializedName("telepon_supplier")
    private String telepon_supplier;
    @SerializedName("alamat_supplier")
    private String alamat_supplier;
    @SerializedName("kode_pengadaan")
    private String kode_pengadaan;
    @SerializedName("tanggal_pengadaan")
    private String tanggal_pengadaan;
    @SerializedName("jumlah_pengadaan")
    private String jumlah_pengadaan;
    @SerializedName("subtotal_pengadaan")
    private String subtotal_pengadaan;
    @SerializedName("status_pengadaan")
    private String status_pengadaan;
    @SerializedName("total_pengadaan")
    private String total_pengadaan;
    @SerializedName("tanggal_tambah_pengadaan_log")
    private String tanggal_tambah_pengadaan_log;
    @SerializedName("tanggal_ubah_pengadaan_log")
    private String tanggal_ubah_pengadaan_log;
    @SerializedName("user_pengadaan_log")
    private String user_pengadaan_log;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;

    public int getId_pengadaan() {
        return id_pengadaan;
    }

    public void setId_pengadaan(int id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public int getId_detail_pengadaan() {
        return id_detail_pengadaan;
    }

    public void setId_detail_pengadaan(int id_detail_pengadaan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getSatuan_produk() {
        return satuan_produk;
    }

    public void setSatuan_produk(String satuan_produk) {
        this.satuan_produk = satuan_produk;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getTelepon_supplier() {
        return telepon_supplier;
    }

    public void setTelepon_supplier(String telepon_supplier) {
        this.telepon_supplier = telepon_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getKode_pengadaan() {
        return kode_pengadaan;
    }

    public void setKode_pengadaan(String kode_pengadaan) {
        this.kode_pengadaan = kode_pengadaan;
    }

    public String getTanggal_pengadaan() {
        return tanggal_pengadaan;
    }

    public void setTanggal_pengadaan(String tanggal_pengadaan) {
        this.tanggal_pengadaan = tanggal_pengadaan;
    }

    public String getJumlah_pengadaan() {
        return jumlah_pengadaan;
    }

    public void setJumlah_pengadaan(String jumlah_pengadaan) {
        this.jumlah_pengadaan = jumlah_pengadaan;
    }

    public String getSubtotal_pengadaan() {
        return subtotal_pengadaan;
    }

    public void setSubtotal_pengadaan(String subtotal_pengadaan) {
        this.subtotal_pengadaan = subtotal_pengadaan;
    }

    public String getStatus_pengadaan() {
        return status_pengadaan;
    }

    public void setStatus_pengadaan(String status_pengadaan) {
        this.status_pengadaan = status_pengadaan;
    }

    public String getTotal_pengadaan() {
        return total_pengadaan;
    }

    public void setTotal_pengadaan(String total_pengadaan) {
        this.total_pengadaan = total_pengadaan;
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

    public void setMessage(String massage) {
        this.message = message;
    }

    // LOG //
    public String getTanggalTambah() {
        return tanggal_tambah_pengadaan_log;
    }

    public void setTanggalTambah(String tanggal_tambah_pengadaan_log) {
        this.tanggal_tambah_pengadaan_log = tanggal_tambah_pengadaan_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_pengadaan_log;
    }

    public void setTanggalUbah(String tanggal_ubah_pengadaan_log) {
        this.tanggal_ubah_pengadaan_log = tanggal_ubah_pengadaan_log;
    }

    public String getUser_pengadaan_log() {
        return user_pengadaan_log;
    }

    public void setUser_pengadaan_log(String user_pengadaan_log) {
        this.user_pengadaan_log = user_pengadaan_log;
    }
}
