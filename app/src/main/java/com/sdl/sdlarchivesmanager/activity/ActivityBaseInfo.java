package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.util.SysApplication;

/**
 * Created by majingyuan on 15/12/5.
 * 创建经销商步骤1
 */
public class ActivityBaseInfo extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RESULT_ADDRESS = 1000;
    private LinearLayout llBack;
    private LinearLayout llNext;
    private LinearLayout llLevel;
    private LinearLayout llUpLevel;
    private TextView tvTittle;
    private TextView tvLocation;
    private RadioButton rbJxs, rbZzdh;  //经销商和种植大户单选按钮
    private RadioButton rbLevel1, rbLevel2, rbLevel3;  //经销商层级
    private TextView tvAddress1;    //地区
    private EditText etAddress2;    //详细地址
    private Typeface iconfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_baseinfo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SysApplication.getInstance().addActivity(this);
        iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");

        createWidget();
        setWidget();
        setClick();
    }

    private void createWidget() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        llLevel = (LinearLayout) findViewById(R.id.ll_clientlevel);
        llUpLevel = (LinearLayout) findViewById(R.id.ll_uplevel);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        rbJxs = (RadioButton) findViewById(R.id.rb_jxs);
        rbZzdh = (RadioButton) findViewById(R.id.rb_zzdh);
        rbLevel1 = (RadioButton) findViewById(R.id.rb_level1);
        rbLevel2 = (RadioButton) findViewById(R.id.rb_level2);
        rbLevel3 = (RadioButton) findViewById(R.id.rb_level3);
        tvAddress1 = (TextView) findViewById(R.id.tv_clientaddr);
        etAddress2 = (EditText) findViewById(R.id.et_clientaddr2);
    }

    private void setWidget() {
        tvTittle.setText(R.string.archives_baseinfo);
    }

    private void setClick() {
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        rbZzdh.setOnClickListener(this);
        rbJxs.setOnClickListener(this);
        rbLevel1.setOnClickListener(this);
        rbLevel2.setOnClickListener(this);
        rbLevel3.setOnClickListener(this);
        tvAddress1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                intent.setClass(ActivityBaseInfo.this, ActivityBankInfo.class);
                startActivity(intent);
                break;
            case R.id.rb_jxs:
                if (llLevel.getVisibility() == View.GONE)
                    llLevel.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_zzdh:
                if (llLevel.getVisibility() == View.VISIBLE)
                    llLevel.setVisibility(View.GONE);
                break;
            case R.id.rb_level1:
                if (llUpLevel.getVisibility() == View.VISIBLE)
                    llUpLevel.setVisibility(View.GONE);
                break;
            case R.id.rb_level2:
                if (llUpLevel.getVisibility() == View.GONE)
                    llUpLevel.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_level3:
                if (llUpLevel.getVisibility() == View.GONE)
                    llUpLevel.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_clientaddr:
                intent.setClass(ActivityBaseInfo.this,ActivityAddrList.class);
                startActivityForResult(intent, RESULT_ADDRESS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ADDRESS && resultCode == 1001) {
            tvAddress1.setText(data.getStringExtra("result"));
        }
    }
}
