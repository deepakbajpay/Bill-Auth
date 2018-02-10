package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout newBillLl,billHistoryLl,wholeSellerLl,profileLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newBillLl =  findViewById(R.id.newbills);
        billHistoryLl= findViewById(R.id.billhistory);
        wholeSellerLl= findViewById(R.id.wholesalors);
        profileLl= findViewById(R.id.myprofile);


    }

}
