package com.example.animall.Product;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.animall.Data.Remote.Models.Product.Slider;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Slider> list;

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.text)
    TextView text;
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

    String lang ;

    public ViewPagerAdapter(Context context, List<Slider> list) {
        this.context = context;
        this.list = list;
        lang = Utilities.getLang(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.product_slider_item_list, null);
        ButterKnife.bind(this,view);
        Picasso.get()
                .load(list.get(position).getPhoto())
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(imageView);
        if(lang.equals("ar")){
            title.setText(list.get(position).getProductNameAr());
            text.setText(list.get(position).getProductDescriptionAr());
            old_price.setText(list.get(position).getBeforePrice());
            old_price.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            new_price.setText(list.get(position).getPrice());
            address_txt.setText(list.get(position).getAddressAr());
        }else {
            title.setText(list.get(position).getProductNameEn());
            text.setText(list.get(position).getProductDescriptionEn());
            old_price.setText(list.get(position).getBeforePrice());
            new_price.setText(list.get(position).getPrice());
            address_txt.setText(list.get(position).getAddressEn());
        }
        ratingBar.setRating(list.get(position).getRate());
        rate_txt.setText(list.get(position).getRate()+"");
        if(list.get(position).getLike()){
            like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
        }else {
            like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_notactive));
        }
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}

