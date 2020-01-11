package com.example.animall.Favorites;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animall.Data.Local.AddToCard.FavoriteViewModel;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.Favorites.FavoriteModel;
import com.example.animall.Data.Remote.Models.LikeModel.LikeModel;
import com.example.animall.Data.Remote.Models.Product.Product;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.Product.Communicator;
import com.example.animall.Product.RecyclerViewAdapter;
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

public class Favorites extends AppCompatActivity implements Communicator {

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

    @BindView(R.id.receclerView)
    RecyclerView receclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    LoginModel loginModel;
    RecyclerViewAdapter recyclerViewAdapter;


    boolean is_saved = false;
    FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        loadFavorites();

    }

    @OnClick(R.id.back_btn)
    public void goBack() {
        finish();
    }

    public void loadFavorites() {


        if (Utilities.isNetworkAvailable(this)) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<FavoriteModel> call = service.getFavorites(loginModel.getResult().getAccessToken(), loginModel.getResult().getUserdata().getUserId() + "");
            call.enqueue(new Callback<FavoriteModel>() {
                @Override
                public void onResponse(Call<FavoriteModel> call, Response<FavoriteModel> response) {

                    if (response.code() == 200) {
                        FavoriteModel favoriteModel = response.body();
                        if (favoriteModel.getStatue().equals("done")) {
                            if (!favoriteModel.getResult().getFavorites().isEmpty()) {
                                handleRecyclerview(favoriteModel.getResult().getFavorites());
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Utilities.showMessage(getApplicationContext(), "القائمة فارغة");
                            }


                        }
                    }
                }

                @Override
                public void onFailure(Call<FavoriteModel> call, Throwable t) {

                    Log.e("error", t.getMessage().toString());
                    Utilities.showMessage(Favorites.this, "Something went wrong...Please try later!");
                }
            });
        }
    }

    public void handleRecyclerview(List<Product> list) {
        receclerView.setNestedScrollingEnabled(false);
        recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        receclerView.setLayoutManager(mLayoutManager);
        receclerView.setAdapter(recyclerViewAdapter);
        progressBar.setVisibility(View.GONE);
        receclerView.setVisibility(View.VISIBLE);
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
                    Utilities.showMessage(getApplicationContext(), "Something went wrong...Please try later!");
                }
            });
        }
    }

    @OnClick(R.id.close)
    public void close_details_scroll_lyt() {
        show_details_scroll_lyt.setVisibility(View.GONE);
    }


    public void setup_room(com.example.animall.Data.Remote.Models.Product.Product product) {

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.getFavoriteMovie(product.getId()).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable com.example.animall.Data.Remote.Models.Product.Product productmodel) {

                if (productmodel != null) {
                    is_saved = true;
                    card_btn_txt.setBackground(getResources().getDrawable(R.drawable.button_backgroundaddedtocard));
                    card_btn_txt.setText("ازالة من السلة");
                    card_btn_txt.setTextColor(getResources().getColor(R.color.greenlight));

                } else {
                    is_saved = false;
                    card_btn_txt.setBackground(getResources().getDrawable(R.drawable.button_background));
                    card_btn_txt.setText("اضف للسلة");
                    card_btn_txt.setTextColor(getResources().getColor(R.color.white));

                }


            }
        });

        card_btn_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_saved){
                    is_saved = false;
                    favoriteViewModel.delete(product);
                }else {
                    is_saved = true;
                    favoriteViewModel.insert(product);
                }
            }
        });


    }
}
