package com.coolascode.emarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,pincode,locality,address,phoneNumber;
    Button addAddressBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        name = findViewById(R.id.nameInput_address);
        pincode = findViewById(R.id.pincodeInput_address);
        locality = findViewById(R.id.localityInput_address);
        address = findViewById(R.id.addressInput);
        phoneNumber = findViewById(R.id.mobileNumberInput_address);

        addAddressBtn = findViewById(R.id.addAddressButton);

        addAddressBtn.setOnClickListener(v->{
            addAddress();
        });
    }

    private void addAddress() {

        String _name = name.getText().toString();
        String _pincode = pincode.getText().toString();
        String _locality = locality.getText().toString();
        String _address = address.getText().toString();
        String number = phoneNumber.getText().toString();

        if (_name.isEmpty())
        {
            name.setError("It should not empty.");
            name.requestFocus();
        }else if (_pincode.isEmpty()){
            pincode.setError("It should not empty.");
            pincode.requestFocus();
        }else if (_locality.isEmpty()){
            locality.setError("It should not empty.");
            locality.requestFocus();
        }else if(_address.isEmpty()){
            address.setError("It should not empty.");
            address.requestFocus();
        }else if(number.isEmpty()){
            phoneNumber.setError("It should not empty.");
            phoneNumber.requestFocus();
        }else{
            HashMap<String,String> addressInput = new HashMap<>();

            addressInput.put("Name",_name);
            addressInput.put("pinCode",_pincode);
            addressInput.put("locality",_locality);
            addressInput.put("address",_address);
            addressInput.put("phoneNumber",number);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            String currentUser = user.getUid();

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

            rootRef.child("Address").child(currentUser).setValue(addressInput)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AddAddressActivity.this, "Address added successfully..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddAddressActivity.this,MainActivity.class));
                        }
                    });
        }
    }
}