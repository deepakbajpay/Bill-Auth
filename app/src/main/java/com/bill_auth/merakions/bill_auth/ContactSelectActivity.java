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
import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Hardik on 2/13/2018.
 */

public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    EditText searchEnterEt;
    ImageView searchClickIv;
    CustomListViewAdapterConatactSelector customListViewAdapter;

    ArrayList<ContactItem> contactlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperdocview);
        searchClickIv = findViewById(R.id.searchclick);
        searchEnterEt = findViewById(R.id.searchenter);
        searchClickIv.setOnClickListener(this);
        ArrayList<ContactItem> contactlist = new ArrayList<>();
        contactlist =new ArrayList<>();

        listView = findViewById(R.id.doclist);

        ContactItem List1 = new ContactItem("hardik", "fbd");
        ContactItem List2 = new ContactItem("hardik", "fbd");
        ContactItem List3 = new ContactItem("hardik", "fbd");
        ContactItem List4 = new ContactItem("hardik", "fbd");
        ContactItem List5 = new ContactItem("hardik", "fbd");
        ContactItem List1=new ContactItem("hardik","fbdsdfs");
        ContactItem List2=new ContactItem("hardik","fbsdfd");
        ContactItem List3=new ContactItem("hardik","fbsdfd");
        ContactItem List4=new ContactItem("hardik","fsdfbd");
        ContactItem List5=new ContactItem("hardik","fsdfbd");

        contactlist.add(List1);
        contactlist.add(List2);
        contactlist.add(List3);
        contactlist.add(List4);
        contactlist.add(List5);

        customListViewAdapter = new CustomListViewAdapterConatactSelector(this,contactlist);
        listView.setAdapter(customListViewAdapter);
        fetchShopkeeprs();

    }

    private void fetchShopkeeprs(){
        contactlist.clear();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.CHILD_SHOP_KEEPER);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    UserItem userItem = ds.getValue(UserItem.class);
                    ContactItem contactItem = new ContactItem();
                    contactItem.setContactname(userItem.getName());
                    contactItem.setuId(userItem.getuId());
                    contactItem.setGetContactaddress(userItem.getAddress());
                    contactlist.add(contactItem);
                }
                customListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchclick:

                break;
        }
    }

}