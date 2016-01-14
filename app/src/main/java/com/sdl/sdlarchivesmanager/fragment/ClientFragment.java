package com.sdl.sdlarchivesmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sdl.sdlarchivesmanager.Client;
import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.User;
import com.sdl.sdlarchivesmanager.activity.ArchiveApplication;
import com.sdl.sdlarchivesmanager.activity.ClientInfoActivity;
import com.sdl.sdlarchivesmanager.adapter.ClientAdapter;
import com.sdl.sdlarchivesmanager.db.DBHelper;
import com.sdl.sdlarchivesmanager.http.NormalPostRequest;
import com.sdl.sdlarchivesmanager.util.sdlClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by majingyuan on 15/12/20.
 * 经销商列表
 */
public class ClientFragment extends Fragment {

    public ClientFragment(){

    }

    private View mainview;

    private FloatingActionButton fabSearch;
    private PtrClassicFrameLayout ptrFrame;
    private ListView listView;
    private ClientAdapter adapter;
    private DBHelper dbHelper;
    private static String BASEURL="AppSyncAction!getSellerListBySalesId";
    private LoadMoreListViewContainer loadMoreListViewContainer;
    private JSONArray array;
    private int click = 0;
    private List<Client> listClient = new ArrayList<Client>();
    private String userNum = "";
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.activity_client_list, null);
        dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());

        user = new User();
        user = dbHelper.loadUserByStatus();

//        判断用户是否存在
        if (user != null) {

                userNum = user.getUser_Num();
            }

        userNum = "20140009";

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

//        加载更多
        setLoadMore();


//        列表项目
        setListView();
        if (userNum != null){
            getClientList(userNum, "");

        }

        return mainview;
    }

    protected void updateData(Context context) {

        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
        ptrFrame.refreshComplete();
    }

    private void setLoadMore(){
        loadMoreListViewContainer = (LoadMoreListViewContainer) mainview.findViewById(R.id.load_more);
        //loadMoreListViewContainer.useDefaultFooter();

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {

                getClientList(userNum, "");
            }
        });
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
                getClientList(userNum, "");
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
                intent.setClass(mainview.getContext(), ClientInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getClientList(String id, String client) {

        click ++;

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
                                    listClient.add(client);


                                }
                                adapter = new ClientAdapter(getActivity(), listClient);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(getContext(),"暂无经销商", Toast.LENGTH_SHORT).show();
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
        ArchiveApplication.getHttpQueues().add(provRequest);
    }

    protected void updateListViewSource(Context context) {

        listClient = dbHelper.loadAllClient();
        adapter = new ClientAdapter(getActivity(), listClient);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ptrFrame.refreshComplete();
        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
    }
}
