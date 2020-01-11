
package com.example.animall.Data.Remote.Models.Product;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("slider")
    @Expose
    private List<Product> slider = null;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public List<Product> getSlider() {
        return slider;
    }

    public void setSlider(List<Product> slider) {
        this.slider = slider;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

}
