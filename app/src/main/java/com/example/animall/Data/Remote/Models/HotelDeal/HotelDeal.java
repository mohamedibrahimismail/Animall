package com.example.animall.Data.Remote.Models.HotelDeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HotelDeal {
    @SerializedName("hotel_deal")
    @Expose
    private Integer hotelDeal;
    @SerializedName("animal_type")
    @Expose
    private String animalType;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("time_host")
    @Expose
    private String timeHost;
    @SerializedName("day_host")
    @Expose
    private String dayHost;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("offer_present")
    @Expose
    private Integer offerPresent;
    @SerializedName("seller")
    @Expose
    private String seller;
    @SerializedName("photo")
    @Expose
    private String photo;

    public Integer getHotelDeal() {
        return hotelDeal;
    }

    public void setHotelDeal(Integer hotelDeal) {
        this.hotelDeal = hotelDeal;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTimeHost() {
        return timeHost;
    }

    public void setTimeHost(String timeHost) {
        this.timeHost = timeHost;
    }

    public String getDayHost() {
        return dayHost;
    }

    public void setDayHost(String dayHost) {
        this.dayHost = dayHost;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getOfferPresent() {
        return offerPresent;
    }

    public void setOfferPresent(Integer offerPresent) {
        this.offerPresent = offerPresent;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
