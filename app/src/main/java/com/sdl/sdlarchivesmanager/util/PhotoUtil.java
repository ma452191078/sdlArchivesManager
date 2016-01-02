package com.sdl.sdlarchivesmanager.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by majingyuan on 16/1/2.
 * 获取照片类
 */
public class PhotoUtil {

    public static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 3;// 结果
    public File tempFile = new FilePath().getPhotoName();
    public Activity photoActivity;

    public PhotoUtil(Activity activity){
        photoActivity = activity;
    }

    //    启动拍照并返回照片
    public void getCameraPhoto() {

        Intent cameraintent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
//      指定调用相机拍照后照片的储存路径
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(tempFile));
        photoActivity.startActivityForResult(cameraintent,
                PHOTO_REQUEST_CAMERA);
    }

    //    启动图库获取照片
    public void getGalleryPhoto() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        photoActivity.startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
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
        photoActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

}
