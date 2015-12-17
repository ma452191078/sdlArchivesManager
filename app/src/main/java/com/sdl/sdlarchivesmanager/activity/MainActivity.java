package com.sdl.sdlarchivesmanager.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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





/**
 * Create by majingyuan on 2015-11-28 16:47:10
 * 主窗口,侧滑菜单实现
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout llMenu;  //侧滑菜单呼出
    private LinearLayout llAdd;   //添加经销商

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
    public static final int      MORE_DATA_MAX_COUNT = 3;
    public int                   moreDataCount       = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        SlidingMenu menu = new SlidingMenu(this);
//        menu.setMode(SlidingMenu.LEFT);
//        // 设置触摸屏幕的模式
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.shadow);
//
//        // 设置滑动菜单视图的宽度
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        // 设置渐入渐出效果的值
//        menu.setFadeDegree(0.35f);
//        /**
//         * SLIDING_WINDOW will include the Title/ActionBar in the content
//         * section of the SlidingMenu, while SLIDING_CONTENT does not.
//         */
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        //为侧滑菜单设置布局
//        menu.setMenu(R.layout.leftmenu);


        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAdd = (LinearLayout) findViewById(R.id.ll_add);
        listView = (ListView) findViewById(R.id.lv_itemlist);

        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ActivityFlowChart.class);
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
                intent.setClass(MainActivity.this, ActivityFlowChart.class);
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
        adapter = new MainListAdapter(MainActivity.this, listItems);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    protected void updateData() {

        Toast.makeText(MainActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
        ptrFrame.refreshComplete();
    }

}
