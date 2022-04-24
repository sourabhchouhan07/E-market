package com.coolascode.emarket.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.coolascode.emarket.activity.AddAddressActivity;
import com.coolascode.emarket.adapter.CartAdapter;
import com.coolascode.emarket.adapter.ShopAdapter;
import com.coolascode.emarket.modal.Cart;
import com.coolascode.emarket.modal.Shops;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {

   View view;
    private RecyclerView cartRecycler;
    ArrayList<Cart> catsArrayList;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String currentUid;
    private CartAdapter cartAdapter;
    Button buyNow;


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_orders, container, false);
        cartRecycler = view.findViewById(R.id.cart_recycler);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        currentUid = mAuth.getCurrentUser().getUid();
        buyNow = view.findViewById(R.id.buy_now);

        catsArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(),catsArrayList);
        cartRecycler.setAdapter(cartAdapter);


        buyNow.setOnClickListener(v->{
            startActivity(new Intent(getContext(), AddAddressActivity.class));
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        retrieve();
    }

    void retrieve(){
        database.child("Cart").child(currentUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                catsArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Cart cart = snapshot1.getValue(Cart.class);
                    catsArrayList.add(cart);
                }

                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}