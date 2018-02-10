package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameEt,emailEt,mobileEt,addressEt,gstEt,passwordEt,passwordcnfEt;
    Button registerBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEt=findViewById(R.id.name);
        emailEt=findViewById(R.id.emailid);
        mobileEt=findViewById(R.id.Mobilenumber);
        addressEt=findViewById(R.id.shop);
        gstEt=findViewById(R.id.gst);

        passwordEt=findViewById(R.id.password);
        passwordcnfEt=findViewById(R.id.passwordcnf);
        registerBt=findViewById(R.id.register);

        String name=nameEt.getText().toString().trim();
        String email=emailEt.getText().toString().trim();
        String mobile=mobileEt.getText().toString().trim();
        String address=addressEt.getText().toString().trim();
        String gst=gstEt.getText().toString().trim();
        String password=passwordEt.getText().toString().trim();
        String passwordcnf=passwordcnfEt.getText().toString().trim();

        registerBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}
