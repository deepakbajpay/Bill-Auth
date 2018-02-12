package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendbillWs:
                Intent intent = new Intent(this,ContactSelectActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
