package com.coolascode.emarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.coolascode.emarket.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserLoginActivity extends AppCompatActivity {

    TextInputEditText userLoginEmail, userLoginPass;
    FirebaseAuth mAuth;
    Button loginBtn;
    TextView forgetPass;
    private FusedLocationProviderClient fusedLocationProviderClient;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(UserLoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(UserLoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Login...");

        userLoginEmail = findViewById(R.id.login_email_edit_text);
        userLoginPass = findViewById(R.id.login_password_edit_text);
        loginBtn = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        forgetPass = findViewById(R.id.forget_password);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserLoginActivity.this, ForgetPasswordActivity.class);

                startActivity(i);

                // close this activity


            }
        });
    }
    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(UserLoginActivity.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Toast.makeText(UserLoginActivity.this, "Address : "+addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public void SignBtn(View view) {

        Intent i = new Intent(UserLoginActivity.this, UserSignUpActivity.class);

        startActivity(i);

    }

    private void login() {
        String email = userLoginEmail.getText().toString();
        String password = userLoginPass.getText().toString();

        if (email.isEmpty()) {
            userLoginEmail.setError("Email can't be Empty!");
            userLoginEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userLoginEmail.setError("Invalid Email!");
            userLoginEmail.requestFocus();
        } else if (password.isEmpty()) {
            userLoginPass.setError("Password can't be Empty!");
            userLoginPass.requestFocus();
        } else {
progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
//                                currentUser = mAuth.getCurrentUser();\
                                Toast.makeText(UserLoginActivity.this, "Authentication Successfull",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                sentToMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(UserLoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                // ...
                            }

                            // ...
                        }
                    });
        }
    }

    private void sentToMainActivity() {
        Intent i = new Intent(UserLoginActivity.this, MainActivity.class);
        startActivity(i);
        finishAffinity();
    }
}