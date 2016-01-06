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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.sdl.sdlarchivesmanager.Client;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.adapter.ClientListAdapter;
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
 * Created by majingyuan on 16/1/6.
 * 获取一级商列表
 */
public class ActivityClientList extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvTittle;
    private TextView tvIconAddress;
    private ListView lvClient;
    private Typeface iconfont;
    private TextView tvClient;
    private List<Client> listItems = new ArrayList<Client>();
    private ClientListAdapter adapter;

    private String strClient;
    private String strClientName = "";
    private static final String BASEURL="AppSyncAction!getSellerListBySalesId";
    private String userNum;
    private JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");

        createWidget();
        setWidget();
        setClick();

        //获取省级列表
        getAddrList(userNum);
    }

    private void getAddrList(String id) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("sales_id", id);

        String url = new sdlClient().getUrl(BASEURL);
        Request<JSONObject> provRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String strdata = null;
                        try {
                            strdata = response.getString("Data");
                            array = new JSONArray(strdata);

                            for (int i = 0; i < array.length(); i++){
                                Client client = new Client();
                                String clientNum = array.getJSONObject(i).getString("seller_id");
                                String clientName = array.getJSONObject(i).getString("seller_name");

                                client.setClient_Name(clientName);
                                client.setClient_Num(clientNum);
                                client.setId((long)i);
                                listItems.add(client);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new ClientListAdapter(ActivityClientList.this, listItems);
                        lvClient.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }, params);
        requestQueue.add(provRequest);
    }

    @Override
    public void onClick(View v) {

    }

    private void createWidget() {
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        tvIconAddress = (TextView) findViewById(R.id.tv_iconaddr);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        lvClient = (ListView) findViewById(R.id.lv_address);
        tvClient = (TextView) findViewById(R.id.tv_address);
    }

    private void setWidget() {

        tvTittle.setText(R.string.title_activity_clientinfo);
        tvIconAddress.setTypeface(iconfont);
    }

    private void setClick() {
        llBack.setOnClickListener(this);

        lvClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = ((Client) parent.getAdapter().getItem(position));
                strClient = client.getClient_Num();
                strClientName = client.getClient_Name();
                tvClient.setText(strClientName);
                Intent intent = new Intent();
                intent.putExtra("result", strClientName);
                intent.putExtra("client", strClient);
                setResult(1002, intent);
                ActivityClientList.this.finish();
            }
        });
    }
}
