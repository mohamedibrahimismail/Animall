package com.example.animall.HotelDeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.Api;
import com.example.animall.Data.Remote.Models.HotelDeal.HotelDeal;
import com.example.animall.Data.Remote.Models.HotelDeal.HotelDeals;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;

import java.util.List;

public class Hotel_Deals extends AppCompatActivity {
    @BindView(R.id.hotel_deal_recycler_view)
    RecyclerView rec_hotel_deal;
    RecyclerView.LayoutManager rec_manager;
    HotelDealsAdapter hotelDealsAdapter;
    MySharedPreference mprefs;
    String access_token;
    LoginModel loginModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel__deals);
        ButterKnife.bind(this);
        getSharedPreferencesData();
        loadDataOnline();
    }

    private void getSharedPreferencesData() {
        mprefs = MySharedPreference.getInstance();
        loginModel = mprefs.Get_UserData(this);
        access_token = loginModel.getResult().getAccessToken();
    }

    private void loadDataOnline() {
        Api.getService().get_hotel_deals(access_token).enqueue(new Callback<HotelDeals>() {
            @Override
            public void onResponse(Call<HotelDeals> call, Response<HotelDeals> response) {
                if(Utilities.isNetworkAvailable(Hotel_Deals.this)){
                    if(response.isSuccessful()){
                        if(response.body().getStatue().equals("done")){
                            handlerecyclerview(response.body().getResult().getHotelDeal());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HotelDeals> call, Throwable t) {

            }
        });
    }

    private void handlerecyclerview(List<HotelDeal> hotelDealList) {
        rec_manager = new LinearLayoutManager(this);
        hotelDealsAdapter = new HotelDealsAdapter(hotelDealList,this);
        rec_hotel_deal.setHasFixedSize(true);
        rec_hotel_deal.setLayoutManager(rec_manager);
        rec_hotel_deal.setAdapter(hotelDealsAdapter);
    }
}
