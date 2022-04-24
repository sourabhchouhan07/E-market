package com.coolascode.emarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserSignUpActivity extends AppCompatActivity {

    TextView ForSeller;
    String[] shops;
    Dialog dialog;
    DatabaseReference rootRef;
    FirebaseUser currentUser;
    TextView textview;
    ArrayAdapter<String> adapterShops;
    TextInputEditText userEmail,userName,userPass,userRePass;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);



        rootRef= FirebaseDatabase.getInstance().getReference();

        TextView sellerSignUp=findViewById(R.id.forSellerBtn);
        Button signUpbtn=findViewById(R.id.signup_signup_btn);
        intialize();

        sellerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserSignUpActivity.this, ShopRegisterActivity.class);

                startActivity(i);
            }
        });

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singin();
            }
        });

    }
    private void intialize() {

        userEmail=findViewById(R.id.signup_email_edit_text);
        userName=findViewById(R.id.signup_name_edit_text);
        userPass=findViewById(R.id.signup_password_edit_text);
        userRePass=userPass;


    }

    private void singin() {
        String email = userEmail.getText().toString();
        String name = userName.getText().toString();
        String password = userPass.getText().toString();
        String rePass = userRePass.getText().toString();

        if (email.isEmpty()) {
            userEmail.setError("Email can't be Empty!");
            userEmail.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            userEmail.setError("Invalid Email!");
            userEmail.requestFocus();
        }
        else if (password.isEmpty()|| password.toString().length()<6)
        {
            userPass.setError("Password can't be Empty!");
            userPass.requestFocus();
        }
        else if (rePass.isEmpty())
        {
            userRePass.setError("Re Enter the Password!");
            userRePass.requestFocus();
        }
        else if (!password.equals(rePass))
        {
            userRePass.setError("Password doesn't matched!");
            userRePass.requestFocus();
        }else {

            final HashMap<String,String> profile = new HashMap<>();
            profile.put("Name",name);
            profile.put("Email Id",email);
            loading();
            FirebaseAuth mAuth;

            mAuth=FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email, rePass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                currentUser = mAuth.getCurrentUser();
                                String currentUserId = currentUser.getUid();
                                loading.dismiss();
                                rootRef.child("Users").child(currentUserId).setValue(profile)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isComplete())
                                                {
                                                    Toast.makeText(UserSignUpActivity.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(UserSignUpActivity.this, "Error!...", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                sentToMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                loading.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(UserSignUpActivity.this, "Error : " + message,
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }

    private void loading() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("Sign Up.....");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
    }

    private void sentToMainActivity() {

        Intent i = new Intent(UserSignUpActivity.this, VerifyEmailActivity.class);
        startActivity(i);
    }

    public void loginbtn(View view) {
        Intent i = new Intent(UserSignUpActivity.this, UserLoginActivity.class);
        startActivity(i);

    }

}