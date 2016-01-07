package com.sdl.sdlarchivesmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.FilePath;
import com.sdl.sdlarchivesmanager.util.PhotoUtil;
import com.sdl.sdlarchivesmanager.util.SysApplication;
import com.sdl.sdlarchivesmanager.util.UriUtil;

import java.io.File;

/**
 * Created by majingyuan on 15/12/6.
 * 创建经销商第四步,身份证信息
 */
public class IDCardActivity extends AppCompatActivity implements View.OnClickListener {

    private int imageItem;  //0正面,1反面
    private LinearLayout llBack;
    private LinearLayout llNext;
    private TextView tvTittle;
    private ImageView ivCardF, ivCardB;


    private CharSequence[] items;   //提示框文本,数组形式,可以自定义
    private File tempFile = new FilePath().getPhotoName();

    private long id;
    private DBHelper dbHelper;
    private Application app;
    private String imgUriF;
    private String imgUriB;
    private PhotoUtil photoUtil;
    private String fileContract;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_idcard);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        SysApplication.getInstance().addActivity(this);
        dbHelper = DBHelper.getInstance(this);
        photoUtil = new PhotoUtil(IDCardActivity.this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            id = bundle.getLong("id");
            app = dbHelper.loadApplicationByID(id);
        }
        createWidget();
        setWidget();
    }

//    初始化控件
    protected void createWidget(){
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        ivCardF = (ImageView) findViewById(R.id.iv_idcardf);
        ivCardB = (ImageView) findViewById(R.id.iv_idcardb);
    }

//    设置属性
    protected void setWidget(){
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        ivCardF.setOnClickListener(this);
        ivCardB.setOnClickListener(this);
        tvTittle.setText(R.string.archives_idcard);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                saveApp();
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                intent.setClass(IDCardActivity.this, ContractActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.iv_idcardf:
                imageItem = 0;
                getPhoto();
                break;
            case R.id.iv_idcardb:
                imageItem = 1;
                getPhoto();
                break;
            default:
                break;
        }
    }

    private void saveApp() {

        app.setApp_IdCardF(imgUriF);
        app.setApp_IdCardB(imgUriB);
        dbHelper.updateApplication(app);
    }

    private void getPhoto() {


        AlertDialog.Builder builder = new AlertDialog.Builder(IDCardActivity.this);
        items = new CharSequence[]{"拍照上传", "从相册选择"};

        builder.setTitle("请选择图片来源");
        builder.setCancelable(true);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        photoUtil.getCameraPhoto();
                        break;
                    case 1:
                        photoUtil.getGalleryPhoto();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.PHOTO_REQUEST_CAMERA:// 当选择拍照时调用
                    setPhoto(Uri.fromFile(tempFile));
                    break;
                case PhotoUtil.PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                    // 做非空判断
                    if (data != null) {

                        setPhoto(data.getData());
                    } else {
                        Toast.makeText(IDCardActivity.this, "请重新选择图片", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PhotoUtil.PHOTO_REQUEST_CUT:// 返回的结果
                    if (data != null)
                        setPhoto(data.getData());
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPhoto(Uri uri) {

        fileContract = new UriUtil().UriToFile(getApplicationContext(),uri);
        if (imageItem == 0) {
            ivCardF.setImageBitmap(photoUtil.createThumbnail(fileContract, 10));
            imgUriF = uri.toString();
        } else if (imageItem == 1) {
            ivCardB.setImageBitmap(photoUtil.createThumbnail(fileContract, 10));
            imgUriB = uri.toString();
        }

    }
}
