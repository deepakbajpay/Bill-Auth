package com.bill_auth.merakions.bill_auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.bill_auth.merakions.bill_auth.adapters.CustomListViewAdapter;
import com.bill_auth.merakions.bill_auth.beanclasses.BillItem;

import java.util.ArrayList;
import java.util.List;

public class Shopkeeperdocview extends AppCompatActivity {
    ListView listView;
    CustomListViewAdapter customListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeperdocview);
        ArrayList<BillItem> billList=new ArrayList<>();

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

        CustomListViewAdapter adapter = new CustomListViewAdapter(this,billList);
        listView.setAdapter(adapter);

    }
}
