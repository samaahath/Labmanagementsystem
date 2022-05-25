package com.book.labmanagementsysytem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Addequipment extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout spinnerlayout;
    RelativeLayout emptylayout;
    private FirebaseFirestore db;
    Map<String, Object> data;
    Button btnsave;
    LinearLayout lvparent;
    EditText edtProductcode;
    EditText edtProductName;
    EditText edtShippingcost;
    EditText edtCost;
    EditText edtPrice;
    EditText edtQuantity;
    EditText edtMinquantity;
    EditText edtDescription;
    EditText ExpirydateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addequipment);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add a new equipment");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        spinnerlayout = findViewById(R.id.layoutspinner);
        emptylayout = findViewById(R.id.emptylayout);
        spinnerlayout.setVisibility(View.GONE);
        emptylayout.setVisibility(View.GONE);
        btnsave = findViewById(R.id.btnsave);
        lvparent = findViewById(R.id.lvparent);
        edtProductcode = findViewById(R.id.edtProductCode);
        edtProductName = findViewById(R.id.edtProductName);
        edtShippingcost = findViewById(R.id.edtShippingCost);
        edtCost = findViewById(R.id.edtCost);
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtMinquantity = findViewById(R.id.edtMinQuantity);
        edtDescription = findViewById(R.id.edtDescription);
        ExpirydateText = findViewById(R.id.EdtExpiryDate);
        ExpirydateText.setInputType(InputType.TYPE_NULL);
        ExpirydateText.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new java.util.Date()));

        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                        ConnectivityManager cm = (ConnectivityManager) Addequipment.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        if (activeNetwork != null) {
                            saveequipment();
                        } else {
                            Toast.makeText(Addequipment.this, "No internet connection, please try again.", Toast.LENGTH_LONG).show();
                        }



            }


        });
    }
    public Boolean isEmpty(String strValue) {
        return strValue == null || strValue.trim().equals((""));
    }
    public void ShowSnackBar(String message) {
        Snackbar.make(lvparent, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(Addequipment.this.getColor(android.R.color.holo_red_light))
                .show();
    }
    private void saveequipment()
    {
       Addequipment.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        spinnerlayout.setVisibility(View.VISIBLE);
        data = new HashMap<>();
        data.put("Code", edtProductcode.getText().toString().trim());
        data.put("Entry Date", edtProductName.getText().toString().trim());
        data.put("ExpDate", edtProductName.getText().toString().trim());
        data.put("Image", edtProductName.getText().toString().trim());
        data.put("Location", edtProductName.getText().toString().trim());
        data.put("Manufacturer", edtProductName.getText().toString().trim());
        data.put("MfgDate", edtProductName.getText().toString().trim());
        data.put("Name", edtProductName.getText().toString().trim());
        data.put("Quantity", edtProductName.getText().toString().trim());
        data.put("Received Date", edtProductName.getText().toString().trim());
        data.put("Status", edtProductName.getText().toString().trim());
        //get Database
        db = FirebaseFirestore.getInstance();


        db.collection("Equipments")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       // Toast.makeText(Addequipment.this, documentReference.getId(), Toast.LENGTH_LONG).show();
                        onBackPressed();
                        finish();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Addequipment.this,e.toString(), Toast.LENGTH_LONG).show();
                    }

                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}