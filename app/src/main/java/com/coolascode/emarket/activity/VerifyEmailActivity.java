package com.coolascode.emarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmailActivity extends AppCompatActivity {


    TextView verifyMsg;
    Button verifyBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);


        verifyBtn=findViewById(R.id.verfiyemail);
        verifyMsg=findViewById(R.id.msg);
        auth=FirebaseAuth.getInstance();


        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send verfication email
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(VerifyEmailActivity.this,"Verification mail is sent..",Toast.LENGTH_SHORT).show();


                    }
                });

                Intent i = new Intent(VerifyEmailActivity.this, UserLoginActivity.class);
                startActivity(i);

                // close this activity

                finish();
            }
        });
    }
}