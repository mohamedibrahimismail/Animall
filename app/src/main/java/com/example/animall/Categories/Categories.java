package com.example.animall.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.Home.Category_;
import com.example.animall.Data.Remote.Models.Home.HomeModel;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.Home.HomeRecyclerViewAdapter;
import com.example.animall.R;
import com.example.animall.SubCategories.SubCategories;
import com.example.animall.Utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categories extends AppCompatActivity implements HomeRecyclerViewAdapter.HandelClicked{

//    String accessToken = "5d8e1373028a65d8e1373028a75d8e1373028a85d8e1373028a95d8e1373028aa";
    private static final String TAG = "Category";

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.receclerView)
    RecyclerView recyclerView;

    LoginModel loginModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        loadDataOnline();

    }

    public void loadDataOnline() {

        if (Utilities.isNetworkAvailable(this)) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<HomeModel> call = service.getHomeModel(loginModel.getResult().getAccessToken());
            call.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                    if (response.code() == 200) {
                        HomeModel homeModel = response.body();
                        if (homeModel.getStatue().equals("done")) {
                            handleRecyclerview(homeModel.getResult().getCategory().getCategory());

                        }
                    }
                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {

                    Log.e("error", t.getMessage().toString());
                    Utilities.showMessage(Categories.this, "Something went wrong...Please try later!");
                }
            });
        }
    }

    @OnClick(R.id.back_btn)
    public void goBack(){
        finish();
    }

    public void handleRecyclerview(List<Category_> category){
        recyclerView.setNestedScrollingEnabled(false);
        HomeRecyclerViewAdapter recyclerViewAdapter = new HomeRecyclerViewAdapter(this,category);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void HandleClicked(Category_ category) {
        Intent intent = new Intent(this, SubCategories.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}
