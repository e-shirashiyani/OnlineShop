package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivitySpalshBinding;
import com.google.android.material.snackbar.Snackbar;

import java.net.ConnectException;

public class SplashActivity extends AppCompatActivity {
    private ActivitySpalshBinding mSplashBinding;
    private Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashBinding= DataBindingUtil.setContentView(this,R.layout.activity_spalsh);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!isOnline()){
                    Snackbar snackbar=Snackbar
                            .make(mSplashBinding.splashLayout,"No Internet Access",Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    recreate();
                                }
                            });
                    snackbar.show();

                }else {
                    Intent intent=new Intent(mContext,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },4000);

    }
    private boolean isOnline(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return  connectivityManager.getActiveNetworkInfo()!=null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}