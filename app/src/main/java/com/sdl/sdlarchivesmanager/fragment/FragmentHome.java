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

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.activity.ActivityFlowChart;
import com.sdl.sdlarchivesmanager.adapter.MainListAdapter;
import com.sdl.sdlarchivesmanager.bean.BeanAudit;

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



    public FragmentHome(){

    }

    private  View mainview;
    private FloatingActionButton fabAdd;
    private List<BeanAudit> listItems = new ArrayList<BeanAudit>();
    private PtrClassicFrameLayout ptrFrame;
    private ListView listView;
    private MainListAdapter adapter;
    private String[][] mStrings = {  {"营销中心审核", "农资店", "张某", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店1", "张某1", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店2", "张某2", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店3", "张某3", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店4", "张某4", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店5", "张某5", "山东省临沂市临沭县石门镇刘晓村"},
            {"营销中心审核", "农资店6", "张某6", "山东省临沂市临沭县石门镇刘晓村"}} ;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.content_main, null);


        fabAdd = (FloatingActionButton) mainview.findViewById(R.id.fab);
        listView = (ListView) mainview.findViewById(R.id.lv_itemlist);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(mainview.getContext(), ActivityFlowChart.class);
                startActivity(intent);
            }
        });

//        下拉刷新控件
        ptrFrame = (PtrClassicFrameLayout)mainview.findViewById(R.id.list_view);
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
                updateData(mainview.getContext());
            }
        });

//        列表项目
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.setClass(mainview.getContext(), ActivityFlowChart.class);
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
        adapter = new MainListAdapter(getActivity(), listItems);
        listView.setAdapter(adapter);

        return mainview;
    }

    protected void updateData(Context context) {

        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
        ptrFrame.refreshComplete();
    }


}
