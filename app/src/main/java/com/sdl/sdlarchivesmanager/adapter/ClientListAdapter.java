package com.sdl.sdlarchivesmanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Client;
import com.sdl.sdlarchivesmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majingyuan on 16/1/6.
 * 创建时选择经销商使用的列表
 */
public class ClientListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Client> dataArray = new ArrayList<Client>();

    public ClientListAdapter(Activity activity, List<Client> mListItems) {

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
            convertView = inflater.inflate(R.layout.listitemaddress,null);
        }
        convertView.setTag(position);
        Client client = (Client) getItem(position);

        TextView tvAddr = (TextView) convertView.findViewById(R.id.tv_addritem);
        tvAddr.setText(client.getClient_Name());


        return convertView;
    }
}
