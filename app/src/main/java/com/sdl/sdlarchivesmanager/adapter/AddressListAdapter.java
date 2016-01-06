package com.sdl.sdlarchivesmanager.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.Address;
import com.sdl.sdlarchivesmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by majingyuan on 15/11/30.
 * 地址列表控制器
 */
public class AddressListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Address> dataArray = new ArrayList<Address>();

    public AddressListAdapter(Activity activity, List<Address> mListItems) {

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
        Address address = (Address) getItem(position);

        TextView tvAddr = (TextView) convertView.findViewById(R.id.tv_addritem);
        tvAddr.setText(address.getAddr_Name());

        return convertView;
    }
}
