package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.bill_auth.merakions.bill_auth.utils.Utilities;

public class WholeMainactivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout sendBillLl,billHistoryLl,shopkeeperLl,profileLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_main);
        sendBillLl =  findViewById(R.id.sendbillWs);
        billHistoryLl= findViewById(R.id.billhistoryWs);
        shopkeeperLl= findViewById(R.id.shopkeeperWs);
        profileLl= findViewById(R.id.myprofileWs);

        sendBillLl.setOnClickListener(this);
        billHistoryLl.setOnClickListener(this);
        profileLl.setOnClickListener(this);
        shopkeeperLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendbillWs:
                Intent intent = new Intent(this,ContactSelectActivity.class);
                startActivity(intent);

                break;
            case R.id.billhistoryWs:
                Intent intent1 = new Intent(this,BillHistoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.myprofileWs:
                Intent intent2 = new Intent(this,ProfileActivity.class);
                startActivity(intent2);
                break;
            case R.id.shopkeeperWs:
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
