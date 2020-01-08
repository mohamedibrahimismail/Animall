package com.example.animall.Data.Remote.Models.HotelDeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelDealModel {
    @SerializedName("result")
    @Expose
    private HotelDealResult result;
    @SerializedName("statue")
    @Expose
    private String statue;

    public HotelDealResult getResult() {
        return result;
    }

    public void setResult(HotelDealResult result) {
        this.result = result;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }
}
