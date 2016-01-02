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

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.util.GetDateUtil;
import com.sdl.sdlarchivesmanager.util.SysApplication;

import java.io.File;

/**
 * Created by majingyuan on 15/12/5.
 * 创建经销商第三步,上传营业执照
 */
public class ActivityLicence extends AppCompatActivity implements View.OnClickListener {

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
    private String imgUri;
    private String timeFlag;
    private DBHelper dbHelper;
    private Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_licence);
        SysApplication.getInstance().addActivity(this);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            timeFlag = bundle.getString("timeflag");
            app = dbHelper.loadApplication(new GetDateUtil().getDate(timeFlag));
        }

        createWidget();
        setWidget();
//        初次载入显示选择提示
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

        tvTittle.setText(R.string.archives_licence);
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
                bundle.putString("timeflag", timeFlag);
                intent.setClass(ActivityLicence.this, ActivityIDCard.class);
                startActivity(intent, bundle);
                break;
            case R.id.iv_picture:
                getPhoto();
            default:
                break;
        }

    }

    protected void saveApp() {
        if (imgUri != null) {
            app.setApp_Licence(imgUri);
            dbHelper.updateApplication(app);
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
        String path = new GetDateUtil().getNowDateTime();
        return path + ".jpg";
    }

    //    裁剪图片
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 4);
//        intent.putExtra("aspectY", 3);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void getPhoto() {


        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLicence.this);
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
                        Toast.makeText(ActivityLicence.this, "请重新选择图片", Toast.LENGTH_SHORT).show();
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
//        try{
//            Bitmap bmp = MediaStore.Images.Media.getBitmap(ActivityLicence.this.getContentResolver(),
//                    uri);
//            ivPicture.setImageBitmap(bmp);
//        }catch (IOException e){
//            Toast.makeText(ActivityLicence.this,e.toString(),Toast.LENGTH_LONG).show();
//        }

        ivPicture.setImageURI(uri);
        imgUri = uri.toString();
    }
}
