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
 * 信息确认
 */
public class ActivityConfirm extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    private TextView tvNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        SysApplication.getInstance().addActivity(this);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        tvNext = (TextView) findViewById(R.id.tv_next);

        tvNext.setText("提交");
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
//                Intent intent = new Intent();
//                intent.setClass(ActivityConfirm.this, ActivityBankInfo.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
