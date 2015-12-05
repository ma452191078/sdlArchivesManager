package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sdl.sdlarchivesmanager.R;

/**
 * Created by majingyuan on 15/12/5.
 * 创建经销商步骤1
 */
public class ActivityStep1 extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_step1);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);

        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                this.finish();
                break;
            case  R.id.ll_next:
                Intent intent = new Intent();
                intent.setClass(ActivityStep1.this,ActivityStep2.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
