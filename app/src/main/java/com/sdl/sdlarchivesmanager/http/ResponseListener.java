package com.sdl.sdlarchivesmanager.http;

import com.android.volley.Response;

/**
 * Created by majingyuan on 16/1/8.
 */
public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {

}