package com.sdl.sdlarchivesmanager.bean;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by majingyuan on 16/1/10.
 */
public class BitmapCatchBean implements ImageLoader.ImageCache {

    //使用LruCache 实现图片缓存 ：
    //使用地址：
    //http://blog.csdn.net/guolin_blog/article/details/9316683

    private LruCache<String,Bitmap> cache;
    //设置最大的 尺寸值

    public BitmapCatchBean() {
        //构造方法 实现 LruCache 缓存 图片

        int maxSize=10*1024*1024;   //设置图片缓存为10M
        cache=new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };

    }

    @Override
    public Bitmap getBitmap(String url) {
        // 得到
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        // 设置
        cache.put(url, bitmap);
    }
}
