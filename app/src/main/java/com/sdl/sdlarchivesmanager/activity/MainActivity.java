package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sdl.sdlarchivesmanager.Address;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.User;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.fragment.ClientFragment;
import com.sdl.sdlarchivesmanager.fragment.HomeFragment;
import com.sdl.sdlarchivesmanager.fragment.SettingFragment;
import com.sdl.sdlarchivesmanager.fragment.UploadFragment;
import com.sdl.sdlarchivesmanager.http.NormalPostRequest;
import com.sdl.sdlarchivesmanager.util.SysApplication;
import com.sdl.sdlarchivesmanager.util.sdlClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private long clickTime = 0; // 记录第一次点击返回键的时间
    private DBHelper dbManager;
    private String userNum, userName;
    private User user;
    private JSONArray array;

    private TextView tvUserName;
    private TextView tvUserRegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SysApplication.getInstance().addActivity(this);
//        是否存在已登录用户
        dbManager = DBHelper.getInstance(this);
        user = new User();
        user = dbManager.loadUserByStatus();

//        判断用户是否存在
        if (user != null) {

            Bundle bundle = this.getIntent().getExtras();
            if (bundle == null){
                userName = user.getUser_Name();
                userNum = user.getUser_Num();
            }else {
                userName = bundle.getString("username");
                userNum = bundle.getString("usernum");
            }

            getDbDate(user.getUser_Num());
            initView();
            setView();
            initLeftMenu();

//            加载数据
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_mainframe, new HomeFragment()).commit();
        } else {
//            不存在进行登录操作
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    private void initLeftMenu() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//            修改侧滑菜单中头部控件
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        tvUserName = (TextView) headerLayout.findViewById(R.id.tv_username);
        tvUserName.setText(userName);
        tvUserRegin = (TextView) headerLayout.findViewById(R.id.tv_userregin);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    }

    private void setView() {
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_mainframe, new HomeFragment()).commit();
                toolbar.setTitle("掌中档");
                break;
            case R.id.nav_send:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_mainframe, new UploadFragment()).commit();
                toolbar.setTitle(item.getTitle());
                break;
            case R.id.nav_client:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_mainframe, new ClientFragment()).commit();
                toolbar.setTitle(item.getTitle());
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_mainframe, new SettingFragment()).commit();
                toolbar.setTitle(item.getTitle());
                break;
            case R.id.nav_signout:
                SignOut();
                break;
            default:

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SignOut() {
        user.setUser_Status(false);
        if (dbManager.updateUser(user)){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 3000) {
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
                    Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            //关闭整个程序
            SysApplication.getInstance().exit();
        }
    }

    public void getDbDate(String userNum){
        refreshArchive(userNum);
        refreshClient(userNum);
    }

    /**
     * 获取申请单最新数据
     * @param userNum
     */
    private void refreshArchive(String userNum) {

        final String BASEURL = "";

        Map<String, String> params = new HashMap<String, String>();
        params.put("parent_id", userNum);
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
                                    addressList.add(address);
                                }
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
        provRequest.setTag("provinceList");
        ArchiveApplication.getHttpQueues().add(provRequest);
    }

    /**
     * 获取经销商最新数据
     * @param userNum
     */
    private void refreshClient(String userNum) {

    }


}
