package com.sdl.sdlarchivesmanager.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.sdl.sdlarchivesmanager.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by majingyuan on 15/12/20.
 * 更新类
 */
public class UpdateManager {

        private Context mContext;
        private static	int newVerCode = -1;
        private static	String newVerName = "archive.apk";

        //提示语
        private String updateMsg = "检测到新版本,请下载并更新后使用";

        //返回的安装包url
        //private String apkUrl = "http://192.168.9.23:8000/SdlEBS/UploadImages/apk/sdlfx.apk";
        private String apkUrl = "http://eb.shidanli.cn/UploadImages/apk/archive.apk";


        private Dialog noticeDialog;

        private Dialog downloadDialog;
        /* 下载包安装路径 */
        private static final String savePath = "/sdcard/download/";

        private static final String saveFileName = savePath + "archive.apk";

        /* 进度条与通知ui刷新的handler和msg常量 */
        private ProgressBar mProgress;


        private static final int DOWN_UPDATE = 1;

        private static final int DOWN_OVER = 2;
        private static final int NoSd = 3;
        private static final int DOWN_OVERsd = 4;
        private int progress;

        private Thread downLoadThread;

        private boolean interceptFlag = false;
        private Context c;
        private Handler mHandler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWN_UPDATE:
                        mProgress.setProgress(progress);
                        break;
                    case DOWN_OVER:
                        installApk();
                        break;
                    case DOWN_OVERsd:
                        installApkatsd();
                        break;
                    case NoSd:

                        downloadApk1();
                        break;
                    default:
                        break;
                }
            };
        };

        public UpdateManager(Context c) {
            this.mContext = c;
            if(c!=null)
            {
                this.c = c;
            }
            else
            {
                Log.w("error", "上下文为空");
            }
        }

        //外部接口让主Activity调用
        public void checkUpdateInfo()
        {
            showNoticeDialog();
        }
        //获得本地客户端版本号
        public int getVerCode(Context context) {
            int verCode = -1;
            try {
                verCode = context.getPackageManager().getPackageInfo(
                        "com.sdl.sdlarchivesmanager", 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                String TAG = "error";
                Log.e(TAG , e.getMessage());

            }
            return verCode;
        }

        public static String getVerName(Context context) {
            String verName = "";
            String TAG1 = "error";
            try {
                verName = context.getPackageManager().getPackageInfo(
                        "com.sdl.sdlarchivesmanager", 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG1, e.getMessage());
            }
            return verName;
        }

        private void showNoticeDialog(){
            AlertDialog.Builder builder = new Builder(mContext);
            builder.setTitle("软件版本更新");
            builder.setMessage(updateMsg);
            builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showDownloadDialog();
                }
            });
            builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            noticeDialog = builder.create();
            noticeDialog.show();
        }
        private void NoshowNoticeDialog(){
            AlertDialog.Builder builder = new Builder(mContext);
            builder.setTitle("软件版本更新");

            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.progress, null);
            mProgress = (ProgressBar)v.findViewById(R.id.progress);

            builder.setView(v);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    interceptFlag = true;
                }
            });
        }
        private void shownosd(){
            AlertDialog.Builder builder = new Builder(mContext);
            builder.setTitle("软件版本更新");

            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.progress, null);
            mProgress = (ProgressBar)v.findViewById(R.id.progress);

            builder.setView(v);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
        }

        private void showDownloadDialog(){
            AlertDialog.Builder builder = new Builder(mContext);
            builder.setTitle("软件版本更新");

            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.progress, null);
            mProgress = (ProgressBar)v.findViewById(R.id.progress);

            builder.setView(v);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    interceptFlag = true;
                }
            });
            downloadDialog = builder.create();
            downloadDialog.show();

            downloadApk();

        }

        private Runnable mdownApkRunnable = new Runnable() {
            @Override
            public void run() {
                try {


                    if (Environment.getExternalStorageState()
                            .equals(Environment.MEDIA_MOUNTED))
                    {
                        //获取SD卡的目录
                        File sdCardDir = Environment.getExternalStorageDirectory();


                    }
                    else
                    {
                        mHandler.sendEmptyMessage(NoSd);

                    }
                    //PTTJDownLoadUtil.downFiletoDecive(apkUrl,saveFileName);
                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if(!file.exists())
                    {
                        file.mkdir();
                    }
                    String apkFile = saveFileName;
                    File ApkFile = new File(apkFile);
                    FileOutputStream fos = new FileOutputStream(ApkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];

                    do{
                        int numread = is.read(buf);
                        count += numread;
                        progress =(int)(((float)count / length) * 100);
                        //更新进度

                        if(numread <= 0){
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWN_OVERsd);
                            break;
                        }
                        else
                        {
                            mHandler.sendEmptyMessage(DOWN_UPDATE);
                        }
                        fos.write(buf,0,numread);
                    }
                    while(!interceptFlag);//点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }

            }
        };


        private Runnable mdownApkRunnable1 = new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = null;
                    fos =c.openFileOutput("archive.apk", Context.MODE_WORLD_READABLE);
                    int count = 0;
                    byte buf[] = new byte[1024];

                    do{
                        int numread = is.read(buf);
                        count += numread;
                        progress =(int)(((float)count / length) * 100);
                        //更新进度

                        if(numread <= 0)
                        {
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                            break;
                        }
                        else
                        {
                            mHandler.sendEmptyMessage(DOWN_UPDATE);
                        }
                        fos.write(buf,0,numread);
                    }while(!interceptFlag);//点击取消就停止下载.

                    fos.close();
                    is.close();
                    fos.flush();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }

            }
        };

        /**
         * 下载apk到SD卡
         *
         */

        private void downloadApk(){
            downLoadThread = new Thread(mdownApkRunnable);
            downLoadThread.start();
        }
        //下载到机身内存
        private void downloadApk1(){
            downLoadThread = new Thread(mdownApkRunnable1);
            downLoadThread.start();
        }

        /**
         * 安装apk
         *
         */
        private void installApk(){
            //File apkfile = new File(saveFileName);

            String loca ="/data/data/" + mContext.getPackageName()+"/files/archive.apk";

            File apkfile = new File(loca);
            String command = "chmod 777 " + apkfile.getAbsolutePath();
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(command);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	      /*  if (!apkfile.exists()) {
	            return;
	        }*/
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");

            mContext.startActivity(i);

        }
        private void installApkatsd(){
            File apkfile = new File(saveFileName);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            // i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            mContext.startActivity(i);

        }

    }
