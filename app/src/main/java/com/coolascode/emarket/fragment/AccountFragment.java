package com.coolascode.emarket.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coolascode.emarket.R;
import com.coolascode.emarket.activity.UserLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference rootRef;
    Button logOutBtn;
    String currentUserId;
    TextView userName,userEmail;
    ProgressDialog progressDialog;



    public AccountFragment() {
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
        view = inflater.inflate(R.layout.fragment_account, container, false);
        logOutBtn = view.findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserId = user.getUid();

        progressDialog =new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userName = view.findViewById(R.id.acount_name);
        userEmail = view.findViewById(R.id.account_Email);
        userEmail.setText(user.getEmail());
        rootRef.child("Users").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Name")){
                    String name = snapshot.child("Name").getValue().toString();
                    userName.setText(name);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
        logOutBtn.setOnClickListener(v->{
            mAuth.signOut();
            startActivity(new Intent(getContext(), UserLoginActivity.class));
            getActivity().finishAffinity();

        });
        return view;
    }
}