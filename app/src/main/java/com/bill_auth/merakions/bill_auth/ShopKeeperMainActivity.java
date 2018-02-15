package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bill_auth.merakions.bill_auth.utils.Utilities;

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
        profileLl.setOnClickListener(this);
        wholeSellerLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newbills:
                Intent intent = new Intent(this,Shopkeeperdocview.class);
                startActivity(intent);

                break;
            case R.id.billhistory:
                Intent intent1 = new Intent(this,Shopkeeperdocview.class);
                startActivity(intent1);

                break;

            case  R.id.myprofile:
                Intent intent2 =new Intent(this,ProfileActivity.class);
                startActivity(intent2);
                break;
            case  R.id.wholesalors:
                Intent intent3=new Intent(this,ContactSelectActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Utilities.logout(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
