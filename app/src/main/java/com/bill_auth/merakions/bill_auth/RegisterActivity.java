package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,mobile,address,gst,password,passwordcnf;
    TextView login;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name);
        email=findViewById(R.id.emailid);
        mobile=findViewById(R.id.Mobilenumber);
        address=findViewById(R.id.shop);
        gst=findViewById(R.id.gst);
        password=findViewById(R.id.password);
        passwordcnf=findViewById(R.id.passwordcnf);
        register=findViewById(R.id.register);

    }
}
