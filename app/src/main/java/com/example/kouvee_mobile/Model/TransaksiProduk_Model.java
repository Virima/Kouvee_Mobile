package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class TransaksiProduk_Model {
    @SerializedName("id_transaksi_produk")
    private int id_transaksi_produk;
    @SerializedName("id_detail_transaksi")
    private int id_detail_transaksi;
    @SerializedName("id_customer")
    private String id_customer;
    @SerializedName("id_produk")
    private String id_produk;
    @SerializedName("kode_transaksi_produk")
    private String kode_transaksi_produk;
    @SerializedName("tanggal_transaksi_produk")
    private String tanggal_transaksi_produk;
    @SerializedName("jumlah_detail_produk")
    private String jumlah_transaksi_produk;
    @SerializedName("subtotal_detail_produk")
    private String subtotal_transaksi_produk;
    @SerializedName("total_transaksi_produk")
    private String total_transaksi_produk;
    @SerializedName("status_transaksi_produk")
    private String status_transaksi_produk;
    @SerializedName("tanggal_tambah_transaksi_log")
    private String tanggal_tambah_transaksi_log;
    @SerializedName("tanggal_ubah_transaksi_log")
    private String tanggal_ubah_transaksi_log;
    @SerializedName("user_transaksi_add")
    private String user_transaksi_add;
    @SerializedName("user_transaksi_edit")
    private String user_transaksi_edit;
    @SerializedName("user_transaksi_delete")
    private String user_transaksi_delete;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;

    public int getId_transaksi_produk() {
        return id_transaksi_produk;
    }

    public void setId_transaksi_produk(int id_transaksi_produk) {
        this.id_transaksi_produk = id_transaksi_produk;
    }

    public int getId_detail_transaksi() {
        return id_detail_transaksi;
    }

    public void setId_detail_transaksi(int id_detail_transaksi) {
        this.id_detail_transaksi = id_detail_transaksi;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getKode_transaksi_produk() {
        return kode_transaksi_produk;
    }

    public void setKode_transaksi_produk(String kode_transaksi_produk) {
        this.kode_transaksi_produk = kode_transaksi_produk;
    }

    public String getTanggal_transaksi_produk() {
        return tanggal_transaksi_produk;
    }

    public void setTanggal_transaksi_produk(String tanggal_transaksi_produk) {
        this.tanggal_transaksi_produk = tanggal_transaksi_produk;
    }

    public String getJumlah_transaksi_produk() {
        return jumlah_transaksi_produk;
    }

    public void setJumlah_transaksi_produk(String jumlah_transaksi_produk) {
        this.jumlah_transaksi_produk = jumlah_transaksi_produk;
    }

    public String getSubtotal_transaksi_produk() {
        return subtotal_transaksi_produk;
    }

    public void setSubtotal_transaksi_produk(String subtotal_transaksi_produk) {
        this.subtotal_transaksi_produk = subtotal_transaksi_produk;
    }

    public String getTotal_transaksi_produk() {
        return total_transaksi_produk;
    }

    public void setTotal_transaksi_produk(String total_transaksi_produk) {
        this.total_transaksi_produk = total_transaksi_produk;
    }

    public String getStatus_transaksi_produk() {
        return status_transaksi_produk;
    }

    public void setStatus_transaksi_produk(String status_transaksi_produk) {
        this.status_transaksi_produk = status_transaksi_produk;
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
        return tanggal_tambah_transaksi_log;
    }

    public void setTanggalTambah(String tanggal_tambah_transaksi_log) {
        this.tanggal_tambah_transaksi_log = tanggal_tambah_transaksi_log;
    }

    public String getTanggalUbah() {
        return tanggal_ubah_transaksi_log;
    }

    public void setTanggalUbah(String tanggal_tambah_transaksi_log) {
        this.tanggal_ubah_transaksi_log = tanggal_ubah_transaksi_log;
    }

    public String getUser_transaksi_add() {
        return user_transaksi_add;
    }

    public void setUser_transaksi_add(String user_transaksi_add) {
        this.user_transaksi_add = user_transaksi_add;
    }

    public String getUser_transaksi_edit() {
        return user_transaksi_edit;
    }

    public void setUser_transaksi_edit(String user_transaksi_edit) {
        this.user_transaksi_edit = user_transaksi_edit;
    }

    public String getUser_transaksi_delete() {
        return user_transaksi_delete;
    }

    public void setUser_transaksi_delete(String user_transaksi_delete) {
        this.user_transaksi_delete = user_transaksi_delete;
    }
}
