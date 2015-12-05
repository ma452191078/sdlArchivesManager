package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sdl.sdlarchivesmanager.R;

/**
 * create by majingyuan on 2015-12-05 20:31:29
 * 第二步,银行信息
 */
public class ActivityStep2 extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout llBack;
    private LinearLayout llNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_step2);
        llBack = (LinearLayout)findViewById(R.id.ll_back);
        llNext = (LinearLayout)findViewById(R.id.ll_next);

        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_next:
                Intent intent = new Intent();
                intent.setClass(ActivityStep2.this,ActivityStep3.class);
                startActivity(intent);
                break;
            case R.id.ll_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
