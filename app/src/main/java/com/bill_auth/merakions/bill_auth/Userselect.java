package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bill_auth.merakions.bill_auth.utils.Constants;

public class Userselect extends AppCompatActivity implements View.OnClickListener {
    LinearLayout shopKeeperLl,wholeSellerLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userselect);
        shopKeeperLl = findViewById(R.id.shopkeeper);
        wholeSellerLl =findViewById(R.id.wholesalor);

        wholeSellerLl.setOnClickListener(this);
        shopKeeperLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wholesalor:
                Intent intent=new Intent(this,Login.class);
                intent.putExtra(Constants.USER_TYPE_TAG,Constants.WHOLE_SALER);
                startActivity(intent);
                break;

            case R.id.shopkeeper:
                Intent intent1=new Intent(this,Login.class);
                intent1.putExtra(Constants.USER_TYPE_TAG,Constants.SHOP_KEEPER);
                startActivity(intent1);
                break;
        }
    }
}
