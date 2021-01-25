package com.example.onlineshop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.example.onlineshop.data.repository.SplashDBRepository;
import com.example.onlineshop.viewmodel.SplashViewModel;

public class ConnectionReceiver extends BroadcastReceiver {

    private SplashDBRepository mSplashDBRepository;


    @Override
    public void onReceive(Context context, Intent intent) {

        mSplashDBRepository= SplashDBRepository.getInstance(context);
        int wifiState= intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);
        if(wifiState==WifiManager.WIFI_STATE_ENABLED){
            mSplashDBRepository.setWiFiEnable(true);
        }
        else if(wifiState==WifiManager.WIFI_STATE_DISABLED){
            mSplashDBRepository.setWiFiEnable(false);
            if(!mSplashDBRepository.isInConnectionActivity())
                context.startActivity(Connection);
        }


    }
}
