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

import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.bill_auth.merakions.bill_auth.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wang.avi.AVLoadingIndicatorView;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    EditText emailEt, passwordEt;
    Button loginBt;
    TextView newUserTv;
    FirebaseAuth mAuth;
    AVLoadingIndicatorView avl;
    private String uid;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userType = getIntent().getStringExtra(Constants.USER_TYPE_TAG);
        System.out.println("ActivityLogin.onCreate " + userType);

        if (!TextUtils.isEmpty(Utilities.getUid(this))) {
            if (Constants.WHOLE_SALER.equals(userType)) {
                Intent intent = new Intent(ActivityLogin.this, WholeMainactivity.class);
                intent.putExtra(Constants.USER_TYPE_TAG, userType);
                startActivity(intent);

            } else {
                Intent intent = new Intent(ActivityLogin.this, ShopKeeperMainActivity.class);
                intent.putExtra(Constants.USER_TYPE_TAG, userType);
                startActivity(intent);
            }
        }

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
                intent.putExtra(Constants.USER_TYPE_TAG, userType);
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

    public void login(String email, String password) {
        showAvi();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideAvi();
                if (task.isSuccessful()) {
                    uid = mAuth.getCurrentUser().getUid();
                    FirebaseMessaging.getInstance().subscribeToTopic(uid);
                    openActivity(uid);
                   /* if (userType == Constants.WHOLE_SALER) {
                        Intent intent = new Intent(ActivityLogin.this, WholeMainactivity.class);

                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(ActivityLogin.this, ShopKeeperMainActivity.class);
                        intent.putExtra(Constants.USER_TYPE_TAG, userType);
                        startActivity(intent);
                    }*/

                } else {
                    hideAvi();
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityLogin.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAvi() {
        loginBt.setVisibility(View.GONE);
        avl.smoothToShow();
    }


    private void hideAvi() {
        loginBt.setVisibility(View.VISIBLE);
        avl.smoothToHide();
    }

    private void openActivity(final String uid) {
        if (Constants.WHOLE_SALER.equals(userType)) {
            DatabaseReference ngoDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.CHILD_WHOLE_SALER);
            ngoDatabaseReference.child(uid)/*.equalTo(uid)*/.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        Utilities.showAlert(ActivityLogin.this, "User not found", "Ngo details not found, make sure you have selected correct user type", "Ok", "Cancel");
                        hideAvi();
                        return;
                    }

                    UserItem user = dataSnapshot.getValue(UserItem.class);
                    if (user == null) {
                        user = new UserItem();
                    }

                    user.setuId(uid);
                    Utilities.saveUserDetails(ActivityLogin.this, user);
                    Utilities.setUserType(ActivityLogin.this,Constants.WHOLE_SALER);
                    hideAvi();
                    Intent intent = new Intent(ActivityLogin.this, WholeMainactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constants.USER_TYPE_TAG, userType);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideAvi();
                    Toast.makeText(ActivityLogin.this, "Failed to connect server", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (Constants.SHOP_KEEPER.equals(userType)) {
            DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.CHILD_SHOP_KEEPER);
            userDatabaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        Utilities.showAlert(ActivityLogin.this, "User not found", "User details not found, make sure you have selected correct user type", "Ok", "Cancel");
                        hideAvi();
                        return;
                    }

                    UserItem user = dataSnapshot.getValue(UserItem.class);
                    if (user == null)
                        user = new UserItem();
                    user.setuId(uid);
                    Utilities.saveUserDetails(ActivityLogin.this, user);
                    Utilities.setUserType(ActivityLogin.this,Constants.SHOP_KEEPER);

                    hideAvi();
                    Intent intent = new Intent(ActivityLogin.this, ShopKeeperMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constants.USER_TYPE_TAG, userType);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideAvi();
                    Toast.makeText(ActivityLogin.this, "Failed to connect server", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

}
