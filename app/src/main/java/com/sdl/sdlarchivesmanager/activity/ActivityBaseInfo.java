package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.User;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.GetDateUtil;
import com.sdl.sdlarchivesmanager.util.SysApplication;

/**
 * Created by majingyuan on 15/12/5.
 * 创建经销商步骤1
 */
public class ActivityBaseInfo extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RESULT_ADDRESS = 1000;
    private static final int RESULT_CLIENT = 2000;
    private LinearLayout llBack;
    private LinearLayout llNext;
    private LinearLayout llLevel;
    private LinearLayout llUpLevel;
    private TextView tvTittle;
    private TextView tvUpLevel; //上级经销商
    private EditText etClientName;  //经销商名称
    private EditText etClientOwner; //经销商法人
    private EditText etClientPhone; //电话
    private TextView tvAddress1;    //地区
    private EditText etAddress2;    //详细地址
    private RadioButton rbJxs, rbZzdh;  //经销商和种植大户单选按钮
    private RadioButton rbLevel1, rbLevel2, rbLevel3;  //经销商层级

    private String strProvince, strCity, strCountry, strTown;
    private DBHelper dbHelper;
    private String timeFlag;
    private String strUpLevel;  //上级经销商
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_baseinfo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SysApplication.getInstance().addActivity(this);
        dbHelper = DBHelper.getInstance(this);
        user = dbHelper.loadUserByStatus();
        createWidget();
        setWidget();
        setClick();
    }

    /*控件声明*/
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
        tvUpLevel = (TextView) findViewById(R.id.tv_uplevel);
        etClientName = (EditText) findViewById(R.id.et_clientname);
        etClientOwner = (EditText) findViewById(R.id.et_clientowner); //经销商法人
        etClientPhone = (EditText) findViewById(R.id.et_clientphone); //电话
        tvAddress1 = (TextView) findViewById(R.id.tv_clientaddr);    //地区
        etAddress2 = (EditText) findViewById(R.id.et_clientaddr2);    //详细地址
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
        tvUpLevel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                saveApp();
                bundle.putString("timeflag", timeFlag.toString());
                intent.setClass(ActivityBaseInfo.this, ActivityBankInfo.class);
                intent.putExtras(bundle);
                startActivity(intent);
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
                intent.setClass(ActivityBaseInfo.this, ActivityAddrList.class);
                startActivityForResult(intent, RESULT_ADDRESS);
                break;
            case R.id.tv_uplevel:
                if (rbLevel3.isChecked()) {
                    bundle.putString("level", "3");
                } else {
                    bundle.putString("level", "2");
                }
                bundle.putString("usernum", user.getUser_Num());
                intent.setClass(ActivityBaseInfo.this, ActivityClientList.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, RESULT_CLIENT, bundle);
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_ADDRESS && resultCode == 1001) {
            tvAddress1.setText(data.getStringExtra("result"));
            strProvince = data.getStringExtra("province");
            strCity = data.getStringExtra("city");
            strCountry = data.getStringExtra("country");
            strTown = data.getStringExtra("town");

        } else if (requestCode == RESULT_CLIENT && resultCode == 2001) {
            tvUpLevel.setText(data.getStringExtra("result"));
            strUpLevel = data.getStringExtra("client");
        }
    }

    protected void saveApp() {
//        获得界面数据
        timeFlag = new GetDateUtil().getNowDateTime();
        Application app = new Application();
//        经销商类型,经销商0/种植大户1
        if (rbJxs.isSelected()) {
            app.setApp_Type("0");
        } else if (rbZzdh.isSelected()) {
            app.setApp_Type("1");
        } else {
            app.setApp_Type("");
        }

//        经销商层级,一级1/二级2/三级3
        if (rbLevel1.isSelected()) {
            app.setApp_Level("1");
        } else if (rbLevel2.isSelected()) {
            app.setApp_Level("2");
        } else if (rbLevel3.isSelected()) {
            app.setApp_Level("3");
        } else {
            app.setApp_Level("");
        }

//        上级经销商
        if (rbLevel2.isSelected() || rbLevel3.isSelected()) {
            app.setApp_Uplevel(strUpLevel);
        } else {
            app.setApp_Uplevel("");
        }

        app.setApp_Name(etClientName.getText().toString().trim());
        app.setApp_Owner(etClientOwner.getText().toString().trim());
        app.setApp_Phone(etClientPhone.getText().toString().trim());
        app.setApp_Province(strProvince);
        app.setApp_City(strCity);
        app.setApp_Country(strCountry);
        app.setApp_Town(strTown);
        app.setApp_Address(etAddress2.getText().toString().trim());
        app.setApp_TimeFlag(new GetDateUtil().getDate(timeFlag));
        app.setApp_Send("2");   //资料尚未建立完整,不允许上传

//        保存申请单
        dbHelper.addApplication(app);
    }
}
