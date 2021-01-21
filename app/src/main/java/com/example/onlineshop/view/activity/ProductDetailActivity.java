package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragmenet.ProductDetailFragment;

public class ProductDetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_PRODUCT_ID = "extra_product_id";

    public static Intent newIntent (Context context, int id){
        Intent intent = new Intent(context,ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID,id);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return ProductDetailFragment.newInstance(getIntent().getIntExtra(EXTRA_PRODUCT_ID,0));
    }
}