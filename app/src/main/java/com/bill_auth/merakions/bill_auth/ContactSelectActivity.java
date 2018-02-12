package com.bill_auth.merakions.bill_auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bill_auth.merakions.bill_auth.adapters.CustomListViewAdapterConatactSelector;
import com.bill_auth.merakions.bill_auth.beanclasses.ContactItem;

import java.util.ArrayList;

/**
 * Created by Hardik on 2/13/2018.
 */

public class ContactSelectActivity extends AppCompatActivity {
    ListView listView;
    CustomListViewAdapterConatactSelector customListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperdocview);
        ArrayList<ContactItem> contactlist=new ArrayList<>();

        listView= findViewById(R.id.doclist);

        ContactItem List1=new ContactItem("hardik","fbd");
        ContactItem List2=new ContactItem("hardik","fbd");
        ContactItem List3=new ContactItem("hardik","fbd");
        ContactItem List4=new ContactItem("hardik","fbd");
        ContactItem List5=new ContactItem("hardik","fbd");

        contactlist.add(List1);
        contactlist.add(List2);
        contactlist.add(List3);
        contactlist.add(List4);
        contactlist.add(List5);

        CustomListViewAdapterConatactSelector adapter = new CustomListViewAdapterConatactSelector(this,contactlist);
        listView.setAdapter(adapter);

    }

}
