package com.coolascode.emarket.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.widget.ImageViewCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolascode.emarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference rootRef;
    String currentUid;
    AppCompatEditText productName,productPrice,productDesc;
    AppCompatButton addProductBtn;
    TextView imageSelect;
    Uri selectedImage;
    ImageView productImage;
    TextView shopName;
    ImageView backBtn;
    String shop_name;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        productName =  findViewById(R.id.productNameInput);
        productPrice = findViewById(R.id.productPriceInput);
        productDesc = findViewById(R.id.productDescriptionInput);
        addProductBtn = findViewById(R.id.addProductButton);
        imageSelect = findViewById(R.id.imageSelectProduct);
        productImage = findViewById(R.id.productImage);
        shopName=findViewById(R.id.shopName_addProduct);
        backBtn=findViewById(R.id.backButton_addProduct);
        loading=new ProgressDialog(this);

        backBtn.setOnClickListener(v->{
            onBackPressed();
        });


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        rootRef= FirebaseDatabase.getInstance().getReference();


        currentUid = user.getUid();

loading();
rootRef.child("Shops").child(currentUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    shop_name=snapshot.child("shopName").getValue().toString();
                    shopName.setText(shop_name);
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addProductBtn.setOnClickListener(v->{


            addProduct();
        });

        imageSelect.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,1);
        });

    }

    private void loading() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage(" Uploading.....");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == 1)
        {

            selectedImage = data.getData();
            productImage.setImageURI(selectedImage);


        }

    }

    private void addProduct() {
        loading();

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference();

        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String desc = productDesc.getText().toString();


        if(selectedImage!=null){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            String key = rootRef.push().getKey().toString();
            StorageReference storageReference = storage.getReference().child("Product Image").child(currentUid).child(key);
            storageReference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String ImageUri = uri.toString();
                                Map<String,String> product = new HashMap<>();
                                product.put("productName",name);
                                product.put("productPrice",price);
                                product.put("productDesc",desc);
                                product.put("imageUri",ImageUri);
                                product.put("shopUid",currentUid);


                                product.put("key",key);
                                productRef.child("Products").child(currentUid).child(key).setValue(product)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                productName.setText("");
                                                productPrice.setText("");
                                                productDesc.setText("");
                                                Uri uri1=null;
                                                productImage.setImageURI(uri1);
                                                loading.dismiss();
                                                Toast.makeText(AddProductActivity.this, "Product Added Successfully...", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        });
                    }
                }
            });
        }else{
            Map<String,String> product = new HashMap<>();
            product.put("productName",name);
            product.put("productPrice",price);
            product.put("productDesc",desc);
            product.put("shopUid",currentUid);
            String key = rootRef.push().getKey().toString();
            product.put("key",key);
            productRef.child("Products").child(currentUid).child(key).setValue(product)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            productName.setText("");
                            productPrice.setText("");
                            productDesc.setText("");
                            loading.dismiss();
                            Toast.makeText(AddProductActivity.this, "Product Added Successfully...", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProductActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


}