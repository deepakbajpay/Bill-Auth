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
    onCustomListViweAdapterItemClick callback;
     public static LayoutInflater inflater=null;

     public CustomListViewAdapter(Context context, ArrayList<BillItem> data,boolean showTick,onCustomListViweAdapterItemClick callback){
         mContext =context;
         bills=data;
         this.callback = callback;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
         View view= convertView;
         if (convertView==null)
             view=inflater.inflate(R.layout.list_row,null);
             TextView billnumber=view.findViewById(R.id.billnumber);
             TextView billdate=view.findViewById(R.id.billdate);
             ImageView verify=view.findViewById(R.id.verify);
             final BillItem mbills;
             mbills=bills.get(position);

             billdate.setText(mbills.getTimestamp()+"");
             billnumber.setText(mbills.getBillnumber());
             if (mbills.getVerify())
                 verify.setImageResource(R.drawable.ic_verified);
             else
                 verify.setImageResource(R.drawable.ic_not_verified);

             verify.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (!mbills.getVerify()){
                         callback.onAdapterItemClicked(position);
                     }
                 }
             });
         return view;
    }

    public interface onCustomListViweAdapterItemClick{
         public void onAdapterItemClicked(int position);
    }
}
