package com.sdl.sdlarchivesmanager.util;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by majingyuan on 16/1/2.
 * 文件存储路径
 */
public class FilePath {
    public FilePath(){

    }

    public File getPhotoName(){
        File tempFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/",
                getPhotoFileName());    //照片文件
        return tempFile;
    }

    //    照片名称,日期时间为名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
