package com.sdl.sdlarchivesmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.util.SysApplication;

/**
 * Created by majingyuan on 15/12/26.
 * 申请单信息
 */
public class ActivityArchiveInfo extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvTittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientinfo);
        SysApplication.getInstance().addActivity(this);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        tvTittle.setText(R.string.title_activity_archiveinfo);

        llBack.setOnClickListener(this);
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
