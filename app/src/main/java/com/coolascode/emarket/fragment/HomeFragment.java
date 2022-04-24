package com.coolascode.emarket.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.coolascode.emarket.adapter.ShopAdapter;
import com.coolascode.emarket.modal.Shops;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView shopList_recycler;
    ArrayList<Shops> shopsArrayList;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String currentUid;
    private ShopAdapter shopAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


//    TODO: Rename and change types and number of parameters
//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        shopList_recycler=view.findViewById(R.id.shop_list_home);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        shopsArrayList = new ArrayList<>();
        shopAdapter =new ShopAdapter(getContext(),shopsArrayList);
        shopList_recycler.setAdapter(shopAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reteriveUser();
    }

    private void reteriveUser(){
        database.child("Shops").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        shopsArrayList.clear();
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            Shops shop=snapshot1.getValue(Shops.class);
                            shopsArrayList.add(shop);
                        }
                        shopAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}