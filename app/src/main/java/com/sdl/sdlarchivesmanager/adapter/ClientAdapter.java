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
 * Created by majingyuan on 15/11/30.
 * 经销商列表控制器
 */
public class ClientAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Client> dataArray = new ArrayList<Client>();

    public ClientAdapter(Activity activity, List<Client> mListItems) {

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
        Client client = (Client) getItem(position);

        TextView tvClientName = (TextView) convertView.findViewById(R.id.tv_clientname);
        tvClientName.setText(client.getClient_Name());

        TextView tvClientOwner = (TextView) convertView.findViewById(R.id.tv_clientowner);
        tvClientOwner.setText(client.getClient_Owner());

        TextView tvClientAddress = (TextView) convertView.findViewById(R.id.tv_clientaddr);
        tvClientAddress.setText(client.getClient_Address());

        TextView tvClientLevel = (TextView) convertView.findViewById(R.id.tv_clientlevel);
        if (client.getClient_Type().equals("种植大户")){
            tvClientLevel.setText("种植大户");
        }else
        {
            tvClientLevel.setText(client.getClient_Level());
        }


        return convertView;
    }
}
