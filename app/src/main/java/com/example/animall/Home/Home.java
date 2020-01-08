package com.example.animall.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.animall.Categories.Categories;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.Home.Category_;
import com.example.animall.Data.Remote.Models.Home.HomeModel;
import com.example.animall.Data.Remote.Models.Home.Slider;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.R;
import com.example.animall.SubCategories.SubCategories;
import com.example.animall.Utilities.Utilities;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements HomeRecyclerViewAdapter.HandelClicked{

    //String accessToken = "5d8e1373028a65d8e1373028a75d8e1373028a85d8e1373028a95d8e1373028aa";
    private static final String TAG = "Home";

    //sideMenu
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;

    //slider
    @BindView(R.id.sliderviewPager)
    ViewPager sliderviewPager;
    @BindView(R.id.indecator)
    LinearLayout indecator_lyt;
    private ImageView[] dots;

    //RecyclerView
    @BindView(R.id.recyclecViewHome)
    RecyclerView recyclecViewHome;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.home_lyt)
    NestedScrollView home_lyt;

    LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        Log.e("token",loginModel.getResult().getAccessToken() );
        Log.e("id",loginModel.getResult().getUserdata().getUserId() +"");
        handleSideMenu();
        loadDataOnline();


    }

    public void handleSideMenu() {
        View headerLayout = navigationView.getHeaderView(0);
        LinearLayout categories_lyt = (LinearLayout)headerLayout.findViewById(R.id.categories_lyt);
        categories_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Categories.class));
            }
        });
    }

    public void handleSlider(final List<Slider> list) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, list);
        sliderviewPager.setAdapter(viewPagerAdapter);
        dots = new ImageView[list.size()];

        for (int i = 0; i < list.size(); i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            indecator_lyt.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        sliderviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < list.size(); i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void handleRecyclerview(List<Category_> category){
        recyclecViewHome.setNestedScrollingEnabled(false);
        HomeRecyclerViewAdapter recyclerViewAdapter = new HomeRecyclerViewAdapter(this,category);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclecViewHome.setLayoutManager(mLayoutManager);
        recyclecViewHome.setItemAnimator(new DefaultItemAnimator());
        recyclecViewHome.setAdapter(recyclerViewAdapter);
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
                            handleSlider(homeModel.getResult().getCategory().getSlider());
                            handleRecyclerview(homeModel.getResult().getCategory().getCategory());
                            progressBar.setVisibility(View.GONE);
                            home_lyt.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {

                    Log.e("error", t.getMessage().toString());
                    Utilities.showMessage(Home.this, "Something went wrong...Please try later!");
                }
            });
        }
    }

    @OnClick(R.id.menu)
    public void openSlideMenu() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }


    @Override
    public void HandleClicked(Category_ category) {
        Intent intent = new Intent(this, SubCategories.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}
