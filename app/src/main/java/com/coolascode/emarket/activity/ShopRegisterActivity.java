package com.coolascode.emarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShopRegisterActivity extends AppCompatActivity {

    TextView textview;
    ArrayAdapter<String> adapterShops;
    TextView ForSeller;
    String[] shops;
    Dialog dialog;
    TextInputEditText shopName,shopEmail,shopPassword,shopRePassword;
    TextView shopType;
    Button shopSignUpBtn;
    ProgressDialog loading;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_register);

        TextView shopbtn=findViewById(R.id.shop_up_in);
        shopName = findViewById(R.id.shop_name);
        shopEmail = findViewById(R.id.shop_signup_email_);
        shopPassword = findViewById(R.id.shop_signup_password1);
        shopRePassword = findViewById(R.id.shop_signup_password2);
        shopType = findViewById(R.id.shop_type);
        shopSignUpBtn = findViewById(R.id.shop_signup_btn);

        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        
        shopSignUpBtn.setOnClickListener(v->{
            shopSignUp();
        });
        
        shopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShopRegisterActivity.this, UserLoginActivity.class);

                startActivity(i);
            }
        });


        shops = getResources().getStringArray(R.array.shop_list);

        shopType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(ShopRegisterActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(800,1200);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(ShopRegisterActivity.this, android.R.layout.simple_list_item_1,shops);
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        shopType.setText(adapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    private void shopSignUp() {
        String email = shopEmail.getText().toString();
        String name = shopName.getText().toString();
        String password = shopPassword.getText().toString();
        String rePass = shopRePassword.getText().toString();
        String shoptype = shopType.getText().toString();

        if (email.isEmpty()) {
            shopEmail.setError("Email can't be Empty!");
            shopEmail.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            shopEmail.setError("Invalid Email!");
            shopEmail.requestFocus();
        }
        else if (password.isEmpty()|| password.toString().length()<6)
        {
            shopPassword.setError("Password can't be Empty!");
            shopPassword.requestFocus();
        }
        else if (rePass.isEmpty())
        {
            shopRePassword.setError("Re Enter the Password!");
            shopRePassword.requestFocus();
        }
        else if (!password.equals(rePass))
        {
            shopRePassword.setError("Password doesn't matched!");
            shopRePassword.requestFocus();
        }else {

            final HashMap<String,String> profile = new HashMap<>();
            profile.put("shopName",name);
            profile.put("emailId",email);
            profile.put("shopType",shoptype);
            profile.put("shopLocation","Prayagraj-211004");
            loading();

            mAuth.createUserWithEmailAndPassword(email, rePass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Sign in success, update UI with the signed-in user's information
                                currentUser = mAuth.getCurrentUser();
                                String currentUserId = currentUser.getUid();
                                profile.put("userId",currentUserId);

                                loading.dismiss();
                                rootRef.child("Shops").child(currentUserId).setValue(profile)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isComplete())
                                                {
                                                    Toast.makeText(ShopRegisterActivity.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(ShopRegisterActivity.this, "Error!...", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                sentToMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                loading.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(ShopRegisterActivity.this, "Error : " + message,
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

        Intent i = new Intent(ShopRegisterActivity.this, VerifyEmailActivity.class);
        finishAffinity();
        startActivity(i);
    }
}