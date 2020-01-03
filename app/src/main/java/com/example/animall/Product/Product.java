package com.example.animall.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.Product.Slider;
import com.example.animall.Data.Remote.Models.Product.ProductModel;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.Category;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity implements RecyclerViewAdapter.HandelClicked {

    //slider
    @BindView(R.id.sliderviewPager)
    ViewPager sliderviewPager;
    @BindView(R.id.indecator)
    LinearLayout indecator_lyt;
    private ImageView[] dots;

    LoginModel loginModel;

    @BindView(R.id.receclerView)
    RecyclerView receclerView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.lyt)
    LinearLayout layout;

    @BindView(R.id.title)
    TextView title;
    Category categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        categoryModel = (Category) getIntent().getParcelableExtra("sub_category");
        if (Utilities.getLang(this).equals("ar")) {
            title.setText(categoryModel.getCategoryAr());
        }else {
            title.setText(categoryModel.getCategoryEn());
        }
        loadDataOnline();
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

    public void loadDataOnline() {

        if (Utilities.isNetworkAvailable(this)) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ProductModel> call = service.getProducts(loginModel.getResult().getAccessToken(), categoryModel.getId().toString());
            call.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {

                    if (response.code() == 200) {
                        ProductModel productModel = response.body();
                        if (productModel.getStatue().equals("done")) {
                            if (!productModel.getResult().getData().getSlider().isEmpty()) {
                                handleSlider(productModel.getResult().getData().getSlider());
                            }
                            if (!productModel.getResult().getData().getProduct().isEmpty()) {
                                handleRecyclerview(productModel.getResult().getData().getProduct());
                            }
                            progressBar.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {

                    Log.e("error", t.getMessage().toString());
                    Utilities.showMessage(Product.this, "Something went wrong...Please try later!");
                }
            });
        }
    }

    public void handleRecyclerview(List<com.example.animall.Data.Remote.Models.Product.Product> list) {
        receclerView.setNestedScrollingEnabled(false);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        receclerView.setLayoutManager(mLayoutManager);
        receclerView.setAdapter(recyclerViewAdapter);
    }

    @OnClick(R.id.back_btn)
    public void goBack() {
        finish();
    }

    @Override
    public void HandleClicked(com.example.animall.Data.Remote.Models.Product.Product product) {

    }
}
