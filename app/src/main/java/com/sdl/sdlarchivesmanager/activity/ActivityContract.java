package com.sdl.sdlarchivesmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdl.sdlarchivesmanager.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by majingyuan on 15/12/6.
 * 经销商协议书
 */
public class ActivityContract extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private LinearLayout llNext;
    private ImageView ivPicture;
    private TextView tvTittle;

    private CharSequence[] items;   //提示框文本,数组形式,可以自定义
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/",
            getPhotoFileName());    //照片文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contract);
//        组件声明
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llNext = (LinearLayout) findViewById(R.id.ll_next);
        ivPicture = (ImageView) findViewById(R.id.iv_picture);
        tvTittle = (TextView) findViewById(R.id.tv_tittle);

        tvTittle.setText(R.string.archives_contract);
//        添加监听
        llBack.setOnClickListener(this);
        llNext.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
//        初次载入显示选择提示
        getPhoto();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.ll_next:
                Intent intent = new Intent();
                intent.setClass(ActivityContract.this, ActivityIDCard.class);
                startActivity(intent);
                break;
            case R.id.iv_picture:
                getPhoto();
            default:
                break;
        }

    }

    //    启动拍照并返回照片
    public void getCameraPhoto() {

        Intent cameraintent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
//      指定调用相机拍照后照片的储存路径
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(tempFile));
        startActivityForResult(cameraintent,
                PHOTO_REQUEST_CAMERA);
    }

    //    启动图库获取照片
    public void getGalleryPhoto() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
    }

    //    照片名称,日期时间为名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    private void getPhoto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityContract.this);
        items = new CharSequence[]{"拍照上传", "从相册选择"};

        builder.setTitle("请选择图片来源");
        builder.setCancelable(true);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        getCameraPhoto();
                        break;
                    case 1:
                        getGalleryPhoto();
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
                case PHOTO_REQUEST_CAMERA:// 当选择拍照时调用
                    setPhoto(Uri.fromFile(tempFile));
                    break;
                case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                    // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                    if (data != null) {

                        setPhoto(data.getData());
                    } else {
                        Toast.makeText(ActivityContract.this, "请重新选择图片", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_REQUEST_CUT:// 返回的结果
                    if (data != null)
                        setPhoto(data.getData());
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPhoto(Uri uri) {
        ivPicture.setImageURI(uri);
    }
}
