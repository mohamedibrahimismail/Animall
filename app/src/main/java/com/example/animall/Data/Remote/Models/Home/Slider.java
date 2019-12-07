
package com.example.animall.Data.Remote.Models.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text_small_ar")
    @Expose
    private String textSmallAr;
    @SerializedName("text_small_en")
    @Expose
    private String textSmallEn;
    @SerializedName("text_big_ar")
    @Expose
    private String textBigAr;
    @SerializedName("text_big_en")
    @Expose
    private String textBigEn;
    @SerializedName("photo")
    @Expose
    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextSmallAr() {
        return textSmallAr;
    }

    public void setTextSmallAr(String textSmallAr) {
        this.textSmallAr = textSmallAr;
    }

    public String getTextSmallEn() {
        return textSmallEn;
    }

    public void setTextSmallEn(String textSmallEn) {
        this.textSmallEn = textSmallEn;
    }

    public String getTextBigAr() {
        return textBigAr;
    }

    public void setTextBigAr(String textBigAr) {
        this.textBigAr = textBigAr;
    }

    public String getTextBigEn() {
        return textBigEn;
    }

    public void setTextBigEn(String textBigEn) {
        this.textBigEn = textBigEn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
