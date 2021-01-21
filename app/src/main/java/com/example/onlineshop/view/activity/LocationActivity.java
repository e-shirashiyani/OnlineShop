package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragmenet.LocationFragment;

public class LocationActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,LocationActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return LocationFragment.newInstance();
    }
}