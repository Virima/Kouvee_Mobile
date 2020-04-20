package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Pengadaan_Model {
    @SerializedName("id_detail_pengadaan")
    private int id_detail_pengadaan;
    @SerializedName("id_pengadaan")
    private String id_pengadaan;
    @SerializedName("id_produk")
    private String id_produk;
    @SerializedName("id_supplier")
    private String id_supplier;
    @SerializedName("tanggal_pengadaan")
    private String tanggal_pengadaan;
    @SerializedName("jumlah_pengadaan")
    private String jumlah_pengadaan;
    @SerializedName("subtotal_pengadaan")
    private String subtotal_pengadaan;
    @SerializedName("status_pengadaan")
    private String status_pengadaan;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;

    public int getId() {
        return id_detail_pengadaan;
    }

    public void setId(int id_detail_pengadaan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
    }

    public String getId_pengadaan() {
        return id_pengadaan;
    }

    public void setId_pengadaan(String id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
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
}
