package com.example.animall.OrderCycle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class orderArray implements Serializable {
    @SerializedName("orderArray")
    @Expose
    private List<ProductOrderModel> orderArray;

    public orderArray() {}

    public orderArray(List<ProductOrderModel> orderArray) {
        this.orderArray = orderArray;
    }

    public List<ProductOrderModel> getOrderArray() {
        return orderArray;
    }

    public void setOrderArray(List<ProductOrderModel> orderArray) {
        this.orderArray = orderArray;
    }
}
