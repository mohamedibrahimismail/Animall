package com.example.animall.Authentication;

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
import android.widget.Toast;

import com.example.animall.Data.Remote.Api;
import com.example.animall.Data.Remote.Models.Seller.SellerModel;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.R;
import com.google.android.material.textfield.TextInputEditText;

public class Seller_Registration extends AppCompatActivity {
    @BindView(R.id.seller_name)
    TextInputEditText txt_sellername;
    @BindView(R.id.seller_address)
    TextInputEditText txt_selleraddress;
    @BindView(R.id.seller_email)
    TextInputEditText txt_selleremail;
    @BindView(R.id.com_reg_no)
    TextInputEditText txt_com_registeration_number;
    @BindView(R.id.shop_name)
    TextInputEditText txt_shopname;
    @BindView(R.id.seller_phone)
    TextInputEditText txt_Sellerphone;
    @BindView(R.id.seller_password)
    TextInputEditText txt_seller_password;
    @BindView(R.id.seller_password_confirmation)
    TextInputEditText txt_seller_password_confirmation;
    String seller_name,seller_address,seller_phone,seller_email,shop_name,com_reg_No,password,confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__registration);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_register)
    public void make_registeration(View view) {
        validation();
    }
    public void validation(){
        seller_email = txt_selleremail.getText().toString();
        seller_phone = txt_Sellerphone.getText().toString();
        seller_address = txt_selleraddress.getText().toString();
        seller_name = txt_sellername.getText().toString();
        shop_name = txt_shopname.getText().toString();
        com_reg_No = txt_com_registeration_number.getText().toString();
        password = txt_seller_password.getText().toString();
        confirm_password = txt_seller_password_confirmation.getText().toString();
        if (!TextUtils.isEmpty(seller_name) &&
                !TextUtils.isEmpty(seller_email) &&
                !TextUtils.isEmpty(seller_phone) &&
                !TextUtils.isEmpty(seller_address) &&
                !TextUtils.isEmpty(shop_name) &&
                !TextUtils.isEmpty(com_reg_No) &&
                seller_phone.length() == 11 &&
                (password.equals(confirm_password)&& password.length()!=0)
        ) {

            //Common.CloseKeyBoard(this, edt_email);
            txt_shopname.setError(null);
            txt_sellername.setError(null);
            txt_Sellerphone.setError(null);
            txt_selleremail.setError(null);
            txt_selleraddress.setError(null);
            txt_com_registeration_number.setError(null);
            Register_seller(seller_name,seller_email,seller_phone,seller_address,shop_name,com_reg_No,password,confirm_password);
        } else {
            if (TextUtils.isEmpty(seller_name)) {
                txt_sellername.setError("ادخل الاسم الخاص بك");
            } else {
                txt_sellername.setError(null);
            }
            if (TextUtils.isEmpty(seller_email)) {
                txt_selleremail.setError("ادخل الايميل الخاص بك");
            } else {
                txt_selleremail.setError(null);
            }
            if (TextUtils.isEmpty(seller_phone)) {
                txt_Sellerphone.setError("ادخل رقم الهاتف الخاصة بك");
            } else {
                txt_Sellerphone.setError(null);
            }
            if (TextUtils.isEmpty(seller_address)) {
                txt_selleraddress.setError("ادخل العنوان الخاص بك");
            } else {
                txt_selleraddress.setError(null);
            }
            if (TextUtils.isEmpty(shop_name)) {
                txt_shopname.setError("ادخل العنوان الخاص بك");
            } else {
                txt_shopname.setError(null);
            }
            if (TextUtils.isEmpty(com_reg_No)) {
                txt_com_registeration_number.setError("ادخل العنوان الخاص بك");
            } else {
                txt_com_registeration_number.setError(null);
            }
            if (password.length() ==0) {
                txt_seller_password.setError("ادخل كلمة المرور الخاصة بك");
            } else {
                txt_seller_password.setError(null);
            }
            if (password.length() < 8 && password.length() >= 1) {
                txt_seller_password.setError("كلمة المرور ضعيفة");
            } else {
                txt_seller_password.setError(null);
            }
            if (seller_phone.length() < 11){
                txt_Sellerphone.setError("رقم الموبايل غير صحيح");
            }else {
                txt_Sellerphone.setError(null);
            }
            if (!password.equals(confirm_password)){
                txt_seller_password_confirmation.setError("كلمة المرور غير متطابقة");
            }else {
                txt_seller_password_confirmation.setError(null);
            }
        }
    }

    private void Register_seller(String seller_name, String seller_email, String seller_phone, String seller_address, String shop_name, String com_reg_no, String password, String confirm_password) {
        Api.getService().Register_Seller(seller_name,seller_email,password,confirm_password,shop_name,com_reg_no,seller_phone,seller_address).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatue().equals("done")){
                        Toast.makeText(Seller_Registration.this,"تم تسجيلك بنجاح",Toast.LENGTH_LONG).show();
                    }else if(response.body().getStatue().equals("error")){
                        Toast.makeText(Seller_Registration.this,"لقد تم تسجيلك من قبل",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
    }
}
