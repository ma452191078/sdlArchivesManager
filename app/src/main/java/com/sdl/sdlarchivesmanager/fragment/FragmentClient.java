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

import com.sdl.sdlarchivesmanager.Client;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.activity.ActivityClientInfo;
import com.sdl.sdlarchivesmanager.adapter.ClientListAdapter;
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
public class FragmentClient extends Fragment {

    public FragmentClient(){

    }

    private View mainview;

    private FloatingActionButton fabSearch;
    private List<Client> listClient = new ArrayList<Client>();
    private PtrClassicFrameLayout ptrFrame;
    private ListView listView;
    private ClientListAdapter adapter;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.activity_client_list, null);
        dbHelper = DBHelper.getInstance(getContext());

        fabSearch = (FloatingActionButton) mainview.findViewById(R.id.fab_search);
        listView = (ListView) mainview.findViewById(R.id.lv_itemlist);
        fabSearch.setVisibility(View.GONE);
//        fabSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mainview.getContext(),"搜索",Toast.LENGTH_SHORT).show();
//            }
//        });

//        下拉刷新控件
        setPtrFrame();

//        列表项目
        setListView();
        setListViewSource();

        return mainview;
    }

    protected void updateData(Context context) {

        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
        ptrFrame.refreshComplete();
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
                Client client = (Client) parent.getAdapter().getItem(position);
                bundle.putLong("id", client.getId());
                bundle.putString("source", "client");
                intent.setClass(mainview.getContext(), ActivityClientInfo.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void setListViewSource() {

        listClient = dbHelper.loadAllClient();
        adapter = new ClientListAdapter(getActivity(), listClient);
        listView.setAdapter(adapter);
    }

    protected void updateListViewSource(Context context) {

        listClient = dbHelper.loadAllClient();
        adapter = new ClientListAdapter(getActivity(), listClient);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ptrFrame.refreshComplete();
        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
    }
}
