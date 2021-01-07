package com.madhumankatha.plantas.models;

import com.google.gson.annotations.SerializedName;

public class Users {
    /*
    *  "id": 1,
        "username": "madhu",
        "password": "madhu",
        "phone": "8147887534",
        "address": "#8 2nd main",
        "email": "madhumankatha@gmail.com",
    * */

    @SerializedName("id")
    private long id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;


    public Users() {
    }

    public Users(long id, String username, String password, String phone, String address, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
