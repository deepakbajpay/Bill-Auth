package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.bill_auth.merakions.bill_auth.utils.Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        fetchUserProfile();
    }

    private void fetchUserProfile(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
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
        });
    }
}
