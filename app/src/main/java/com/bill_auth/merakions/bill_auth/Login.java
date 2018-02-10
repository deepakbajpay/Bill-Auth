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
    EditText email, password;
    Button login;
    TextView newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emaillogin);
        password = findViewById(R.id.passwordenter);
        login = findViewById(R.id.login);
        newUser = findViewById(R.id.newuser);

        newUser.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newuser:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
        }
    }
}
