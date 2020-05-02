package com.example.kouvee_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Login_Model {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;
    @SerializedName("hash")
    private String hash;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
