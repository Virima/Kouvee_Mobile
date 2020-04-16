package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Pegawai_Model {
    @SerializedName("id_pegawai")
    private int id;
    @SerializedName("id_role")
    private String id_role;
    @SerializedName("nama_pegawai")
    private String nama_pegawai;
    @SerializedName("alamat_pegawai")
    private String alamat_pegawai;
    @SerializedName("tanggal_lahir_pegawai")
    private String tanggal_lahir_pegawai;
    @SerializedName("telepon_pegawai")
    private String telepon_pegawai;
    @SerializedName("password")
    private String password;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }


    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public String getTgl_lahir_pegawai() {
        return tanggal_lahir_pegawai;
    }

    public void setTgl_lahir_pegawai(String tgl_lahir_pegawai) {
        this.tanggal_lahir_pegawai = tgl_lahir_pegawai;
    }

    public String getTelepon_pegawai() {
        return telepon_pegawai;
    }

    public void setTelepon_pegawai(String telepon_pegawai) {
        this.telepon_pegawai = telepon_pegawai;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return message;
    }

    public void setMassage(String message) {
        this.message = message;
    }

}
