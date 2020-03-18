package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Layanan_Model {
    @SerializedName("id_layanan")
    private int id_layanan;
    @SerializedName("nama_layanan")
    private String nama_layanan;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;

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
}
