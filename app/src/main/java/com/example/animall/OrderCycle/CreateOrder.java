package com.example.animall.OrderCycle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateOrder implements Serializable {
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("orderArray")
    @Expose
    private com.example.animall.OrderCycle.orderArray orderArray;

    public CreateOrder() {}

    public CreateOrder(String access_token, String user_id, com.example.animall.OrderCycle.orderArray orderArray) {
        this.access_token = access_token;
        this.user_id = user_id;
        this.orderArray = orderArray;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public com.example.animall.OrderCycle.orderArray getOrderArray() {
        return orderArray;
    }

    public void setOrderArray(com.example.animall.OrderCycle.orderArray orderArray) {
        this.orderArray = orderArray;
    }
}
