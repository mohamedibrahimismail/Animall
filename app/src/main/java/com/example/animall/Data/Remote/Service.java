package com.example.animall.Data.Remote;

import com.example.animall.Data.Remote.Models.Home.HomeModel;
import com.example.animall.Data.Remote.Models.LoginModel;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.SubCategoriesModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {
    @FormUrlEncoded
    @POST("api/login")
    Call<LoginModel> Login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<LoginModel> Register(@Field("name") String name, @Field("email") String email,
                              @Field("password") String password, @Field("password_confirmation") String password_confirmation
            , @Field("phone") String phone);


    @GET("/api/categories")
    Call<HomeModel> getHomeModel(@Query("access_token") String access_token);

    @GET("/api/sub_categories")
    Call<SubCategoriesModel> getSubCategories(@Query("access_token") String access_token, @Query("category_id ") String category_id );

}
