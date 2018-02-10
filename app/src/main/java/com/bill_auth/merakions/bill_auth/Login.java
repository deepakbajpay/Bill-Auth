package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText emailEt, passwordEt;
    Button loginBt;
    TextView newUserTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEt = findViewById(R.id.emaillogin);
        passwordEt = findViewById(R.id.passwordenter);
        loginBt = findViewById(R.id.login);
        newUserTv = findViewById(R.id.newuser);

        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        loginBt.setOnClickListener(this);
        newUserTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newuser:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.login:
                break;

        }
    }
}
