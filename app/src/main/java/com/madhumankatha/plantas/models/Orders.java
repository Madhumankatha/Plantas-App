package com.madhumankatha.plantas.models;

import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("Id")
    private int id;

    @SerializedName("Uid")
    private int uid;

    @SerializedName("Pid")
    private int pid;

    @SerializedName("total")
    private int total;

    @SerializedName("status")
    private String status;

    @SerializedName("CreatedAt")
    private String CreatedAt;

    @SerializedName("pidNavigation")
    private Products products;

    @SerializedName("uidNavigation")
    private Users users;


    public Orders() {
    }

    public Orders(int uid, int pid, int total, String status) {
        this.uid = uid;
        this.pid = pid;
        this.total = total;
        this.status = status;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }
}
