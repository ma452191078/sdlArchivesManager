package com.sdl.sdlarchivesmanager.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdl.sdlarchivesmanager.R;

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

    /**
     * 压缩图片
     */
    public Bitmap createThumbnail(String filepath, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     * 点击图片放大查看
     */
    public void getBigPicture(Bitmap b, Activity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(activity).create();
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        if (b != null) {
            Display display = activity.getWindowManager()
                    .getDefaultDisplay();
            int scaleWidth = display.getWidth();
            int height = b.getHeight();// 图片的真实高度
            int width = b.getWidth();// 图片的真实宽度
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) img.getLayoutParams();
            lp.width = scaleWidth;// 调整宽度
            lp.height = (height * scaleWidth) / width;// 调整高度
            img.setLayoutParams(lp);
            img.setImageBitmap(b);
            dialog.setView(imgEntryView); // 自定义dialog
            dialog.show();
        }
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        });
    }
}
