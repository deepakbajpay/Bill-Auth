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
import android.widget.Toast;

import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Config;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.bill_auth.merakions.bill_auth.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wang.avi.AVLoadingIndicatorView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameEt, emailEt, mobileEt, addressEt, gstEt, passwordEt, passwordcnfEt;
    Button registerBt;
    String userType;
    AVLoadingIndicatorView avl;
    private FirebaseAuth mAuth;
    private UserItem user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userType = getIntent().getStringExtra(Constants.USER_TYPE_TAG);

        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.emailid);
        mobileEt = findViewById(R.id.Mobilenumber);
        addressEt = findViewById(R.id.shop);
        gstEt = findViewById(R.id.gst);
        avl = findViewById(R.id.avi_register);

        passwordEt = findViewById(R.id.password);
        passwordcnfEt = findViewById(R.id.passwordcnf);
        registerBt = findViewById(R.id.register);

        registerBt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:

                String name = nameEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                String mobile = mobileEt.getText().toString().trim();
                String address = addressEt.getText().toString().trim();
                String gst = gstEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();
                String confirmPassword = passwordcnfEt.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Utilities.showAlert(this, "Empty Field", "Please Enter your name", "OK", "Cancel");
                } else if (mobile.length() != 10) {
                    Utilities.showAlert(this, "Invalid phone number", "Please Enter valid phone number", "OK", "Cancel");
                } else if (TextUtils.isEmpty(gst)) {
                    Utilities.showAlert(this, "Empty GST number", "Please enter a valid GST number", "OK", "Cancel");
                } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
                    Utilities.showAlert(this, "Invalid email", "Please enter a valid email address", "OK", "Cancel");
                } else if (TextUtils.isEmpty(address)) {
                    Utilities.showAlert(this, "Empty address", "Please enter a valid address", "OK", "Cancel");
                } else if (password.length() < 8) {
                    Utilities.showAlert(this, "Invalid password", "Password length should be more than 8 character", "OK", "Cancel");
                } else if (!password.equals(confirmPassword)) {
                    Utilities.showAlert(this, "Passwords do not match", "Password and confirm password are different", "OK", "Cancel");
                } else {
                    if (Config.isConnected(getApplicationContext())) {
                        user = new UserItem();
                        user.setName(name);
                        user.setAddress(address);
                        user.setEmail(email);
                        user.setGst(gst);
                        user.setMobile(mobile);
                        user.setPassword(password);
                        register();
                    }
                }
                break;
        }
    }

    private void register() {
        showAvl();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user.setuId(task.getResult().getUser().getUid());
                    FirebaseMessaging.getInstance().subscribeToTopic(task.getResult().getUser().getUid());
                    Toast.makeText(RegisterActivity.this, "Registered...", Toast.LENGTH_SHORT).show();
                    uploadDetails();
                } else {
                    hideAvl();
                    System.out.println("RegisterActivity.onComplete " + task.getException());
                    Toast.makeText(RegisterActivity.this, "Registration Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void uploadDetails() {

        DatabaseReference dbreference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference childReference;
        if (Constants.WHOLE_SALER.equals(userType)) {
            childReference = dbreference.child(Constants.CHILD_WHOLE_SALER);
        } else {
            childReference = dbreference.child(Constants.CHILD_SHOP_KEEPER);
        }

        childReference = childReference.child(user.getuId());
        try {
            childReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        System.out.println("DatabaseHandler.onComplete in upload details");
                        FirebaseMessaging.getInstance().subscribeToTopic(user.getuId());
                        Utilities.saveUserDetails(RegisterActivity.this, user);
                        Utilities.setUserType(RegisterActivity.this,userType);
                        Intent intent = new Intent(RegisterActivity.this, ShopKeeperMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    hideAvl();
                }
            });
        } catch (Exception e) {
            hideAvl();
            Toast.makeText(RegisterActivity.this, "Error! Please try again", Toast.LENGTH_SHORT).show();
            mAuth.getCurrentUser().delete();
            System.out.println("UserRegisterActivity.uploadDetails " + e.getCause().getCause());
        }
    }

    private void showAvl() {
        registerBt.setVisibility(View.GONE);
        avl.setVisibility(View.VISIBLE);
    }


    private void hideAvl() {
        registerBt.setVisibility(View.VISIBLE);
        avl.setVisibility(View.GONE);
    }

}
