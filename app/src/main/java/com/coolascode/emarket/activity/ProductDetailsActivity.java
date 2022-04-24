package com.coolascode.emarket.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.coolascode.emarket.fragment.OrdersFragment;
import com.coolascode.emarket.modal.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    String shopUid,key,url,price,name,dsc;
    TextView shopName,productName,productDesc,productPrice;
    ImageView productImg;
    DatabaseReference rootRef;
    String currentUid;
    ImageView back;
    Button addcart;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

       shopUid = getIntent().getStringExtra("shopid");
        key = getIntent().getStringExtra("key");
        name =getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        url = getIntent().getStringExtra("imageurl");
        dsc = getIntent().getStringExtra("desc");

        shopName = findViewById(R.id.shopName_details);
        productName = findViewById(R.id.productName_details);
        productDesc = findViewById(R.id.productDescription_details);
        productPrice = findViewById(R.id.productPrice_details);
        productImg = findViewById(R.id.productImage_details);
        back = findViewById(R.id.backButton_details);
        addcart = findViewById(R.id.addToCartButton);
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();

        productDesc.setText(dsc);
        productName.setText(name);
        productPrice.setText(price);
        Picasso.get().load(url).into(productImg);


        back.setOnClickListener(v->{
            onBackPressed();
        });
        addcart.setOnClickListener(v->{
            HashMap<String,String> cartItem = new HashMap<>();
            cartItem.put("productName",name);
            cartItem.put("productPrice",price);
            cartItem.put("productImage",url);
            cartItem.put("productQuantity","01");

            rootRef.child("Cart").child(currentUid).push().setValue(cartItem)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ProductDetailsActivity.this, "Saved in cart", Toast.LENGTH_LONG).show();
                        }
                    });


        });


    }

}