package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragmenet.NotificationFragment;

public class NotificationActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,NotificationActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return NotificationFragment.newInstance();
    }

}