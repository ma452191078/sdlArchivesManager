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
import com.sdl.sdlarchivesmanager.adapter.ClientListAdapter;
import com.sdl.sdlarchivesmanager.bean.BeanAudit;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by majingyuan on 15/12/20.
 * 经销商列表
 */
public class FragmentUpload extends Fragment {

    public FragmentUpload(){

    }

    private View mainview;
    private String[][] mStrings = {  {"农资店", "张一","0","1", "山东省临沂市临沭县石门镇刘晓村"},
            {"农资店1", "张21","0","2", "山东省临沂市临沭县石门镇刘晓村"},
            {"农资店2", "张3","0","3", "山东省临沂市临沭县石门镇刘晓村"},
            {"农资店3", "张4","1","", "山东省临沂市临沭县石门镇刘晓村"}} ;

    private FloatingActionButton fabSearch;
    private List<BeanAudit> listItems = new ArrayList<BeanAudit>();
    private PtrClassicFrameLayout ptrFrame;
    private ListView listView;
    private ClientListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.activity_client_list, null);

        fabSearch = (FloatingActionButton) mainview.findViewById(R.id.fab_search);
        listView = (ListView) mainview.findViewById(R.id.lv_itemlist);

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mainview.getContext(),"搜索",Toast.LENGTH_SHORT).show();
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
            audit.setClientName(mStrings[i][0]);
            audit.setClientOwner(mStrings[i][1]);
            audit.setClientType(mStrings[i][2]);
            audit.setClientLevel(mStrings[i][3]);
            audit.setClientAddress(mStrings[i][4]);
            listItems.add(audit);
        }
        adapter = new ClientListAdapter(getActivity(), listItems);
        listView.setAdapter(adapter);

        return mainview;
    }

    protected void updateData(Context context) {

        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
        ptrFrame.refreshComplete();
    }
}
