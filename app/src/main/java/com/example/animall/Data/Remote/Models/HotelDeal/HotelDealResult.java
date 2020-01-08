package com.example.animall.Data.Remote.Models.HotelDeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelDealResult {
    @SerializedName("hotelDeal")
    @Expose
    private HotelDeal hotelDeal;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public HotelDeal getHotelDeal() {
        return hotelDeal;
    }

    public void setHotelDeal(HotelDeal hotelDeal) {
        this.hotelDeal = hotelDeal;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
