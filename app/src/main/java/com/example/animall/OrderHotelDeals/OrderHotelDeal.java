package com.example.animall.OrderHotelDeals;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.Api;
import com.example.animall.Data.Remote.Models.HotelDeal.HotelDealModel;
import com.example.animall.Data.Remote.Models.OrderHost.OrderHost;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;
import com.squareup.picasso.Picasso;

public class OrderHotelDeal extends AppCompatActivity {
    @BindView(R.id.animal_type)
    EditText et_animal_type;
    @BindView(R.id.animal_age)
    EditText et_animal_age;
    @BindView(R.id.time_host)
    EditText et_time_host;
    @BindView(R.id.service)
    EditText et_available_service;
    @BindView(R.id.vaccination)
    EditText et_vaccination;
    @BindView(R.id.client_phone)
    EditText client_phone;
    @BindView(R.id.client_email)
    EditText client_email;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.img_close)
    ImageView close;
    Integer hotel_deal_id;
    MySharedPreference mprfs;
    LoginModel loginModel;
    String access_token, phone, email, age, vaccination;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_hotel_deal);
        ButterKnife.bind(this);
        getIntentData();
        getPreferencedData();
        getUserData();
        get_hotel_deal_model_online();
    }

    private void getPreferencedData() {
        mprfs = MySharedPreference.getInstance();
        loginModel = mprfs.Get_UserData(this);
        access_token = loginModel.getResult().getAccessToken();
        user_id = loginModel.getResult().getUserdata().getUserId();
    }

    private void getUserData() {
        if (Utilities.isNetworkAvailable(this)) {
            Api.getService().getProfile(access_token, user_id + "").enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatue().equals("done")) {
                            email = response.body().getResult().getUserdata().getEmail();
                            phone = response.body().getResult().getUserdata().getPhone();
                            client_email.setText(email);
                            client_phone.setText(phone);


                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {

                }
            });

        }
    }

    private void get_hotel_deal_model_online() {
        if (Utilities.isNetworkAvailable(OrderHotelDeal.this)) {
            Api.getService().get_hotel_deal(access_token, hotel_deal_id + "").enqueue(new Callback<HotelDealModel>() {
                @Override
                public void onResponse(Call<HotelDealModel> call, Response<HotelDealModel> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatue().equals("done")) {
                            et_animal_type.setText(response.body().getResult().getHotelDeal().getAnimalType());
                            et_available_service.setText(response.body().getResult().getHotelDeal().getService());
                            et_time_host.setText(response.body().getResult().getHotelDeal().getTimeHost());
                        }
                    }
                }

                @Override
                public void onFailure(Call<HotelDealModel> call, Throwable t) {

                }
            });
        }

    }

    private void getIntentData() {
        hotel_deal_id = getIntent().getIntExtra("hotel_deal", 0);
    }

    @OnClick
    public void confirm(View view) {
        validation();
    }

    private void validation() {
        age = et_animal_age.getText().toString();
        vaccination = et_vaccination.getText().toString();
        if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(vaccination)) {
            progressBar.setVisibility(View.VISIBLE);
            order_hotel_host(age, vaccination);

        } else {
            if (TextUtils.isEmpty(age)) {
                et_animal_age.setError("ادخل عمر الحيوان");
            } else {
                et_animal_age.setError(null);
            }
             if (TextUtils.isEmpty(vaccination)) {
                et_vaccination.setError("ادخل عدد التطعيمات");
            } else {
                et_vaccination.setError(null);
            }
        }
    }

    private void order_hotel_host(String age, String vaccination) {
        if (Utilities.isNetworkAvailable(this)) {
            Api.getService().order_host(access_token, vaccination, age, phone, email, user_id + "", hotel_deal_id + "").enqueue(new Callback<OrderHost>() {
                @Override
                public void onResponse(Call<OrderHost> call, Response<OrderHost> response) {
                       if(response.isSuccessful()){
                           if(response.body().getStatue().equals("done")){
                               progressBar.setVisibility(View.GONE);
                               Toast.makeText(OrderHotelDeal.this, "your order is accepted", Toast.LENGTH_SHORT).show();
                           }
                       }
                }

                @Override
                public void onFailure(Call<OrderHost> call, Throwable t) {

                }
            });
        }
    }

    public void close(View view) {
        finish();
    }
}
