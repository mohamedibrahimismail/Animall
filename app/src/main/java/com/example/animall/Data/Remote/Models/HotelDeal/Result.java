package com.example.animall.Data.Remote.Models.HotelDeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("hotelDeal")
    @Expose
    private List<HotelDeal> hotelDeal = null;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public List<HotelDeal> getHotelDeal() {
        return hotelDeal;
    }

    public void setHotelDeal(List<HotelDeal> hotelDeal) {
        this.hotelDeal = hotelDeal;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
