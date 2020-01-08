package com.example.animall.HotelDeals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.animall.Data.Remote.Models.HotelDeal.HotelDeal;
import com.example.animall.OrderHotelDeals.OrderHotelDeal;
import com.example.animall.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelDealsAdapter extends RecyclerView.Adapter<HotelDealsAdapter.HotelDealsHolder> {
    List<HotelDeal> hotelDealList;
    Context context;
    Hotel_Deals hotel_deals;
    public HotelDealsAdapter(List<HotelDeal> hotelDealList, Context context) {
        this.hotelDealList = hotelDealList;
        this.context = context;

    }

    @NonNull
    @Override
    public HotelDealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.hotel_deal_item,parent,false);

        return new HotelDealsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelDealsHolder holder, int position) {
        holder.setData(hotelDealList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent = new Intent(context,OrderHotelDeal.class);
             intent.putExtra("hotel_deal",hotelDealList.get(position).getHotelDeal());
             context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return hotelDealList.size();
    }

    class HotelDealsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.product_img)
        ImageView product_img;
        @BindView(R.id.txt_discount)
        TextView discount;
        @BindView(R.id.seller_name)
        TextView By;
        @BindView(R.id.animal_type)
        TextView animal_type;
        @BindView(R.id.service)
        TextView service;
        @BindView(R.id.time_host)
        TextView time_host;
        @BindView(R.id.day_host)
        TextView day_host;
        @BindView(R.id.offer)
        TextView offer;
        @BindView(R.id.site)
        TextView site;
        public HotelDealsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HotelDeal hotelDeal) {
            Picasso.get().load(hotelDeal.getPhoto()).into(product_img);
            discount.setText(hotelDeal.getOfferPresent()+"%");
            By.setText(hotelDeal.getSeller());
            animal_type.setText(hotelDeal.getAnimalType());
            service.setText(hotelDeal.getService());
            time_host.setText(hotelDeal.getTimeHost());
            day_host.setText(hotelDeal.getDayHost());
            offer.setText(hotelDeal.getOffer());
            site.setText(hotelDeal.getSite());
        }
    }
}
