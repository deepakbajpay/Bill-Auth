package com.bill_auth.merakions.bill_auth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bill_auth.merakions.bill_auth.R;
import com.bill_auth.merakions.bill_auth.beanclasses.BillItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hardik on 2/11/2018.
 */

public class CustomListViewAdapter extends BaseAdapter {
     public Context mContext;
     public  ArrayList<BillItem> bills ;

     public static LayoutInflater inflater=null;

     public CustomListViewAdapter(Context context, ArrayList<BillItem> data){
         mContext =context;
         bills=data;
         inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     }
    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
         View view= convertView;
         if (convertView==null)
             view=inflater.inflate(R.layout.list_row,null);
             TextView billnumber=view.findViewById(R.id.billnumber);
             TextView billdate=view.findViewById(R.id.billdate);
             ImageView verify=view.findViewById(R.id.verify);
             BillItem mbills;
             mbills=bills.get(position);

             billdate.setText(mbills.getBilldate());
             billnumber.setText(mbills.getBillnumber());
             if (mbills.getVerify())
                 verify.setVisibility(View.VISIBLE);
             else
                 verify.setVisibility(View.GONE);
         return view;
    }
}
