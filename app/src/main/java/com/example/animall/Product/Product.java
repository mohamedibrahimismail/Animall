package com.example.animall.Product;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.animall.Data.Local.AddToCard.FavoriteViewModel;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.LikeModel.LikeModel;
import com.example.animall.Data.Remote.Models.Product.ProductModel;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.Category;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity implements Communicator {

    //scroll details layout
    @BindView(R.id.card_txt)
    TextView card_btn_txt;
    @BindView(R.id.show_details_scroll_lyt)
    ScrollView show_details_scroll_lyt;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.old_price)
    TextView old_price;
    @BindView(R.id.new_price)
    TextView new_price;
    @BindView(R.id.ratingBar)
    MaterialRatingBar ratingBar;
    @BindView(R.id.rate_txt)
    TextView rate_txt;
    @BindView(R.id.address_txt)
    TextView address_txt;

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

    RecyclerViewAdapter recyclerViewAdapter;
    FavoriteViewModel favoriteViewModel;

    boolean is_saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        categoryModel = (Category) getIntent().getParcelableExtra("sub_category");
        if (Utilities.getLang(this).equals("ar")) {
            title.setText(categoryModel.getCategoryAr());
        } else {
            title.setText(categoryModel.getCategoryEn());
        }
        loadDataOnline();
    }

    public void handleSlider(final List<com.example.animall.Data.Remote.Models.Product.Product> list) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, favoriteViewModel,list);
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
            Call<ProductModel> call = service.getProducts(loginModel.getResult().getAccessToken(), loginModel.getResult().getUserdata().getUserId() + "", categoryModel.getId().toString());
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
        recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        receclerView.setLayoutManager(mLayoutManager);
        receclerView.setAdapter(recyclerViewAdapter);
    }

    @OnClick(R.id.back_btn)
    public void goBack() {
        finish();
    }

    @Override
    public void HandleClicked(int index, com.example.animall.Data.Remote.Models.Product.Product product) {
        setup_room(product);
        Picasso.get()
                .load(product.getPhoto())
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(imageView);
        if (Utilities.getLang(this).equals("ar")) {
            name.setText(product.getProductNameAr());
            old_price.setText(product.getBeforePrice());
            new_price.setText(product.getPrice() + "SR");
            address_txt.setText(product.getAddressAr());
            text.setText(product.getProductDescriptionAr());
        } else {
            name.setText(product.getProductNameEn());
            old_price.setText(product.getBeforePrice());
            new_price.setText(product.getPrice() + "SR");
            address_txt.setText(product.getAddressEn());
            text.setText(product.getProductDescriptionEn());
        }

        old_price.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ratingBar.setRating(product.getRate());
        rate_txt.setText(product.getRate() + "");
        if (product.getLike()) {
            like.setImageDrawable(getResources().getDrawable(R.drawable.heart_active));
        } else {
            like.setImageDrawable(getResources().getDrawable(R.drawable.heart_notactive));
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getLike()) {
                    product.setLike(false);
                    like.setImageDrawable(getApplication().getResources().getDrawable(R.drawable.heart_notactive));

                } else {
                    product.setLike(true);
                    like.setImageDrawable(getApplication().getResources().getDrawable(R.drawable.heart_active));
                }
                like(product.getId() + "");
                recyclerViewAdapter.setLike(index, product.getLike());
            }
        });
        show_details_scroll_lyt.setVisibility(View.VISIBLE);
        card_btn_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_saved) {
                    favoriteViewModel.insert(product);
                } else {
                    favoriteViewModel.delete(product);
                }
            }
        });

    }


    @OnClick(R.id.close)
    public void close_details_scroll_lyt() {
        show_details_scroll_lyt.setVisibility(View.GONE);
    }

    @Override
    public void like(String id) {
        if (Utilities.isNetworkAvailable(this)) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<LikeModel> call = service.likeProduct(loginModel.getResult().getAccessToken(), loginModel.getResult().getUserdata().getUserId() + "", id);
            call.enqueue(new Callback<LikeModel>() {
                @Override
                public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {

                    if (response.code() == 200) {
                        LikeModel likeModel = response.body();

                    }
                }

                @Override
                public void onFailure(Call<LikeModel> call, Throwable t) {

                    Log.e("error", t.getMessage().toString());
                    Utilities.showMessage(Product.this, "Something went wrong...Please try later!");
                }
            });
        }
    }



    public void setup_room(com.example.animall.Data.Remote.Models.Product.Product product) {

        favoriteViewModel.getFavoriteMovie(product.getId()).observe(this, new Observer<com.example.animall.Data.Remote.Models.Product.Product>() {
            @Override
            public void onChanged(@Nullable com.example.animall.Data.Remote.Models.Product.Product productmodel) {

                if (productmodel != null) {
                    is_saved = true;
                    card_btn_txt.setBackground(getResources().getDrawable(R.drawable.button_backgroundaddedtocard));
                    card_btn_txt.setText("ازالة من السلة");
                    card_btn_txt.setTextColor(getResources().getColor(R.color.greenlight));
//                    addtocard_btn.setText(getString(R.string.addedTocard));
//                    addtocard_btn.setBackgroundResource(R.color.green);
//                    categoryModel.setPhotos(Convert_String_To_List(categoryModel.getPhotosString()));
//                    spinner.setSelection(categoryModel.getQuantity()-1);
//                    quantity.setText(categoryModel.getQuantity()+"");
                    // Log.e("photos",categoryModel.getPhotos().toString());
                    // Toast.makeText(CategoryDetailes.this,categoryModel.getPhotos().toString(),Toast.LENGTH_LONG).show();


                } else {
                    is_saved = false;
                    card_btn_txt.setBackground(getResources().getDrawable(R.drawable.button_background));
                    card_btn_txt.setText("اضف للسلة");
                    card_btn_txt.setTextColor(getResources().getColor(R.color.white));

                }


            }
        });


    }

}
