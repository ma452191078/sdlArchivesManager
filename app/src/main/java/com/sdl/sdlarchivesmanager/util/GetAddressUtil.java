package com.sdl.sdlarchivesmanager.util;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sdl.sdlarchivesmanager.Address;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.http.NormalPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by majingyuan on 16/1/7.
 * 获取所有地址信息
 */
public class GetAddressUtil {

    private static final String BASEURL="AppSyncAction!getDivListByParentId";
    private JSONArray array;
    private DBHelper dbHelper;
    private Activity activity;
    private RequestQueue requestQueue;
    private int postion;
    private String strAddress = null;

    public GetAddressUtil(Activity act){
        activity = act;
        requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        getAddrList();
    }

    /**
     * 获取省市县列表
     */
    private void getAddrList(){
        dbHelper = DBHelper.getInstance(activity.getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();
        String url = new sdlClient().getUrl(BASEURL);
        Request<JSONObject> provRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        List<Address> addressList = new ArrayList<Address>();
                        String strdata = null;
                        try {
                            strdata = response.getString("Data");
                            array = new JSONArray(strdata);

                            if (array.length() > 0){
                                for (int i = 0; i < array.length(); i++){
                                    Address address = new Address();
                                    String code = array.getJSONObject(i).getString("code");
                                    String name = array.getJSONObject(i).getString("name");
                                    String parent = array.getJSONObject(i).getString("parent");
                                    String level = array.getJSONObject(i).getString("level");

                                    address.setAddr_Name(name);
                                    address.setAddr_Code(code);
                                    address.setId((long) i);
                                    address.setAddr_UpCode(parent);
                                    address.setAddr_Level(level);
                                    addressList.add(address);
                                }
                                dbHelper.insertAddressList(addressList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }, params);
        requestQueue.add(provRequest);
    }

    public String getAddressByCode(final String code, final String parentCode, final String type){

        Map<String, String> params = new HashMap<String, String>();
        params.put("parent_id", parentCode);
        params.put("type_code", type);
        String url = new sdlClient().getUrl(BASEURL);
        Request<JSONObject> provRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        List<Address> addressList = new ArrayList<Address>();
                        String strdata = null;
                        try {
                            strdata = response.getString("Data");
                            array = new JSONArray(strdata);
                            for (int i = 0; i < array.length(); i++){
                                Address address = new Address();
                                String prov_code = array.getJSONObject(i).getString("code");
                                String prov_name = array.getJSONObject(i).getString("name");

                                if (prov_code == code){
                                    strAddress = prov_name;
                                    continue;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("TAG", error.getMessage(), error);
            }
        }, params);
        requestQueue.add(provRequest);

        return strAddress;
    }
}
