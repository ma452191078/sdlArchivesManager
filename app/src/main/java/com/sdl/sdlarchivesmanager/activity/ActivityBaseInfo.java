package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
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

    private LinearLayout llBack;
    private LinearLayout llNext;
    private LinearLayout llLevel;
    private LinearLayout llUpLevel;
    private TextView tvTittle;
    private RadioButton rbJxs, rbZzdh;  //经销商和种植大户单选按钮
    private RadioButton rbLevel1, rbLevel2, rbLevel3;  //经销商层级

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_baseinfo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SysApplication.getInstance().addActivity(this);

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
    }

    private void setWidget() {
        tvTittle.setText(R.string.archives_baseinfo);

    }

    private void setClick(){
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        rbZzdh.setOnClickListener(this);
        rbJxs.setOnClickListener(this);
        rbLevel1.setOnClickListener(this);
        rbLevel2.setOnClickListener(this);
        rbLevel3.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                Intent intent = new Intent();
                intent.setClass(ActivityBaseInfo.this, ActivityBankInfo.class);
                startActivity(intent);
                break;
            case  R.id.rb_jxs:
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
            default:
                break;
        }
    }
}
