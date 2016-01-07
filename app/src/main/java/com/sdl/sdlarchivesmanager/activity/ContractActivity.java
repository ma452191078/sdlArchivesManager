package com.sdl.sdlarchivesmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
 * 经销商协议书
 */
public class ContractActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    private ImageView ivPicture;
    private TextView tvTittle;

    private CharSequence[] items;   //提示框文本,数组形式,可以自定义
    private File tempFile = new FilePath().getPhotoName();
    private String imgUri;
    private long id;
    private DBHelper dbHelper;
    private Application app;
    private PhotoUtil photoUtil;
    private String fileContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contract);
        SysApplication.getInstance().addActivity(this);
        dbHelper = DBHelper.getInstance(this);
        photoUtil = new PhotoUtil(ContractActivity.this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
            app = dbHelper.loadApplicationByID(id);
        }

//        组件声明
        createWidget();
//        初次载入显示选择提示
        setWidget();
        getPhoto();
    }

    //        组件声明
    protected void createWidget() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        ivPicture = (ImageView) findViewById(R.id.iv_picture);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
    }

    //        添加监听
    protected void setWidget() {
        tvTittle.setText(R.string.archives_contract);
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        ivPicture.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                saveApp();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                bundle.putString("source", "create");
                intent.setClass(ContractActivity.this, ConfirmActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.iv_picture:
                getPhoto();
            default:
                break;
        }

    }

    protected void saveApp(){
        if (imgUri != null){
            app.setApp_Contract(imgUri);
            dbHelper.updateApplication(app);
        }
    }

    private void getPhoto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ContractActivity.this);
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
                    // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                    if (data != null) {

                        setPhoto(data.getData());
                    } else {
                        Toast.makeText(ContractActivity.this, "请重新选择图片", Toast.LENGTH_SHORT).show();
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
        ivPicture.setImageBitmap(photoUtil.createThumbnail(fileContract, 10));
        imgUri = uri.toString();
    }
}
