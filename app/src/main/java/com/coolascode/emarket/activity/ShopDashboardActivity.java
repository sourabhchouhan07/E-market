package com.coolascode.emarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.TextViewCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.coolascode.emarket.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopDashboardActivity extends AppCompatActivity {
    CardView addProduct;
     FirebaseAuth mAuth;
     FirebaseUser currentUser;
     DatabaseReference rootRef;
     String currentUserId;
     TextView shop_name;
     String shopName;
     CardView logOut,productListCard;
ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_dashboard);


        logOut = findViewById(R.id.seller_log_out);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        currentUserId=currentUser.getUid();
        rootRef=FirebaseDatabase.getInstance().getReference();
        shop_name=findViewById(R.id.shopName);
        addProduct = findViewById(R.id.addProduct);
        productListCard = findViewById(R.id.product_list_card);


        productListCard.setOnClickListener(v->{
            Intent intent=new Intent(ShopDashboardActivity.this, ProductListActivity.class);
            intent.putExtra("uid",currentUserId);
            intent.putExtra("shopname",shopName);
            startActivity(intent);
        });

        logOut.setOnClickListener(v->{
            mAuth.signOut();
            startActivity(new Intent(ShopDashboardActivity.this,UserLoginActivity.class));
            finishAffinity();
        });

        addProduct.setOnClickListener(v->{
            startActivity(new Intent(ShopDashboardActivity.this,AddProductActivity.class));
        });
        loading();
        rootRef.child("Shops").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    shopName=snapshot.child("shopName").getValue().toString();
                    shop_name.setText(shopName);
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loading() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("Loading.....");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
    }

}