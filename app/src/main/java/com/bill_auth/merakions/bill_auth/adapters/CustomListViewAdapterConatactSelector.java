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
import com.bill_auth.merakions.bill_auth.beanclasses.ContactItem;

import java.util.ArrayList;

/**
 * Created by Hardik on 2/12/2018.
 */

public class CustomListViewAdapterConatactSelector extends BaseAdapter{

    public Context mContext;
    public ArrayList<ContactItem> contacts ;

    public static LayoutInflater inflater=null;

    public CustomListViewAdapterConatactSelector(Context context, ArrayList<ContactItem> data){
        mContext =context;
        contacts=data;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return contacts.size();
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
        TextView contact=view.findViewById(R.id.billnumber);
        TextView contactaddress=view.findViewById(R.id.billdate);
        ContactItem mcontact;
        mcontact=contacts.get(position);

        contactaddress.setText(mcontact.getGetContactaddress());
        contact.setText(mcontact.getContactname());
        return view;
    }
}
