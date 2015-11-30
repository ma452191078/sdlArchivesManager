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
 * 首页申请列表控制器
 */
public class MainListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<BeanAudit> dataArray = new ArrayList<BeanAudit>();

    public MainListAdapter(Activity activity, List<BeanAudit> mListItems) {

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
        BeanAudit audit = (BeanAudit) getItem(position);

        TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
        tvStatus.setText(audit.getStatus().toString());

        TextView tvClientName = (TextView) convertView.findViewById(R.id.tv_clientname);
        tvClientName.setText(audit.getClientName().toString());

        TextView tvClientAddress = (TextView) convertView.findViewById(R.id.tv_clientaddr);
        tvClientAddress.setText(audit.getClientAddress().toString());

        TextView tvClientMan = (TextView) convertView.findViewById(R.id.tv_clientman);
        tvClientMan.setText(audit.getClientOwner().toString());



        return convertView;
    }
}
