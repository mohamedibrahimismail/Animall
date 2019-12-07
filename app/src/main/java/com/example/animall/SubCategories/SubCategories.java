package com.example.animall.SubCategories;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animall.Data.Remote.GetDataService;
import com.example.animall.Data.Remote.Models.Home.Category_;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.Category;
import com.example.animall.Data.Remote.Models.SubCategoriesModel.SubCategoriesModel;
import com.example.animall.Data.Remote.RetrofitClientInstance;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategories extends AppCompatActivity implements SubCategoryRecyclerViewAdapter.HandelClicked{

    String accessToken = "5d8e1373028a65d8e1373028a75d8e1373028a85d8e1373028a95d8e1373028aa";
    private static final String TAG = "SubCategory";

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.receclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;

    Category_ category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        ButterKnife.bind(this);
        if(getIntent().hasExtra("category")){
           category = (Category_)getIntent().getParcelableExtra("category");
           if(Utilities.getLang(this).equals("ar")){
               title.setText(category.getCategoryAr());
               loadDataOnline(category.getId()+"");
           }else {
               title.setText(category.getCategoryEn());
           }
        }

    }

    @OnClick(R.id.back_btn)
    public void goBack(){
        finish();
    }

    public void loadDataOnline(String id) {

        if (Utilities.isNetworkAvailable(this)) {
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<SubCategoriesModel> call = service.getSubCategories(accessToken,id);
            call.enqueue(new Callback<SubCategoriesModel>() {
                @Override
                public void onResponse(Call<SubCategoriesModel> call, Response<SubCategoriesModel> response) {

                    if (response.code() == 200) {
                        SubCategoriesModel subCategoriesModel = response.body();
                        if (subCategoriesModel.getStatue().equals("done")) {
                            handleRecyclerview(subCategoriesModel.getResult().getSubCategory().getCategory());
                        }
                    }
                }

                @Override
                public void onFailure(Call<SubCategoriesModel> call, Throwable t) {

                    Log.e("error", t.getMessage().toString());
                    Utilities.showMessage(SubCategories.this, "Something went wrong...Please try later!");
                }
            });
        }
    }

    public void handleRecyclerview(List<Category> category){
        recyclerView.setNestedScrollingEnabled(false);
        SubCategoryRecyclerViewAdapter recyclerViewAdapter = new SubCategoryRecyclerViewAdapter(this,category);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }



    @Override
    public void HandleClicked(Category category) {

    }
}
