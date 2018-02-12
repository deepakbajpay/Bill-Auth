package com.bill_auth.merakions.bill_auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bill_auth.merakions.bill_auth.adapters.CustomListViewAdapterConatactSelector;
import com.bill_auth.merakions.bill_auth.beanclasses.ContactItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Hardik on 2/13/2018.
 */

public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    EditText searchEnterEt;
    ImageView searchClickIv;
    CustomListViewAdapterConatactSelector customListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperdocview);
        searchClickIv = findViewById(R.id.searchclick);
        searchEnterEt = findViewById(R.id.searchenter);
        searchClickIv.setOnClickListener(this);
        ArrayList<ContactItem> contactlist = new ArrayList<>();

        listView = findViewById(R.id.doclist);

        ContactItem List1 = new ContactItem("hardik", "fbd");
        ContactItem List2 = new ContactItem("hardik", "fbd");
        ContactItem List3 = new ContactItem("hardik", "fbd");
        ContactItem List4 = new ContactItem("hardik", "fbd");
        ContactItem List5 = new ContactItem("hardik", "fbd");

        contactlist.add(List1);
        contactlist.add(List2);
        contactlist.add(List3);
        contactlist.add(List4);
        contactlist.add(List5);

        CustomListViewAdapterConatactSelector adapter = new CustomListViewAdapterConatactSelector(this, contactlist);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchclick:

                break;
        }
    }
}