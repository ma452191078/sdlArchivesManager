package com.sdl.sdlarchivesmanager.http;

import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by majingyuan on 16/1/4.
 * 字符串转换格式为UTF-8
 */
public class HttpHeaderParserUtf extends HttpHeaderParser {

    public static String parseCharset(Map<String, String> headers) {

        return "UTF-8";
    }

}
