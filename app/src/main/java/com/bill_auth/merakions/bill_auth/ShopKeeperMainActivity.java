package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ShopKeeperMainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout newBillLl,billHistoryLl,wholeSellerLl,profileLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newBillLl =  findViewById(R.id.newbills);
        billHistoryLl= findViewById(R.id.billhistory);
        wholeSellerLl= findViewById(R.id.wholesalors);
        profileLl= findViewById(R.id.myprofile);

        newBillLl.setOnClickListener(this);
        billHistoryLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newbills:
                Intent intent = new Intent(this,UploadActivity.class);
                startActivity(intent);

                break;
            case R.id.billhistory:
                Intent intent1 = new Intent(this,Shopkeeperdocview.class);
                startActivity(intent1);

                break;
        }
    }
}