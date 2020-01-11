package com.example.animall.MyCard;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animall.Data.Local.AddToCard.FavoriteViewModel;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.LikeModel.LikeModel;
import com.example.animall.Data.Remote.Models.Product.Product;
import com.example.animall.Data.Remote.Models.User.LoginModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.OrderCycle.OrderCycle;
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

public class MyCard extends AppCompatActivity implements RecyclerViewAdapter.Communicator {

    FavoriteViewModel favoriteViewModel;
    RecyclerViewAdapter recyclerViewAdapter;
    @BindView(R.id.receclerView)
    RecyclerView receclerView;

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

    @BindView(R.id.update_quantity_txt)
    TextView update_quantity_txt;
    @BindView(R.id.quantity_edt)
    EditText quantity_edt;

    @BindView(R.id.modify_quntity_scroll_lyt)
    ScrollView modify_quntity_scroll_lyt;

    @BindView(R.id.allcost)
    TextView allcost;

    @BindView(R.id.card_lyt)
    NestedScrollView card_lyt;

    LoginModel loginModel;
    boolean is_saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        ButterKnife.bind(this);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        handleRecyclerview();
    }

    public void handleRecyclerview() {
        receclerView.setNestedScrollingEnabled(false);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        receclerView.setLayoutManager(mLayoutManager);
        receclerView.setAdapter(recyclerViewAdapter);
        setup_room();
    }

    public void setup_room() {

        favoriteViewModel.getAllNotes().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                if (!list.isEmpty()) {
                    recyclerViewAdapter.setList(list);
                    calculateAllCost(list);
                    card_lyt.setVisibility(View.VISIBLE);
                } else {
                    card_lyt.setVisibility(View.GONE);
                    recyclerViewAdapter.setList(list);
                    Utilities.showMessage(getApplicationContext(), "السلة فارغة يرجي اضافة منتجات");
                }

            }
        });


    }

    public void calculateAllCost(List<Product> list){
        double AllCost = 0.0d;
        for(Product product:list){
            AllCost += product.getQuantity()*Double.parseDouble(product.getPrice());
        }
        AllCost += 54d;
        allcost.setText(AllCost+"");
    }

    @OnClick(R.id.close)
    public void close_details_scroll_lyt() {
        show_details_scroll_lyt.setVisibility(View.GONE);
    }

    @Override
    public void showProductDetails(Product product) {
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
                favoriteViewModel.update(product);
                like(product.getId() + "");

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

    @Override
    public void removeProduct(Product product) {
        favoriteViewModel.delete(product);
    }

    public void setup_room(com.example.animall.Data.Remote.Models.Product.Product product) {

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


    }

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
                    Utilities.showMessage(com.example.animall.MyCard.MyCard.this, "Something went wrong...Please try later!");
                }
            });
        }
    }

    @Override
    public void modifyQuantity(Product product){
        quantity_edt.setText(product.getQuantity()+"");
        modify_quntity_scroll_lyt.setVisibility(View.VISIBLE);
        update_quantity_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity_edt.getText()!=null&&!quantity_edt.getText().toString().trim().equals("")){
                    product.setQuantity(Double.parseDouble(quantity_edt.getText().toString().trim()));
                    favoriteViewModel.update(product);
                    modify_quntity_scroll_lyt.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick(R.id.close_quenty)
    public void close_quenty(){
        modify_quntity_scroll_lyt.setVisibility(View.GONE);
    }

    @OnClick(R.id.back_btn)
    public void goBack() {
        finish();
    }

    @OnClick(R.id.gotoOrderCycle)
    public void  gotoOrderCycle(){
        startActivity(new Intent(this, OrderCycle.class));
    }



}
