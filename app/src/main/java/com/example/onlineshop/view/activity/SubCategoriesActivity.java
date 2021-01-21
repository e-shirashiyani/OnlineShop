package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragmenet.SubCategoriesFragment;

public class SubCategoriesActivity extends SingleFragmentActivity {

    public static final String PARENT_ID = "com.example.onlineshop.Parent_id";

    public static Intent newIntent(Context context, int id){
        Intent intent = new Intent(context,SubCategoriesActivity.class);
        intent.putExtra(PARENT_ID,id);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return SubCategoriesFragment.newInstance(getIntent().getIntExtra(PARENT_ID,0));
    }
}