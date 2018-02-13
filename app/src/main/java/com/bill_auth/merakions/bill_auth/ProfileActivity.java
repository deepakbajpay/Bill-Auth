package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView nameEt,mobileEt,emailEt,shopEt,gstEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameEt=findViewById(R.id.nameP);
        mobileEt=findViewById(R.id.MobilenumberP);
        emailEt=findViewById(R.id.emailidP);
        shopEt=findViewById(R.id.shopP);
        gstEt=findViewById(R.id.gstP);

        String name=nameEt.getText().toString().trim();

        String mobile=nameEt.getText().toString().trim();
        String email=emailEt.getText().toString().trim();
        String shop=shopEt.getText().toString().trim();
        String gst=gstEt.getText().toString().trim();
    }
}
