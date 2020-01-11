package com.example.animall.OrderCycle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductOrderModel implements Serializable {
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("qty")
    @Expose
    private String qty;

    public ProductOrderModel() {}

    public ProductOrderModel(String product_id, String qty) {
        this.product_id = product_id;
        this.qty = qty;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
