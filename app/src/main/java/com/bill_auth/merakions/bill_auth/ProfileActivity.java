package com.bill_auth.merakions.bill_auth;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Utilities;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class ProfileActivity extends AppCompatActivity {
    TextView nameEt,mobileEt,emailEt,address,gstEt;
    Button generate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameEt=findViewById(R.id.nameP);
        mobileEt=findViewById(R.id.MobilenumberP);
        emailEt=findViewById(R.id.emailidP);
        address=findViewById(R.id.address_tv);
        gstEt=findViewById(R.id.gstP);
        generate = findViewById(R.id.generate_key_btn);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generateKey();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        setUserValues();
    }

    private void generateKey() throws NoSuchProviderException, NoSuchAlgorithmException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                kpg.initialize(new KeyGenParameterSpec.Builder(
                        "sdf",
                        KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                        .setDigests(KeyProperties.DIGEST_SHA256,
                                KeyProperties.DIGEST_SHA512)
                        .build());
            }
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        KeyPair kp = kpg.generateKeyPair();

        System.out.println("ProfileActivity.generateKey private " +kp.getPrivate().getEncoded());// Base64.encodeToString(kp.getPrivate().getEncoded(), Base64.DEFAULT));
        System.out.println("ProfileActivity.generateKey public " + Base64.encodeToString(kp.getPublic().getEncoded(), Base64.DEFAULT));
    }

    private void setUserValues(){
        UserItem userItem = Utilities.getSavedUserDetails(this);
        nameEt.setText(userItem.getName());
        mobileEt.setText(userItem.getMobile());
        emailEt.setText(userItem.getEmail());
        address.setText(userItem.getAddress());
        gstEt.setText(userItem.getGst());


       /* DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        if (Constants.WHOLE_SALER.equals(Utilities.getUserType(this))){
            dbRef = dbRef.child(Constants.CHILD_WHOLE_SALER);
        }else
            dbRef = dbRef.child(Constants.CHILD_SHOP_KEEPER);
        dbRef = dbRef.child(Utilities.getUid(this));
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserItem userItem = dataSnapshot.getValue(UserItem.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
