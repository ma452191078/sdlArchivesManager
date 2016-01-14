/**
 * sdlClient.java
 * 2015上午8:38:49
 * TODO
 *
 * @作者 曹欣欣
 */
package com.sdl.sdlarchivesmanager.util;

/**
 *异步获取网络数据的基本方法
 */

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class sdlClient {


//    private static final String BASE_URL = "http://eb.shidanli.cn/";//基本的url
    private static final String BASE_URL = "http://192.168.9.23:8000/SdlEBS/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String gurl = BASE_URL + relativeUrl;
        return gurl;
    }

    public String getUrl(String relativeUrl) {
        String gurl = BASE_URL + relativeUrl;
        return gurl;
    }
}


