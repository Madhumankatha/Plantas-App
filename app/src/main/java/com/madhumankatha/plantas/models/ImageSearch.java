package com.madhumankatha.plantas.models;

import com.google.gson.annotations.SerializedName;

import java.sql.ResultSet;

public class ImageSearch {
    @SerializedName("image")
    private String image;

    @SerializedName("file")
    private String file;

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
