package com.example.animall.Data.Remote;

import com.example.animall.Data.Remote.Models.Home.HomeModel;
import com.example.animall.Data.Remote.Models.Product.ProductModel;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.SubCategoriesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/api/categories")
    Call<HomeModel> getHomeModel(@Query("access_token") String access_token);

    @GET("/api/sub_categories")
    Call<SubCategoriesModel> getSubCategories(@Query("access_token") String access_token,
                                              @Query("category_id ") String category_id);
    
     @GET("/api/products")
    Call<ProductModel> getProducts(@Query("access_token") String access_token,
                                   @Query("sub_category_id") String sub_category_id);


}
