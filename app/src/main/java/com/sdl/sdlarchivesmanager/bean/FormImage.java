package com.sdl.sdlarchivesmanager.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by majingyuan on 16/1/8.
 * 上传图片类
 */
public class FormImage {
    //参数的名称
    private String mName ;

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public void setmMime(String mMime) {
        this.mMime = mMime;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    //文件名
    private String mFileName ;
    //文件的 mime，需要根据文档查询
    private String mMime ;

    private Bitmap mBitmap ;

    public FormImage(String filePath) {
        try {
//            接收传过来的路径值,转换为bitmap
            FileInputStream fis = new FileInputStream(filePath);
            this.mBitmap  = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return mName;
    }

    public String getFileName() {
        return mFileName;
    }
    //对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        mBitmap.compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }

    public String getMime() {
        return "image/jpeg";
    }
}