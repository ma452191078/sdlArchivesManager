package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Address;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.adapter.AddressListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majingyuan on 15/12/5.
 * 创建经销商步骤1
 */
public class ActivityAddrList extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvTittle;
    private TextView tvIconAddress;
    private ListView lvAddress;
    private Typeface iconfont;
    private TextView tvAddress;
    private List<Address> listItems = new ArrayList<Address>();
    private AddressListAdapter adapter;

    private String strProvince;
    private String strCity;
    private String strCountry;
    private String strTown;
    private String[] sProvince = {"山东", "北京"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");

        createWidget();
        setWidget();
        setClick();

        Address address;
        for (int i = 0; i < sProvince.length; i++){
            address = new Address();
            address.setAddr_Name(sProvince[i]);
            listItems.add(address);
        }
        adapter = new AddressListAdapter(ActivityAddrList.this, listItems);
        lvAddress.setAdapter(adapter);

    }

    private void createWidget() {
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        tvIconAddress = (TextView) findViewById(R.id.tv_iconaddr);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        lvAddress = (ListView) findViewById(R.id.lv_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
    }

    private void setWidget() {

        tvTittle.setText(R.string.title_activity_address);
        tvIconAddress.setTypeface(iconfont);
    }

    private void setClick() {
        llBack.setOnClickListener(this);

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String adr = ((Address) parent.getAdapter().getItem(position)).getAddr_Name();
                tvAddress.setText(adr);
                Intent intent = new Intent();
                intent.putExtra("result", adr);
                intent.putExtra("province",strProvince);
                intent.putExtra("city",strCity);
                intent.putExtra("country",strCountry);
                intent.putExtra("town",strTown);
                setResult(1001, intent);
                ActivityAddrList.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;

            default:
                break;
        }
    }
}
