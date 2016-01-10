package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.util.SysApplication;

/**
 * Created by majingyuan on 15/12/3.
 * 步骤列表
 */
public class FlowChartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private LinearLayout llBack;
    private LinearLayout llStart;
    private TextView tvTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowchart);
        SysApplication.getInstance().addActivity(this);

        llBack = (LinearLayout) findViewById(R.id.ll_back);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        llStart = (LinearLayout) findViewById(R.id.ll_start);

        tvTittle.setText(R.string.archives_flowchart);
        llBack.setOnClickListener(this);
        llStart.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                FlowChartActivity.this.finish();
                break;
            case R.id.ll_start:
                Intent intent = new Intent();
                intent.setClass(FlowChartActivity.this, BaseInfoActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
