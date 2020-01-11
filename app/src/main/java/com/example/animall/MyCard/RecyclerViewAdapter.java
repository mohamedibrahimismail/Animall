package com.example.animall.MyCard;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Product> list= new ArrayList<>();
    Communicator communicator;

    interface Communicator{
        public void showProductDetails(Product product);
        public void removeProduct(Product product);
        public void modifyQuantity(Product product);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.deletecard)
        ImageView deletecard;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.quantity_txt)
        TextView quantity_txt;
        @BindView(R.id.price_txt)
        TextView price_txt;
        @BindView(R.id.modify)
        TextView modify;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communicator.showProductDetails(list.get(getPosition()));
                }
            });

        }

        @OnClick(R.id.modify)
        public void modifyQuantity(){
            communicator.modifyQuantity(list.get(getPosition()));
        }

        @OnClick(R.id.deletecard)
        public void removeFromCard(){
            communicator.removeProduct(list.get(getPosition()));
        }

        public void setData(Product product) {
            Picasso.get()
                    .load(product.getPhoto())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(img);
            if (Utilities.getLang(context).equals("ar")) {
                name.setText(product.getProductNameAr());
                quantity_txt.setText(product.getQuantity() + "طن");

            } else {
                name.setText(product.getProductNameEn());
                quantity_txt.setText(product.getQuantity() + "ton");

            }
            price_txt.setText((Double.parseDouble(product.getPrice()) * product.getQuantity()) + "RS");


        }


    }

    public void setList(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.communicator = (Communicator)context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycard_list_item, parent, false);

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

