package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.regex.Matcher;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText emailEt, passwordEt;
    Button loginBt;
    TextView newUserTv;
    FirebaseAuth mAuth;
    AVLoadingIndicatorView avl ;
    private String uid;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userType = getIntent().getStringExtra(Constants.USER_TYPE_TAG);
        mAuth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.emaillogin);
        passwordEt = findViewById(R.id.passwordenter);
        loginBt = findViewById(R.id.login);
        newUserTv = findViewById(R.id.newuser);
        avl = findViewById(R.id.avi_login);

        loginBt.setOnClickListener(this);
        newUserTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newuser:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(Constants.USER_TYPE_TAG,userType);
                startActivity(intent);
                break;

            case R.id.login:
                String email = emailEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Email address", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    login(email, password);
                }
                break;

        }
    }

    public void login(String email,String password) {
        showAvi();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideAvi();
                if (task.isSuccessful()) {
                    uid = mAuth.getCurrentUser().getUid();
                    FirebaseMessaging.getInstance().subscribeToTopic(uid);
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.putExtra(Constants.USER_TYPE_TAG,userType);
                    startActivity(intent);

                } else {
                    hideAvi();
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAvi(){
        loginBt.setVisibility(View.GONE);
        avl.setVisibility(View.VISIBLE);
    }


    private void hideAvi(){
        loginBt.setVisibility(View.VISIBLE);
        avl.setVisibility(View.GONE);
    }
}
