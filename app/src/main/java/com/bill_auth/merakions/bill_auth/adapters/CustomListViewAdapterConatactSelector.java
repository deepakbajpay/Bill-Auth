package com.bill_auth.merakions.bill_auth.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bill_auth.merakions.bill_auth.ContactSelectActivity;
import com.bill_auth.merakions.bill_auth.R;
import com.bill_auth.merakions.bill_auth.UploadActivity;
import com.bill_auth.merakions.bill_auth.beanclasses.ContactItem;
import com.bill_auth.merakions.bill_auth.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Hardik on 2/12/2018.
 */

public class CustomListViewAdapterConatactSelector extends BaseAdapter implements View.OnClickListener {

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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view= convertView;
        if (convertView==null)
            view=inflater.inflate(R.layout.list_row,null);
        RelativeLayout billItemRl = view.findViewById(R.id.bill_item_rl);
        TextView contact=view.findViewById(R.id.billnumber);
        TextView contactaddress=view.findViewById(R.id.billdate);
        ImageView cancel = view.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        ImageView verify = view.findViewById(R.id.verify);
        verify.setVisibility(View.GONE);
        ContactItem mcontact;
        mcontact=contacts.get(position);

        contactaddress.setText(mcontact.getGetContactaddress());
        contact.setText(mcontact.getContactname());
        
        billItemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.bill_item_rl:
                        Intent intent = new Intent(mContext, UploadActivity.class);
                        intent.putExtra(Constants.U_ID_TAG,contacts.get(position).getuId());
                        mContext.startActivity(intent);
                        ((ContactSelectActivity)mContext).finish();
                        break;

                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
