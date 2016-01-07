package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sdl.sdlarchivesmanager.Address;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.adapter.AddressListAdapter;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.http.NormalPostRequest;
import com.sdl.sdlarchivesmanager.util.sdlClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by majingyuan on 15/12/5.
 * 获取地区信息,省市县
 */
public class AddrListActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvTittle;
    private TextView tvIconAddress;
    private ListView lvAddress;
    private Typeface iconfont;
    private TextView tvAddress;
    private List<Address> listItems = new ArrayList<Address>();
    private AddressListAdapter adapter;

    private String strProvince;
    private String strProvinceName = "";
    private String strCity;
    private String strCityName = "";
    private String strCountry;
    private String strCountryName = "";
    private String strTown;
    private String strTownName = "";
    private static final String BASEURL="AppSyncAction!getDivListByParentId";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String COUNTRY = "area";
    private static final String TOWN = "town";

    private String strClick = null;
    private JSONArray array;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        dbHelper = DBHelper.getInstance(getApplicationContext());

        createWidget();
        setWidget();
        setClick();

        //获取省级列表
        getAddrList("0", PROVINCE);
    }

    private void createWidget() {
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        tvIconAddress = (TextView) findViewById(R.id.tv_iconaddr);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        lvAddress = (ListView) findViewById(R.id.lv_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
    }

    private void setWidget() {

        tvTittle.setText(R.string.title_activity_address);
        tvIconAddress.setTypeface(iconfont);
    }

    private void setClick() {
        llBack.setOnClickListener(this);

        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address adr = ((Address) parent.getAdapter().getItem(position));

                if (strClick.equals(PROVINCE)) {
                    strProvince = adr.getAddr_Code();
                    strProvinceName = adr.getAddr_Name();
                    tvAddress.setText(strProvinceName + strCityName + strCountryName + strTownName);
                    getAddrList(adr.getAddr_Code(), CITY);
                } else if (strClick.equals(CITY)) {
                    strCity = adr.getAddr_Code();
                    strCityName = adr.getAddr_Name();
                    tvAddress.setText(strProvinceName + strCityName + strCountryName + strTownName);
                    getAddrList(adr.getAddr_Code(), COUNTRY);
                } else if (strClick.equals(COUNTRY)) {
                    strCountry = adr.getAddr_Code();
                    strCountryName = adr.getAddr_Name();
                    tvAddress.setText(strProvinceName + strCityName + strCountryName + strTownName);
                    getAddrList(adr.getAddr_Code(), TOWN);

                } else if (strClick.equals(TOWN)) {
                    strTownName = adr.getAddr_Name();
                    strTown = adr.getAddr_Code();
                    tvAddress.setText(strProvinceName + strCityName + strCountryName + strTownName);
                    Intent intent = new Intent();
                    intent.putExtra("result", tvAddress.getText());
                    intent.putExtra("province", strProvince);
                    intent.putExtra("city", strCity);
                    intent.putExtra("country", strCountry);
                    intent.putExtra("town", strTown);
                    setResult(1001, intent);
                    AddrListActivity.this.finish();
                }


            }
        });
    }

    /**
     * 获取省市县列表
     */
    private void getAddrList( final String id, final String type){

        strClick = type;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("parent_id", id);
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

                            if (array.length() > 0){
                                for (int i = 0; i < array.length(); i++){
                                    Address address = new Address();
                                    String prov_code = array.getJSONObject(i).getString("code");
                                    String prov_name = array.getJSONObject(i).getString("name");

                                    address.setAddr_Name(prov_name);
                                    address.setAddr_Code(prov_code);
                                    address.setId((long) i);
                                    address.setAddr_UpCode(id);
                                    address.setAddr_Level(type);
                                    addressList.add(address);
                                }

                            saveAddressList(addressList);
                            adapter = new AddressListAdapter(AddrListActivity.this, addressList);
                            lvAddress.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(getApplicationContext(),"未查询到地址信息",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"网络连接错误",Toast.LENGTH_SHORT).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }, params);
        requestQueue.add(provRequest);
    }

    private void saveAddressList(List<Address> addressList) {
        dbHelper.insertAddressList(addressList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                this.finish();
                break;

            default:
                break;
        }
    }
}
