package com.example.animall.Data.Remote;

import com.example.animall.Data.Remote.Models.Home.HomeModel;
import com.example.animall.Data.Remote.Models.Profile.ProfileModel;
import com.example.animall.Data.Remote.Models.Seller.SellerModel;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.SubCategoriesModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @FormUrlEncoded
    @POST("api/register_seller")
    Call<SellerModel> Register_Seller(@Field("name")String name,@Field("email")String email,
                                      @Field("password")String password,
                                      @Field("password_confirmation")String password_confirmation
    ,@Field("market_name")String market_name,@Field("number_id")String number_id,@Field("phone")String phone
    ,@Field("address")String address);
    @GET("/api/categories")
    Call<HomeModel> getHomeModel(@Query("access_token") String access_token);

    @GET("/api/sub_categories")
    Call<SubCategoriesModel> getSubCategories(@Query("access_token") String access_token, @Query("category_id ") String category_id );
    @GET("api/profile")
    Call<LoginModel>getProfile(@Query("access_token")String access_token,@Query("user_id")String user_id);
    @Multipart
    @POST("api/profile/Update")
    Call<LoginModel>update_profile(@Part("access_token")RequestBody access_token
    ,@Part("user_id")RequestBody user_id,@Part("name")RequestBody name,
                                   @Part("email")RequestBody email,
                                   @Part("address")RequestBody address,
                                   @Part("aria")RequestBody aria,
                                   @Part("phone")RequestBody phone,
                                   @Part MultipartBody.Part photo,
                                   @Part("website")RequestBody website);
    @FormUrlEncoded
    @POST("api/profile/Update")
    Call<LoginModel>update_profile_without_photo(@Field("access_token") String access_token
            ,@Field("user_id") String user_id,@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("address") String address,
                                   @Field("aria") String aria,
                                   @Field("phone") String phone,
                                   @Field("website") String website);

}
