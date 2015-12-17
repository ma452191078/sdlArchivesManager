package com.sdl.sdlarchivesmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.adapter.MainListAdapter;
import com.sdl.sdlarchivesmanager.bean.BeanAudit;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout llMenu;  //侧滑菜单呼出
    private LinearLayout llAdd;   //添加经销商
    private FloatingActionButton fabAdd;
    private List<BeanAudit> listItems = new ArrayList<BeanAudit>();
    private PtrClassicFrameLayout ptrFrame  = null;
    private ListView listView;
    private MainListAdapter adapter;
    private String[][] mStrings = {  {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店1", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店2", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店3", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店4", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店5", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店6", "张某", "山东省临沂市临沭县石门镇刘晓村"}} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.lv_itemlist);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, ActivityFlowChart.class);
                startActivity(intent);
            }
        });

//        下拉刷新控件
        ptrFrame = (PtrClassicFrameLayout)findViewById(R.id.list_view);
        ptrFrame.disableWhenHorizontalMove(true);
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                // here check $mListView instead of $content
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }
        });

//        列表项目
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, ActivityFlowChart.class);
                startActivity(intent);
            }
        });
        BeanAudit audit ;

        for (int i = 0; i < mStrings.length; i++){
            audit = new BeanAudit();
            audit.setStatus(mStrings[i][0]);
            audit.setClientName(mStrings[i][1]);
            audit.setClientAddress(mStrings[i][3]);
            audit.setClientOwner(mStrings[i][2]);
            listItems.add(audit);
        }
        adapter = new MainListAdapter(Main2Activity.this, listItems);
        listView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void updateData() {

        Toast.makeText(Main2Activity.this, "刷新成功", Toast.LENGTH_SHORT).show();
        ptrFrame.refreshComplete();
    }
}
