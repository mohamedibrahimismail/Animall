
package com.example.animall.Data.Remote.Models.Product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("like")
    @Expose
    private Boolean like;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("product_name_ar")
    @Expose
    private String productNameAr;
    @SerializedName("product_name_en")
    @Expose
    private String productNameEn;
    @SerializedName("product_description_ar")
    @Expose
    private String productDescriptionAr;
    @SerializedName("product_description_en")
    @Expose
    private String productDescriptionEn;
    @SerializedName("before_price")
    @Expose
    private String beforePrice;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("address_ar")
    @Expose
    private String addressAr;
    @SerializedName("address_en")
    @Expose
    private String addressEn;
    @SerializedName("photo")
    @Expose
    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getProductNameAr() {
        return productNameAr;
    }

    public void setProductNameAr(String productNameAr) {
        this.productNameAr = productNameAr;
    }

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    public String getProductDescriptionAr() {
        return productDescriptionAr;
    }

    public void setProductDescriptionAr(String productDescriptionAr) {
        this.productDescriptionAr = productDescriptionAr;
    }

    public String getProductDescriptionEn() {
        return productDescriptionEn;
    }

    public void setProductDescriptionEn(String productDescriptionEn) {
        this.productDescriptionEn = productDescriptionEn;
    }

    public String getBeforePrice() {
        return beforePrice;
    }

    public void setBeforePrice(String beforePrice) {
        this.beforePrice = beforePrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddressAr() {
        return addressAr;
    }

    public void setAddressAr(String addressAr) {
        this.addressAr = addressAr;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
