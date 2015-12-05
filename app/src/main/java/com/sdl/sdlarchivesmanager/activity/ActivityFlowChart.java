package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdl.sdlarchivesmanager.R;

/**
 * Created by majingyuan on 15/12/3.
 * 步骤列表
 */
public class ActivityFlowChart extends AppCompatActivity implements View.OnClickListener{

    private ImageView ivBack;
    private LinearLayout llBack;
    private LinearLayout llAdd;
    private LinearLayout llStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowchart);
        ivBack = (ImageView) findViewById(R.id.iv_menu);
        ivBack.setImageResource(R.mipmap.button_back);

        llBack = (LinearLayout)findViewById(R.id.ll_menu);
        llAdd = (LinearLayout)findViewById(R.id.ll_add);
        llStart = (LinearLayout)findViewById(R.id.ll_start);
        llAdd.setVisibility(View.INVISIBLE);
        llBack.setOnClickListener(this);
        llStart.setOnClickListener(this);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_menu:
                ActivityFlowChart.this.finish();
                break;
            case  R.id.ll_start:
                Intent intent = new Intent();
                intent.setClass(ActivityFlowChart.this,ActivityStep1.class);
                startActivity(intent);
            default:
                break;
        }
    }
}
