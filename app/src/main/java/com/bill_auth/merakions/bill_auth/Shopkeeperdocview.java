package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.bill_auth.merakions.bill_auth.adapters.CustomListViewAdapter;
import com.bill_auth.merakions.bill_auth.beanclasses.BillItem;
import com.bill_auth.merakions.bill_auth.beanclasses.UserItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.bill_auth.merakions.bill_auth.utils.Utilities;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Shopkeeperdocview extends AppCompatActivity implements CustomListViewAdapter.onCustomListViweAdapterItemClick {
    ListView listView;
    CustomListViewAdapter customListViewAdapter;
    private ArrayList<BillItem> billList;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperdocview);
        billList=new ArrayList<>();
        uid = Utilities.getUid(this);

        listView= findViewById(R.id.doclist);

        BillItem List1=new BillItem("01","11 Feb 2018",true);
        BillItem List2=new BillItem("01","23 Jun 2017",false);
        BillItem List3=new BillItem("01","23 Dec 2017",true);
        BillItem List4=new BillItem("01","1 Jan 2018",false);
        BillItem List5=new BillItem("01","15 Jan 2018",true);

        billList.add(List1);
        billList.add(List2);
        billList.add(List3);
        billList.add(List4);
        billList.add(List5);

        customListViewAdapter = new CustomListViewAdapter(this,billList,true,this);
        listView.setAdapter(customListViewAdapter);
        fetchBills();

    }

    @Override
    public void onAdapterItemClicked(int position) {
        verifyBill(position);
    }

    private void verifyBill(int position) {

    }

    private void fetchBills(){
        billList.clear();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child(Constants.CHILD_BILLS);
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                System.out.println("Shopkeeperdocview.onChildAdded datasnapshot " +dataSnapshot);
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    System.out.println("Shopkeeperdocview.onChildAdded ds " + ds);
                    if (ds.getKey().equals(uid)){
                        for (DataSnapshot ds1: ds.getChildren()){
                            System.out.println("Shopkeeperdocview.onChildAdded " + ds1);
                            BillItem billItem = ds1.getValue(BillItem.class);
                            billList.add(billItem);
                        }
                    }
                }

                customListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
