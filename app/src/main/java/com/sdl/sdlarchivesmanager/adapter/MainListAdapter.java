package com.sdl.sdlarchivesmanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Application;
import com.sdl.sdlarchivesmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majingyuan on 15/11/30.
 * 首页申请列表控制器
 */
public class MainListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Application> dataArray = new ArrayList<Application>();

    public MainListAdapter(Activity activity, List<Application> mListItems) {

        inflater = activity.getLayoutInflater();
        dataArray = mListItems;
    }

    @Override
    public int getCount() {
        return dataArray.toArray().length;
    }

    @Override
    public Object getItem(int position) {
        if (position >= getCount()) {
            return null;
        }
        return dataArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(R.layout.activity_main_list_item,null);
        }
        convertView.setTag(position);
        Application application = (Application) getItem(position);

        TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
        if (application.getApp_Send().toString() == "0"){
            tvStatus.setText(application.getApp_Status().toString());
        }else if (application.getApp_Send().toString() == "1"){
            tvStatus.setText("未上传");
        }else if (application.getApp_Send().toString() == "2"){
            tvStatus.setText("编辑未完成,无法上传");
        }

        TextView tvClientName = (TextView) convertView.findViewById(R.id.tv_clientname);
        tvClientName.setText(application.getApp_Name().toString());

        TextView tvClientAddress = (TextView) convertView.findViewById(R.id.tv_clientaddr);
        tvClientAddress.setText(application.getApp_Address());

        TextView tvClientMan = (TextView) convertView.findViewById(R.id.tv_clientman);
        tvClientMan.setText(application.getApp_Owner().toString());

        return convertView;
    }
}
