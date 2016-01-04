package com.sdl.sdlarchivesmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.activity.ActivityConfirm;
import com.sdl.sdlarchivesmanager.activity.ActivityFlowChart;
import com.sdl.sdlarchivesmanager.adapter.MainListAdapter;
import com.sdl.sdlarchivesmanager.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by majingyuan on 15/12/20.
 * 首页
 */
public class FragmentHome extends Fragment {


    public FragmentHome() {

    }

    private View mainview;
    private FloatingActionButton fabAdd;
    private List<Application> listItems = new ArrayList<Application>();
    private PtrClassicFrameLayout ptrFrame;
    private ListView listView;
    private MainListAdapter adapter;
    private DBHelper dbHelper;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.content_main, null);
        dbHelper = DBHelper.getInstance(getContext());

        fabAdd = (FloatingActionButton) mainview.findViewById(R.id.fab);
        listView = (ListView) mainview.findViewById(R.id.lv_itemlist);

        //添加按钮
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(mainview.getContext(), ActivityFlowChart.class);
                startActivity(intent);
            }
        });

//        下拉刷新控件
        setPtrFrame();

//        列表项目
        setListView();
        setListViewSource();

        return mainview;
    }

    /**
    * 设置下拉刷新组件
    * */
    private void setPtrFrame(){
        ptrFrame = (PtrClassicFrameLayout) mainview.findViewById(R.id.list_view);
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
                updateListViewSource(mainview.getContext());
            }
        });
    }

    /**
     * 设置申请单列表
     * */
    private void setListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Application application = (Application) parent.getAdapter().getItem(position);
                bundle.putLong("id", application.getId());
                bundle.putString("source", "home");
                intent.setClass(mainview.getContext(), ActivityConfirm.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void setListViewSource() {

        listItems = dbHelper.loadApplicationBySend("");
        adapter = new MainListAdapter(getActivity(), listItems);
        listView.setAdapter(adapter);
    }

    protected void updateListViewSource(Context context) {

        listItems = dbHelper.loadApplicationBySend("");
        adapter = new MainListAdapter(getActivity(), listItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ptrFrame.refreshComplete();
        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
    }

}
