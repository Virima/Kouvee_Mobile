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
}
