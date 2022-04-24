package com.coolascode.emarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coolascode.emarket.R;
import com.coolascode.emarket.activity.ProductListActivity;
import com.coolascode.emarket.modal.Shops;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopsViewHolder> {

    Context context;
    ArrayList<Shops> shopsArrayList;

    public ShopAdapter(Context context, ArrayList<Shops> shopsArrayList) {
        this.context = context;
        this.shopsArrayList = shopsArrayList;
    }

    @NonNull
    @Override
    public ShopsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.shop_item,parent,false);

        return new ShopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsViewHolder holder, int position) {

        Shops shop=shopsArrayList.get(position);
        holder.shopName.setText(shop.getShopName());
        holder.shopAddress.setText(shop.getShopLocation());
        holder.shopType.setText(shop.getShopType());

        holder.shopListLayout.setOnClickListener(v -> {
            Intent intent=new Intent(context, ProductListActivity.class);
            intent.putExtra("uid",shop.getUserId());
            intent.putExtra("shopname",shop.getShopName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return shopsArrayList.size();
    }


    public static class ShopsViewHolder extends RecyclerView.ViewHolder{

        TextView shopName,shopAddress,shopType;
        CardView shopListLayout;
        public ShopsViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName=itemView.findViewById(R.id.shopName_list);
            shopAddress=itemView.findViewById(R.id.shopAddress_list);
            shopType=itemView.findViewById(R.id.shopCategory_list);
            shopListLayout=itemView.findViewById(R.id.shopList_id);

        }
    }
}
