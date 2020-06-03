package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class TransaksiLayanan_Model {
    @SerializedName("id_transaksi_layanan")
    private int id_transaksi_layanan;
    @SerializedName("id_detail_transaksi")
    private int id_detail_transaksi;

    @SerializedName("id_hewan")
    private String id_hewan;
    @SerializedName("id_layanan")
    private String id_layanan;
    @SerializedName("id_jenis")
    private String id_jenis;
    @SerializedName("id_ukuran")
    private String id_ukuran;
    @SerializedName("id_customer")
    private String id_customer;
    @SerializedName("telepon_customer")
    private String telepon_customer;

    @SerializedName("kode_transaksi_layanan")
    private String kode_transaksi_layanan;
    @SerializedName("tanggal_transaksi_layanan")
    private String tanggal_transaksi_layanan;
    @SerializedName("total_transaksi_layanan")
    private String total_transaksi_layanan;
    @SerializedName("status_transaksi_layanan")
    private String status_transaksi_layanan;
    @SerializedName("jumlah_detail_layanan")
    private String jumlah_transaksi_layanan;
    @SerializedName("subtotal_detail_layanan")
    private String subtotal_transaksi_layanan;

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

    public int getId_transaksi_layanan() {
        return id_transaksi_layanan;
    }

    public void setId_transaksi_layanan(int id_transaksi_layanan) {
        this.id_transaksi_layanan = id_transaksi_layanan;
    }

    public int getId_detail_transaksi() {
        return id_detail_transaksi;
    }

    public void setId_detail_transaksi(int id_detail_transaksi) {
        this.id_detail_transaksi = id_detail_transaksi;
    }

    public String getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(String id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getKode_transaksi_layanan() {
        return kode_transaksi_layanan;
    }

    public void setKode_transaksi_layanan(String kode_transaksi_layanan) {
        this.kode_transaksi_layanan = kode_transaksi_layanan;
    }

    public String getTanggal_transaksi_layanan() {
        return tanggal_transaksi_layanan;
    }

    public void setTanggal_transaksi_layanan(String tanggal_transaksi_layanan) {
        this.tanggal_transaksi_layanan = tanggal_transaksi_layanan;
    }

    public String getTotal_transaksi_layanan() {
        return total_transaksi_layanan;
    }

    public void setTotal_transaksi_layanan(String total_transaksi_layanan) {
        this.total_transaksi_layanan = total_transaksi_layanan;
    }

    public String getStatus_transaksi_layanan() {
        return status_transaksi_layanan;
    }

    public void setStatus_transaksi_layanan(String status_transaksi_layanan) {
        this.status_transaksi_layanan = status_transaksi_layanan;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
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

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setTelepon_customer(String telepon_customer) {
        this.telepon_customer = telepon_customer;
    }

    public String getTelepon_customer() {
        return telepon_customer;
    }

    public void setId_ukuran(String id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getJumlah_transaksi_layanan() {
        return jumlah_transaksi_layanan;
    }

    public void setJumlah_transaksi_layanan(String jumlah_transaksi_layanan) {
        this.jumlah_transaksi_layanan = jumlah_transaksi_layanan;
    }

    public String getSubtotal_transaksi_layanan() {
        return subtotal_transaksi_layanan;
    }

    public void setSubtotal_transaksi_layanan(String subtotal_transaksi_layanan) {
        this.subtotal_transaksi_layanan = subtotal_transaksi_layanan;
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
