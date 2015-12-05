package com.sdl.sdlarchivesmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.util.PhotoDialog;

/**
 * Created by majingyuan on 15/12/5.
 * 创建经销商第三步,上传营业执照
 */
public class ActivityStep3 extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    private ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_step3);
//        组件声明
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        ivPicture = (ImageView) findViewById(R.id.iv_picture);
//        添加监听
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
//        初次载入显示选择提示
        new PhotoDialog(ActivityStep3.this, ActivityStep3.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                break;
            case R.id.iv_picture:
                new PhotoDialog(ActivityStep3.this, ActivityStep3.this);
            default:
                break;
        }

    }
}
