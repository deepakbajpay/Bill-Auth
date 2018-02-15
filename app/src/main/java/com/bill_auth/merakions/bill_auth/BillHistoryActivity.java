package com.bill_auth.merakions.bill_auth;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bill_auth.merakions.bill_auth.adapters.CustomListViewAdapter;
import com.bill_auth.merakions.bill_auth.beanclasses.BillItem;
import com.bill_auth.merakions.bill_auth.services.RetrofitApiService;
import com.bill_auth.merakions.bill_auth.services.RetrofitServiceUtil;
import com.bill_auth.merakions.bill_auth.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillHistoryActivity extends AppCompatActivity implements CustomListViewAdapter.onCustomListViweAdapterItemClick {
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

        CustomListViewAdapter adapter = new CustomListViewAdapter(this,billList,true,this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onAdapterItemClicked(int position) {

    }




}
