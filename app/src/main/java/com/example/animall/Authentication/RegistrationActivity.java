package com.example.animall.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.animall.*;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.Api;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Home.Home;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    TextInputEditText txt_name, txt_email, txt_phone, txt_password, txt_confirm_password;
    String name, email, phone, password, confirm_password;
    MySharedPreference mprefs;
    LoginModel usermodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init_view();
    }

    private void init_view() {
        txt_name = findViewById(R.id.user_name);
        txt_email = findViewById(R.id.user_email);
        txt_phone = findViewById(R.id.user_phone);
        txt_password = findViewById(R.id.user_password);
        txt_confirm_password = findViewById(R.id.user_confirm_password);
        mprefs = MySharedPreference.getInstance();
    }

    public void make_registeration(View view) {
        validation();
    }

    private void validation() {
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();
        phone = txt_phone.getText().toString();
        confirm_password = txt_confirm_password.getText().toString();
        name = txt_name.getText().toString();
        if (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(phone) &&
                !TextUtils.isEmpty(password) &&
                 password.length()!=0 &&
                confirm_password.length()!=0 &&
                 phone.length() == 11 &&
                 password.length() >= 8 &&
                (confirm_password.equals(password))
                ) {

            //Common.CloseKeyBoard(this, edt_email);
            txt_name.setError(null);
            txt_email.setError(null);
            txt_phone.setError(null);
            txt_password.setError(null);
            txt_confirm_password.setError(null);
            RegisterWeb(name,email,phone,password,confirm_password);
        } else {
            if (TextUtils.isEmpty(name)) {
                txt_name.setError("ادخل الاسم الخاص بك");
            } else {
                txt_name.setError(null);
            }
            if (TextUtils.isEmpty(email)) {
                txt_email.setError("ادخل الايميل الخاص بك");
            } else {
                txt_email.setError(null);
            }
            if (TextUtils.isEmpty(phone)) {
                txt_phone.setError("ادخل رقم الهاتف الخاصة بك");
            } else {
                txt_phone.setError(null);
            }
            if (TextUtils.isEmpty(password) && password.equals(confirm_password)) {
                txt_password.setError("ادخل كلمة المرور الخاصة بك");
            } else {
                txt_password.setError(null);
            }
            if (password.length() < 8 && password.length() >= 1) {
                txt_password.setError("كلمة المرور ضعيفة");
            } else {
                txt_password.setError(null);
            }
            if (phone.length() < 11){
                txt_phone.setError("رقم الموبايل غير صحيح");
            }else {
                txt_phone.setError(null);
            }
            if (!password.equals(confirm_password)){
                txt_confirm_password.setError("كلمة المرور غير متطابقة");
            }else {
                txt_confirm_password.setError(null);
            }
        }
    }

    private void RegisterWeb(String name, String email, String phone, String password, String confirm_password) {
        Api.getService().Register(name,email,password,confirm_password,phone).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatue().equals("done")){
                        usermodel = response.body();
                        mprefs.Create_Update_UserData(RegistrationActivity.this,usermodel);
                        Toast.makeText(RegistrationActivity.this,"تم تسجيل دخولك بنجاح",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this, Home.class));
                        finish();
                    }else if(response.body().getStatue().equals("error")){
                        Toast.makeText(RegistrationActivity.this,"لقد تم تسجيلك من قبل",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

    }
}