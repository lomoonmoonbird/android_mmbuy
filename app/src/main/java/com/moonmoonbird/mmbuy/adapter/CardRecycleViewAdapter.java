package com.moonmoonbird.mmbuy.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.moonmoonbird.mmbuy.R;
import com.moonmoonbird.mmbuy.model.Good;
import com.moonmoonbird.mmbuy.model.GoodList;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CardRecycleViewAdapter extends RecyclerView.Adapter<CardRecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<GoodList.GoodData> goods;

    public CardRecycleViewAdapter(Context mContext, ArrayList<GoodList.GoodData> goods) {
        this.mContext = mContext;
        this.goods = goods;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.home_card_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.good_name.setText(goods.get(position).Name);
        Picasso.get().load(Uri.parse(goods.get(position).Url)).into(holder.good_url);
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView good_name;
        ImageView good_url;
        MaterialCardView carView;


        MyViewHolder(View itemView){
            super(itemView);
            good_name = (TextView)itemView.findViewById(R.id.name);
            good_url = (ImageView)itemView.findViewById(R.id.url);
            carView = (MaterialCardView)itemView.findViewById(R.id.home_card);
        }
    }

}
