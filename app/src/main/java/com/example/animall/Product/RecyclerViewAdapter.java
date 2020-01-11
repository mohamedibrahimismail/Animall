package com.example.animall.Product;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animall.Data.Remote.Models.Product.Product;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Product> list;
    String lang ;

    Communicator communicator;

    public interface HandelClicked{
        public void HandleClicked(Product product);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.like)
        ImageView like;
        @BindView(R.id.title)
        TextView title;
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


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            old_price.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communicator.HandleClicked(getPosition(),list.get(getPosition()));
                }
            });

        }

        public void setData(Product product){
            Picasso.get()
                    .load(product.getPhoto())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(imageView);
            if(lang.equals("ar")){
                title.setText(product.getProductNameAr());
                old_price.setText(product.getBeforePrice());
                new_price.setText(product.getPrice()+"RS");
                address_txt.setText(product.getAddressAr());
            }else {
                title.setText(product.getProductNameEn());
                old_price.setText(product.getBeforePrice());
                new_price.setText(product.getPrice()+"RS");
                address_txt.setText(product.getAddressEn());
            }
            ratingBar.setRating(product.getRate());
            rate_txt.setText(product.getRate()+"");
            if(product.getLike()){
                like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
            }else {
                like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_notactive));
            }

        }



        @OnClick(R.id.like)
        public void likeProduct()
        {
            if(list.get(getPosition()).getLike()){
                list.get(getPosition()).setLike(false);
                like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_notactive));

            }else {
                list.get(getPosition()).setLike(true);
                like.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
            }

            communicator.like(list.get(getPosition()).getId()+"");
        }

    }

    public void setLike(int index ,Boolean status){
        list.get(index).setLike(status);
        notifyDataSetChanged();
    }

    public RecyclerViewAdapter(Context context, List<Product> list) {

        this.context = context;
        this.list=list;
        communicator = (Communicator) context;
        lang = Utilities.getLang(context);
        this.communicator = (Communicator)context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_recyclerview_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




}

