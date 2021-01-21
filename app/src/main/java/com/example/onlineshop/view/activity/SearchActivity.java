package com.example.onlineshop.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlineshop.view.fragmenet.SearchFragment;

public class SearchActivity extends SingleFragmentActivity{
    public static final String SEARCH_QUERY = "search_query";
    public static final String REQUEST_CODE = "request_code";
    public static final String PRODUCT_ID = "productId";

    public static Intent newIntent (Context context, String query,String requestCode,String productId){
        Intent intent = new Intent(context,SearchActivity.class);
        intent.putExtra(SEARCH_QUERY,query);
        intent.putExtra(REQUEST_CODE,requestCode);
        intent.putExtra(PRODUCT_ID,productId);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return SearchFragment.newInstance(getIntent().getStringExtra(SEARCH_QUERY),
                getIntent().getStringExtra(REQUEST_CODE),getIntent().getStringExtra(PRODUCT_ID));
    }
}
