package com.coolascode.emarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.coolascode.emarket.R;
import com.coolascode.emarket.modal.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    Context context;
    ArrayList<Cart> cartArrayList;

    public CartAdapter(Context context, ArrayList<Cart> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartArrayList.get(position);

        holder.productName.setText(cart.getProductName());
        holder.productPrice.setText(cart.getProductPrice());
        holder.productQuantity.setText(cart.getProductQuantity());
        Picasso.get().load(cart.getProductImage()).into(holder.productImage);
        holder.deleteFromCart.setOnClickListener(v->{

        });
        holder.layout.setOnClickListener(v->{

        });
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        TextView productName,productPrice,productQuantity;
        ImageView productImage;
        Button deleteFromCart;
        CardView layout;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName_cart);
            productPrice = itemView.findViewById(R.id.productPrice_cart);
            productImage = itemView.findViewById(R.id.productImage_cart);
            deleteFromCart = itemView.findViewById(R.id.deleteFromCart);
            productQuantity = itemView.findViewById(R.id.productQuantity_cart);
            layout = itemView.findViewById(R.id.cart_list_layout);
        }
    }
}
