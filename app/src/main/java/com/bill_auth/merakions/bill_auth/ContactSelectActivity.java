package com.bill_auth.merakions.bill_auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bill_auth.merakions.bill_auth.adapters.CustomListViewAdapterConatactSelector;
import com.bill_auth.merakions.bill_auth.beanclasses.ContactItem;
import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Hardik on 2/13/2018.
 */

public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    EditText searchEnterEt;
    ImageView searchClickIv;
    CustomListViewAdapterConatactSelector customListViewAdapter;
    AVLoadingIndicatorView avl;
    ArrayList<ContactItem> contactlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperdocview);
        searchClickIv = findViewById(R.id.searchclick);
        searchEnterEt = findViewById(R.id.searchenter);
        searchClickIv.setOnClickListener(this);
        avl = findViewById(R.id.avi_login);
        contactlist = new ArrayList<>();

        listView = findViewById(R.id.doclist);

        ContactItem List1 = new ContactItem("hardik", "fbdsdfs");
        ContactItem List2 = new ContactItem("hardik", "fbsdfd");
        ContactItem List3 = new ContactItem("hardik", "fbsdfd");
        ContactItem List4 = new ContactItem("hardik", "fsdfbd");
        ContactItem List5 = new ContactItem("hardik", "fsdfbd");

        contactlist.add(List1);
        contactlist.add(List2);
        contactlist.add(List3);
        contactlist.add(List4);
        contactlist.add(List5);

        customListViewAdapter = new CustomListViewAdapterConatactSelector(this, contactlist);
        listView.setAdapter(customListViewAdapter);
        fetchShopkeeprs();

    }

    private void showAvi() {
        searchClickIv.setVisibility(View.GONE);
        avl.smoothToShow();
    }


    private void hideAvi() {
        searchClickIv.setVisibility(View.VISIBLE);
        avl.smoothToHide();
    }


    private void fetchShopkeeprs() {
        contactlist.clear();
        showAvi();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.CHILD_SHOP_KEEPER);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserItem userItem = ds.getValue(UserItem.class);
                    ContactItem contactItem = new ContactItem();
                    contactItem.setContactname(userItem.getName());
                    contactItem.setuId(userItem.getuId());
                    contactItem.setGetContactaddress(userItem.getAddress());
                    contactlist.add(contactItem);
                }
                customListViewAdapter.notifyDataSetChanged();
                hideAvi();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideAvi();

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchclick:
                String keyword = searchEnterEt.getText().toString().trim();
                searchShopKeeper(keyword);
                break;
        }
    }

    private void searchShopKeeper(String keyword) {
        Toast.makeText(this, "Searching for" + keyword, Toast.LENGTH_SHORT).show();
        contactlist.clear();
        showAvi();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.CHILD_SHOP_KEEPER);
        Query query = dbRef.orderByChild("name").equalTo(keyword);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showAvi();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserItem userItem = ds.getValue(UserItem.class);
                    ContactItem contactItem = new ContactItem();
                    contactItem.setContactname(userItem.getName());
                    contactItem.setuId(userItem.getuId());
                    contactItem.setGetContactaddress(userItem.getAddress());
                    contactlist.add(contactItem);
                }
                customListViewAdapter.notifyDataSetChanged();
                hideAvi();
                Toast.makeText(ContactSelectActivity.this, "Searching Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideAvi();
            }
        });
    }
}