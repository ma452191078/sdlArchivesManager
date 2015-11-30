package com.sdl.sdlarchivesmanager.activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.adapter.MainListAdapter;
import com.sdl.sdlarchivesmanager.bean.BeanAudit;
import com.sdl.sdlarchivesmanager.view.SlidingMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;


/**
 * Create by majingyuan on 2015-11-28 16:47:10
 * 主窗口,侧滑菜单实现
 */
public class MainActivity extends AppCompatActivity {

    private SlidingMenu mLeftMenu;  //侧滑菜单
    private LinearLayout llMenu;  //侧滑菜单呼出
    private LinearLayout llAdd;   //添加经销商

    private List<BeanAudit> listItems = new ArrayList<BeanAudit>();
    private DropDownListView     listView  = null;
    private MainListAdapter adapter;
    private String[][] mStrings = {  {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"}} ;
    public static final int      MORE_DATA_MAX_COUNT = 3;
    public int                   moreDataCount       = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);   //侧滑菜单
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llAdd = (LinearLayout) findViewById(R.id.ll_add);

        llMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenu.toggle();

            }
        });
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView = (DropDownListView)findViewById(R.id.list_view);
        // set drop down listener
        listView.setOnDropDownListener(new OnDropDownListener() {

            @Override
            public void onDropDown() {
                new GetDataTask(true).execute();
            }
        });

        // set on bottom listener
        listView.setOnBottomListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new GetDataTask(false).execute();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(getApplicationContext(), "点击项目");
            }
        });
         listView.setShowFooterWhenNoMore(true);

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

    private class GetDataTask extends AsyncTask<Void, Void, String[][]> {

        private boolean isDropDown;

        public GetDataTask(boolean isDropDown) {
            this.isDropDown = isDropDown;
        }

        @Override
        protected String[][] doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[][] result) {

            if (isDropDown) {
                adapter.notifyDataSetChanged();

                // should call onDropDownComplete function of DropDownListView at end of drop down complete.
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                listView.onDropDownComplete("上次刷新" + dateFormat.format(new Date()));
            } else {
                moreDataCount++;
                adapter.notifyDataSetChanged();

                if (moreDataCount >= MORE_DATA_MAX_COUNT) {
                    listView.setHasMore(false);
                }

                // should call onBottomComplete function of DropDownListView at end of on bottom complete.
                listView.onBottomComplete();
            }

            super.onPostExecute(result);
        }
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


}
