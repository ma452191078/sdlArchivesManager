package com.sdl.sdlarchivesmanager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdl.sdlarchivesmanager.R;
import com.sdl.sdlarchivesmanager.util.UpdateManager;

/**
 * Created by majingyuan on 15/12/20.
 * 设置页面
 */
public class SettingFragment extends android.support.v4.app.Fragment {

    public SettingFragment(){

    }

    private  View mainview;
    private TextView tvVersion;
    UpdateManager updateManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.activity_setting, null);
        tvVersion = (TextView) mainview.findViewById(R.id.tv_version);
        updateManager = new UpdateManager(mainview.getContext());
        String version = updateManager.getVerName(mainview.getContext())
                +"."+ updateManager.getVerCode(mainview.getContext());
        tvVersion.setText(version);

        return mainview;
    }
}
