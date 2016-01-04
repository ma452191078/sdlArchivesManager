package com.sdl.sdlarchivesmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.activity.ActivityConfirm;
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
 * 经销商列表
 */
public class FragmentUpload extends Fragment {

    public FragmentUpload(){

    }

    private View mainview;
    private FloatingActionButton fabSearch;
    private List<Application> listItems = new ArrayList<Application>();
    private PtrClassicFrameLayout ptrFrame;
    private ListView listView;
    private MainListAdapter adapter;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.activity_client_list, null);
        dbHelper = DBHelper.getInstance(getContext());

        fabSearch = (FloatingActionButton) mainview.findViewById(R.id.fab_search);
        listView = (ListView) mainview.findViewById(R.id.lv_itemlist);

        fabSearch.setVisibility(View.GONE);

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
                updateListViewSource();
            }
        });

//        列表项目
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Application application = (Application) parent.getAdapter().getItem(position);
                bundle.putLong("id", application.getId());
                bundle.putString("source", "upload");
                intent.setClass(mainview.getContext(), ActivityConfirm.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setListViewSource();

        return mainview;
    }
    private void setListViewSource() {

        listItems = dbHelper.loadApplicationBySendNot("0");
        adapter = new MainListAdapter(getActivity(), listItems);
        listView.setAdapter(adapter);
    }

    protected void updateListViewSource() {

        listItems = dbHelper.loadApplicationBySendNot("0");
        adapter = new MainListAdapter(getActivity(), listItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ptrFrame.refreshComplete();
    }
}
