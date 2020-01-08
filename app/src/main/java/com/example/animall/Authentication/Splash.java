package com.example.animall.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Home.Home;
import com.example.animall.HotelDeals.Hotel_Deals;
import com.example.animall.R;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
   MySharedPreference mprefs;
   LoginModel usermodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mprefs = MySharedPreference.getInstance();
        usermodel = mprefs.Get_UserData(Splash.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(usermodel != null){
                    startActivity(new Intent(Splash.this, Hotel_Deals.class));
                    finish();
                }else {
                    startActivity(new Intent(Splash.this,LoginActivity.class));
                    finish();
                }

            }
        },3000);
    }
}
