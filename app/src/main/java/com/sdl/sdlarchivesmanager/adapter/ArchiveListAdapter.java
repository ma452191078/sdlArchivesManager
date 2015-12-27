package com.sdl.sdlarchivesmanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.bean.BeanAudit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majingyuan on 15/11/30.
 * 经销商列表控制器
 */
public class ArchiveListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BeanAudit> dataArray = new ArrayList<BeanAudit>();

    public ArchiveListAdapter(Activity activity, List<BeanAudit> mListItems) {

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
            convertView = inflater.inflate(R.layout.activity_client_list_item,null);
        }
        convertView.setTag(position);
        BeanAudit audit = (BeanAudit) getItem(position);

        TextView tvClientName = (TextView) convertView.findViewById(R.id.tv_clientname);
        tvClientName.setText(audit.getClientName().toString());

        TextView tvClientOwner = (TextView) convertView.findViewById(R.id.tv_clientowner);
        tvClientOwner.setText(audit.getClientOwner().toString());

        TextView tvClientAddress = (TextView) convertView.findViewById(R.id.tv_clientaddr);
        tvClientAddress.setText(audit.getClientAddress().toString());

        TextView tvClientLevel = (TextView) convertView.findViewById(R.id.tv_clientlevel);
        if (audit.getClientType().toString().equals("1")){
            tvClientLevel.setText("种植大户");
        }else {
            switch (audit.getClientLevel().toString()){
                case "1":
                    tvClientLevel.setText("一级商");
                    break;
                case "2":
                    tvClientLevel.setText("二级商");
                    break;
                case "3":
                    tvClientLevel.setText("三级商");
                    break;
                default:
                    tvClientLevel.setText("");
                    break;
            }
        }




        return convertView;
    }
}
