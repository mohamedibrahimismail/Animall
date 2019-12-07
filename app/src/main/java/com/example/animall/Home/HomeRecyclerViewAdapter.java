package com.example.animall.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animall.Data.Remote.Models.Home.Category_;
import com.example.animall.R;
import com.example.animall.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Category_> list;
    HandelClicked handelClicked;

    public interface HandelClicked{
        public void HandleClicked(Category_ category);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.home_item_img)
        CircleImageView home_item_img;
        @BindView(R.id.home_item_txt)
        TextView home_item_txt;
        @BindView(R.id.home_item_lyt)
        LinearLayout home_item_lyt;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handelClicked.HandleClicked(list.get(getPosition()));
                }
            });

        }

        public void setData(Category_ category){
            Picasso.get().load(category.getPhoto()).into(home_item_img);
            if(Utilities.getLang(context).equals("ar")){
                home_item_txt.setText(category.getCategoryAr());
            }else {
                home_item_txt.setText(category.getCategoryEn());
            }
        }

    }


    public HomeRecyclerViewAdapter(Context context,List<Category_> list) {

        this.context = context;
        this.list=list;
        handelClicked = (HandelClicked)context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recyclerview_list_item, parent, false);
        int width = (parent.getMeasuredWidth()/2)-10;
        int height = width;
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width,height);
        layoutParams.setMargins(10,10,10,10);
        itemView.setLayoutParams(layoutParams);

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

