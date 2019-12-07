
package com.example.animall.Data.Remote.Models.SubCategoriesModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("SubCategory")
    @Expose
    private SubCategory subCategory;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
