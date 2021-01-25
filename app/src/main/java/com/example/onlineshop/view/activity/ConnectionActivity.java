package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityConnectionBinding;
import com.example.onlineshop.receiver.ConnectionReceiver;
import com.example.onlineshop.viewmodel.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ConnectionActivity extends AppCompatActivity {
    private ActivityConnectionBinding mConnectionBinding;
    private SplashViewModel mSplashViewModel;
    private ConnectionReceiver mConnectionReceiver;

    public static Intent newIntent (Context context){
        return new Intent(context, ConnectionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mConnectionBinding = DataBindingUtil.setContentView(this,R.layout.activity_connection);
        mSplashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        mSplashViewModel.setInConnectionActivity(true);
        getSupportActionBar().hide();
        mConnectionReceiver = new ConnectionReceiver();

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_connection);

        lottieAnimationView.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOnline()) {
//                    mSplashBinding.progressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(mConnectionBinding.connectionLayout,"No Internet Access",Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                    snackbar.show();
                } else {
                    finish();
                }
            }
        }, 1000);
    }

    private boolean isOnline() {
        return mSplashViewModel.isWiFiEnable();
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mConnectionReceiver, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(mConnectionReceiver);
    }
}