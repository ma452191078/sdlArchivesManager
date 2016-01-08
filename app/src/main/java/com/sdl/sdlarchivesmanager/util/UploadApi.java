package com.sdl.sdlarchivesmanager.util;

import com.android.volley.Request;
import com.sdl.sdlarchivesmanager.bean.FormImage;
import com.sdl.sdlarchivesmanager.http.PostUploadRequest;
import com.sdl.sdlarchivesmanager.http.ResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016-01-08 10:17:33
 */
public class UploadApi {

    private static String BASEURL = "";

    /**
     * 上传图片接口
     *
     * @param filePath 需要上传的图片
     * @param listener 请求回调
     */
    public static void uploadImg(List<String> filePath, ResponseListener listener) {
        List<FormImage> imageList = new ArrayList<FormImage>();
        for (int i = 0; i < filePath.size(); i++) {
            imageList.add(new FormImage(filePath.get(i)));
        }

        Request request = new PostUploadRequest(new sdlClient().getUrl(BASEURL), imageList, listener);
        VolleyUtil.getRequestQueue().add(request);
    }
}
