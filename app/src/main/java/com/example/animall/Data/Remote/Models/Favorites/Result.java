package com.example.animall.Data.Remote.Models.Favorites;

import com.example.animall.Data.Remote.Models.Product.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("favorites")
    @Expose
    private List<Product> favorites=null;
    @SerializedName("access_token")
    @Expose
    private String access_token;

    public List<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Product> favorites) {
        this.favorites = favorites;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
