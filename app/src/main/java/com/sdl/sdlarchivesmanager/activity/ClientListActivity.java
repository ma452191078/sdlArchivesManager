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
public class ClientListActivity extends AppCompatActivity implements View.OnClickListener {

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
    private String userNum = "";
    private String clientLevel = "";
    private JSONArray array;
    private int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            userNum = bundle.getString("usernum");
            clientLevel = bundle.getString("level");
        }

        userNum = "20140009";
        createWidget();
        setWidget();
        setClick();

        //获取省级列表
        if (userNum != null){
            getClientList(userNum, "");

        }

    }

    private void getClientList(String id, String client) {

        click ++;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("sales_id", id);
        params.put("client_id", client);

        String url = new sdlClient().getUrl(BASEURL);
        Request<JSONObject> provRequest = new NormalPostRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String strdata = null;
                        try {
                            strdata = response.getString("Data");
                            array = new JSONArray(strdata);
                            if (array.length() > 0){
                                for (int i = 0; i < array.length(); i++){
                                    Client client = new Client();
                                    String clientNum = array.getJSONObject(i).getString("seller_id");
                                    String clientName = array.getJSONObject(i).getString("seller_name");

                                    client.setClient_Name(clientName);
                                    client.setClient_Num(clientNum);
                                    client.setId((long)i);
                                    listItems.add(client);


                                }
                                adapter = new ClientListAdapter(ClientListActivity.this, listItems);
                                lvClient.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"暂无经销商", Toast.LENGTH_SHORT).show();
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

    private void createWidget() {
        tvTittle = (TextView) findViewById(R.id.tv_tittle);
        tvIconAddress = (TextView) findViewById(R.id.tv_iconaddr);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        lvClient = (ListView) findViewById(R.id.lv_address);
        tvClient = (TextView) findViewById(R.id.tv_address);
    }

    private void setWidget() {

        tvTittle.setText(R.string.title_activity_clientinfo);
        tvClient.setText("选择经销商");
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

                if ((clientLevel.equals("3") && click == 2) || (clientLevel.equals("2") && click == 1)){

                    Intent intent = new Intent();
                    intent.putExtra("result", strClientName);
                    intent.putExtra("client", strClient);
                    setResult(2001, intent);
                    ClientListActivity.this.finish();
                }else {
                    getClientList("", strClient);
                }
            }
        });
    }
}
