package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.SysApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by majingyuan on 2015-12-05 20:31:29
 * 第二步,银行信息
 */
public class ActivityBankInfo extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_BANK = 2000;
    private Date timeFlag = null;
    DBHelper dbHelper;
    Application app = new Application();

    private LinearLayout llBack;
    private LinearLayout llNext;
    private TextView tvTittle;
    private EditText etBankNum;     //银行卡号
    private TextView tvBankName;    //开户行
    private EditText etBankOwner;   //户主
    private RadioButton rbInvoiceTypeZ;     //发票类型,专用发票0
    private RadioButton rbInvoiceTypeP;     //发票类型,普通发票1
    private EditText etInvoiceNum;  //对公账号
    private TextView tvInvoiceName; //开户行
    private EditText etInvoiceName2;    //支行
    private EditText etInvoiceOwner;    //户主
    private EditText etInvoicePhone;    //电话


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_bankinfo);
        SysApplication.getInstance().addActivity(this);

        dbHelper = DBHelper.getInstance(this);
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                timeFlag = sdf.parse(bundle.getString("timeflag"));
                app = dbHelper.loadApplication(timeFlag);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        createWidget();
        setWidget();

    }

//    声明屏幕组件
    protected void createWidget(){
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        etBankNum = (EditText) findViewById(R.id.et_bankname);
        tvBankName = (TextView) findViewById(R.id.tv_bankname);
        etBankOwner = (EditText) findViewById(R.id.et_bankowner);
        rbInvoiceTypeZ = (RadioButton) findViewById(R.id.rb_zyfp);
        rbInvoiceTypeP = (RadioButton) findViewById(R.id.rb_ptfp);
        etInvoiceNum = (EditText) findViewById(R.id.et_invoicebanknum);
        tvInvoiceName = (TextView) findViewById(R.id.tv_invoicebankname);
        etInvoiceName2 = (EditText) findViewById(R.id.et_invoicebankname2);
        etInvoiceOwner = (EditText) findViewById(R.id.et_invoicebankowner);
        etInvoicePhone = (EditText) findViewById(R.id.et_invoicebankphone);
    }

//    设置屏幕组件属性
    protected void setWidget(){
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        tvTittle.setText(R.string.archives_bankinfo);
        tvBankName.setOnClickListener(this);
        tvInvoiceName.setOnClickListener(this);

        if (app != null){
            etBankNum.setText(app.getApp_BankNum());
            tvBankName.setText(app.getApp_BankName());
            etBankOwner.setText(app.getApp_BankOwner());
            if (app.getApp_InvoiceType() == "0"){
//                专用发票
                rbInvoiceTypeZ.setChecked(true);

            }else
            {
                rbInvoiceTypeP.setChecked(true);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.ll_next:
                intent = null;
                intent.setClass(ActivityBankInfo.this, ActivityLicence.class);
                startActivity(intent);
                break;
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.tv_bankname:
                intent = null;
                intent.setClass(ActivityBankInfo.this, ActivityAddrList.class);
                startActivityForResult(intent, RESULT_BANK);
                break;
            case R.id.tv_invoicebankname:
                break;
            default:
                break;
        }
    }
}
