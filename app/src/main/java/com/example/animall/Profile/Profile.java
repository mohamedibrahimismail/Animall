package com.example.animall.Profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.Api;
import com.example.animall.Data.Remote.Models.Profile.ProfileModel;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.io.File;

public class Profile extends AppCompatActivity {
    @BindView(R.id.edit_user_name)
    EditText user_name;
    @BindView(R.id.edit_name)
    EditText name;
    @BindView(R.id.edit_email)
    EditText email;
    @BindView(R.id.edit_phone)
    EditText phone;
    @BindView(R.id.edit_place)
    EditText place;
    @BindView(R.id.website_edittext)
    EditText website;
    @BindView(R.id.edit_address)
    EditText address;
    @BindView(R.id.profile_image)
    CircleImageView profile_photo;
    @BindView(R.id.edit_photo)
    ImageButton editphoto;
    @BindView(R.id.progressbar_edit)
    ProgressBar progressBar;
    MySharedPreference mprefs;
    LoginModel loginModel;
    String access_token;
    int id;
    String user_id;
    int IMG = 1;
    Uri filePath;
    private final String read_permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getLoginModelData();
        getOnlineData();
    }
    private void getLoginModelData() {
        mprefs = MySharedPreference.getInstance();
        loginModel = mprefs.Get_UserData(this);
        access_token = loginModel.getResult().getAccessToken();
        id = loginModel.getResult().getUserdata().getUserId();
        user_id = id+"";
    }
    private void getOnlineData() {
        if(Utilities.isNetworkAvailable(this)){
            Api.getService().getProfile(access_token,user_id).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if(response.isSuccessful()){
                        if(response.body().getStatue().equals("done")){
                            name.setText(response.body().getResult().getUserdata().getName());
                            user_name.setText(response.body().getResult().getUserdata().getName());
                            email.setText(response.body().getResult().getUserdata().getEmail());
                            place.setText(response.body().getResult().getUserdata().getArea());
                            address.setText(response.body().getResult().getUserdata().getAddress());
                            phone.setText(response.body().getResult().getUserdata().getPhone());
                            website.setText(response.body().getResult().getUserdata().getWebsite());
                            Picasso.get().load(response.body().getResult().getUserdata().getPhoto()).into(profile_photo);

                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {

                }
            });

        }
    }
    @OnClick(R.id.edit_photo)
    public void EditPhoto(View view) {
        Check_ReadPermission(IMG);
    }

    private void Check_ReadPermission(int img) {
        if (ContextCompat.checkSelfPermission(this,read_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{read_permission},img);
        }else
        {
            select_photo(img);
        }
    }

    private void select_photo(int img) {
        Intent intent ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else
        {
            intent = new Intent(Intent.ACTION_GET_CONTENT);

        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(intent,img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();


            profile_photo.setVisibility(View.GONE);
            Picasso.get().load(filePath).into(profile_photo);
            profile_photo.setVisibility(View.VISIBLE);
            Toast.makeText(Profile.this, "image added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.btn_save)
    public void save(View view) {
        validation();
    }

    private void validation() {
        if (!TextUtils.isEmpty(place.getText().toString()) &&
                !TextUtils.isEmpty(website.getText().toString())){
            place.setError(null);
            website.setError(null);
            progressBar.setVisibility(View.VISIBLE);
            UpdateUserData();
        }else{
            if (TextUtils.isEmpty(place.getText().toString())){
                place.setError("برجاء ادخال المكان");
            }else {
                place.setError(null);
            }if (TextUtils.isEmpty(website.getText().toString())){
                website.setError("برجاء ادخال الموقع الالكتروني");
            }else {
                website.setError(null);
            }
        }
    }

    private void UpdateUserData() {
        RequestBody rb_access_token = Utilities.getRequestBodyText(access_token);
        RequestBody rb_user_id = Utilities.getRequestBodyText(user_id);
        RequestBody rb_name = Utilities.getRequestBodyText(name.getText().toString());
        RequestBody rb_email =  Utilities.getRequestBodyText(email.getText().toString());
        RequestBody rb_address =  Utilities.getRequestBodyText(address.getText().toString());
        RequestBody rb_area =  Utilities.getRequestBodyText(place.getText().toString());
        RequestBody rb_phone =  Utilities.getRequestBodyText(phone.getText().toString());
        RequestBody rb_website =  Utilities.getRequestBodyText(website.getText().toString());
        if(filePath != null){
            MultipartBody.Part photo = Utilities.getMultiPart(this,filePath,"photo");
            Api.getService().update_profile(rb_access_token,rb_user_id,rb_name,rb_email,rb_address,rb_area,rb_phone,photo,rb_website).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                    if(Utilities.isNetworkAvailable(Profile.this)){
                        if(response.isSuccessful()){
                            name.setText(response.body().getResult().getUserdata().getName());
                            email.setText(response.body().getResult().getUserdata().getEmail());
                            address.setText(response.body().getResult().getUserdata().getAddress());
                            place.setText(response.body().getResult().getUserdata().getArea());
                            phone.setText(response.body().getResult().getUserdata().getPhone());
                            website.setText(response.body().getResult().getUserdata().getWebsite());
                            Picasso.get().load(response.body().getResult().getUserdata().getPhoto()).into(profile_photo);
                            mprefs.Create_Update_UserData(Profile.this,response.body());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {

                }
            });
        }else {
            Api.getService().update_profile_without_photo(access_token,user_id,name.getText().toString(),email.getText().toString(), address.getText().toString(),place.getText().toString(),
                    phone.getText().toString(),website.getText().toString()).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (Utilities.isNetworkAvailable(Profile.this)) {
                        if (response.isSuccessful()) {
                            name.setText(response.body().getResult().getUserdata().getName());
                            email.setText(response.body().getResult().getUserdata().getEmail());
                            address.setText(response.body().getResult().getUserdata().getAddress());
                            place.setText(response.body().getResult().getUserdata().getArea());
                            phone.setText(response.body().getResult().getUserdata().getPhone());
                            website.setText(response.body().getResult().getUserdata().getWebsite());
                            Picasso.get().load(response.body().getResult().getUserdata().getPhoto()).into(profile_photo);
                            mprefs.Create_Update_UserData(Profile.this, response.body());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.btn_cancel)
    public void cancel(View view) {
    }

}
