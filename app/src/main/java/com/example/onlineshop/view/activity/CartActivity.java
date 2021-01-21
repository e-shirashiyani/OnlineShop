package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.view.fragmenet.CartFragment;

public class CartActivity extends SingleFragmentActivity {
public static Intent newIntent(Context context){
    return new Intent(context,CartActivity.class);
}
    @Override
    public Fragment createFragment() {
        return CartFragment.newInstance();
    }

}