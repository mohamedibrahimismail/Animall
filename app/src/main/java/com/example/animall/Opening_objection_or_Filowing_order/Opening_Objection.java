package com.example.animall.Opening_objection_or_Filowing_order;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.animall.R;

public class Opening_Objection extends AppCompatActivity {
    @BindView(R.id.scroll1)
    ScrollView scroll1;
    @BindView(R.id.scroll2)
    ScrollView scroll2;
    @BindView(R.id.img_back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening__objection);
        ButterKnife.bind(this);
    }
    @OnClick
    public void open_objection(View view) {
        scroll1.setVisibility(View.GONE);
        scroll2.setVisibility(View.VISIBLE);
    }
    @OnClick
    public void GoBack(View view) {
        scroll1.setVisibility(View.VISIBLE);
        scroll2.setVisibility(View.GONE);
    }
}
