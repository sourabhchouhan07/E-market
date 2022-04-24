package com.coolascode.emarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.coolascode.emarket.R;
import com.coolascode.emarket.activity.ProductDetailsActivity;
import com.coolascode.emarket.modal.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        String name = product.getProductName();
        holder.productName.setText(name);
        holder.productPrice.setText(product.getProductPrice());

        Picasso.get().load(product.getImageUri()).placeholder(R.drawable.image).into(holder.productImage);

        holder.cardView.setOnClickListener(v->{
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("shopid",product.getShopUid());
            intent.putExtra("key",product.getKey());
            intent.putExtra("name",product.getProductName());
            intent.putExtra("price",product.getProductPrice());
            intent.putExtra("imageurl",product.getImageUri());
            intent.putExtra("desc",product.getProductDesc());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice;
        ImageView productImage;
        CardView cardView;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName_list);
            productPrice = itemView.findViewById(R.id.productPrice_list);
            productImage = itemView.findViewById(R.id.productImage_list);
            cardView = itemView.findViewById(R.id.productList_layout);
        }
    }
}
