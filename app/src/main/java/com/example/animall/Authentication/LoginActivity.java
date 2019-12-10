package com.example.animall.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Home.Home;
import com.example.animall.R;
import com.example.animall.Data.Remote.*;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText txt_email,txt_password;
    String email,password;
    MySharedPreference mprefs;
    LoginModel usermodel ;
   /* protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        String lang = Paper.book().read("language");
        if (Paper.book().read("language").equals("ar")) {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.AR_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());

        } else {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(Tags.EN_FONT_NAME)
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        }

        super.attachBaseContext(CalligraphyContextWrapper.wrap(LanguageHelper.onAttach(newBase, lang)));


    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
    }

    private void initview() {
        txt_email = findViewById(R.id.user_email);
        txt_password = findViewById(R.id.user_password);
        mprefs = MySharedPreference.getInstance();
    }

    public void LoginUser(View view) {
        Validation();
    }

    private void Validation() {
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();

        if (!TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                password.length() >= 8) {

            //Common.CloseKeyBoard(this, edt_email);
            txt_email.setError(null);
            txt_password.setError(null);
            txt_password.setError(null);
//            Login(userName, passWord);

//            Login(email, passWord);
            LoginWeb(email, password);
        } else {

            if (TextUtils.isEmpty(email)) {
                txt_email.setError("ادخل الايميل الخاص بك");
            } else {
                txt_email.setError(null);
            }
            if (TextUtils.isEmpty(password)) {
                txt_password.setError("ادخل كلمة المرور الخاصة بك");
            } else {
                txt_password.setError(null);
            }
            if (password.length() < 8 && password.length()>=1) {
                txt_password.setError("كلمة المرور صعيفة");
            } else {
                txt_password.setError(null);
            }
        }
    }
    private void LoginWeb(String email, String password) {
        Api.getService().Login(email,password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatue().equals("done")){
                        usermodel = response.body();
                        mprefs.Create_Update_UserData(LoginActivity.this,usermodel);
                        Toast.makeText(LoginActivity.this,"تم تسجيل الدخول بنجاج",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, Home.class));
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"الايميل غير صحيح ",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }

    public void go_to_user_register(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }

    public void go_to_seller_register(View view) {
        startActivity(new Intent(LoginActivity.this,Seller_Registration.class));
    }
}
