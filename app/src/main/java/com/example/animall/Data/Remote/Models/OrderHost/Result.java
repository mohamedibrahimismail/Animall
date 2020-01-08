package com.example.animall.Data.Remote.Models.OrderHost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("orderhost")
    @Expose
    private Boolean orderhost;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public Boolean getOrderhost() {
        return orderhost;
    }

    public void setOrderhost(Boolean orderhost) {
        this.orderhost = orderhost;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
