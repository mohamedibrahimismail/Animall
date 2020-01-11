package com.example.animall.OrderCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.animall.Data.Local.AddToCard.FavoriteViewModel;
import com.example.animall.Data.Local.MySharedPreference;
import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.LikeModel.LikeModel;
import com.example.animall.Data.Remote.Models.Product.Product;
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

public class OrderCycle extends AppCompatActivity {
    @BindView(R.id.cost_txt)
    TextView cost_txt;
    @BindView(R.id.allcost_lyt)
    ScrollView allcost_lyt;
    @BindView(R.id.paywhenreceive_lyt)
    ScrollView paywhenreceive_lyt;
    @BindView(R.id.phase3_src)
    ScrollView phase3_src;
    @BindView(R.id.phase4_src)
    ScrollView phase4_src;
    int phase = 1;
    LoginModel loginModel;
    FavoriteViewModel favoriteViewModel;
    List<Product> allproducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cycle);
        ButterKnife.bind(this);
        loginModel = MySharedPreference.getInstance().Get_UserData(this);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        setup_room();

    }

    public void setup_room() {

        favoriteViewModel.getAllNotes().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> list) {
                if (!list.isEmpty()) {
                    allproducts = list;
                    getAllCost();
                }

            }
        });


    }

    public void makeOrder(){

        List<ProductOrderModel> list = new ArrayList<>();
        for(Product product : allproducts){
            list.add(new ProductOrderModel(product.getId()+"",product.getQuantity()+""));
        }
        orderArray orderArray = new orderArray(list);

        CreateOrder createOrder = new CreateOrder(loginModel.getResult().getAccessToken(),loginModel.getResult().getUserdata().getUserId()+"",orderArray);

        if (Utilities.isNetworkAvailable(this)) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<LikeModel> call = service.makeOrder(createOrder);
            call.enqueue(new Callback<LikeModel>() {
                @Override
                public void onResponse(Call<LikeModel> call, Response<LikeModel> response) {

                    if (response.code() == 200) {


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

    public void getAllCost(){

        Double sum = 0.0;
        for(Product product:allproducts){
            sum += Double.parseDouble(product.getPrice())*product.getQuantity();
        }
        sum+= 54.0;
        String result = sum + "ريال سعودي";
        cost_txt.setText(result);
        phase1();

    }


    @OnClick(R.id.phase1_btn)
    public  void  phase1_btn(){
        phase2();
    }

    @OnClick(R.id.phase2_btn)
    public  void  phase2_btn(){

        makeOrder();
        phase3();
    }

    public void phase1(){
        phase = 1;
        allcost_lyt.setVisibility(View.VISIBLE);
        paywhenreceive_lyt.setVisibility(View.GONE);
        phase3_src.setVisibility(View.GONE);
        phase4_src.setVisibility(View.GONE);
    }

    public void phase2(){
        phase = 2;
        allcost_lyt.setVisibility(View.GONE);
        paywhenreceive_lyt.setVisibility(View.VISIBLE);
        phase3_src.setVisibility(View.GONE);
        phase4_src.setVisibility(View.GONE);
    }

    public void phase3(){
        phase = 3;
        allcost_lyt.setVisibility(View.GONE);
        paywhenreceive_lyt.setVisibility(View.GONE);
        phase3_src.setVisibility(View.VISIBLE);
        phase4_src.setVisibility(View.GONE);
    }

    public void phase4(){
        phase = 4;
        allcost_lyt.setVisibility(View.GONE);
        paywhenreceive_lyt.setVisibility(View.GONE);
        phase3_src.setVisibility(View.GONE);
        phase4_src.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.objection)
    public void objection(){
        phase4();
    }

    @Override
    public void onBackPressed() {
        switch (phase){
            case 1:
                super.onBackPressed();
                break;
            case 2:
                phase1();
                break;
            case 3:
                phase2();
                break;
            case 4:
                phase3();
                break;

        }

    }

    @OnClick({R.id.back_btn,R.id.img_back})
    public void back_btn(){
        onBackPressed();
    }
}
