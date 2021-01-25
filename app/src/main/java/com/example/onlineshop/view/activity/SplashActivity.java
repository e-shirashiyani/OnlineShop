package com.example.onlineshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.databinding.ActivitySpalshBinding;
import com.example.onlineshop.viewmodel.ProductViewModel;
import com.example.onlineshop.viewmodel.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private ActivitySpalshBinding mSplashBinding;
    private ProductViewModel mProductViewModel;
    private SplashViewModel mSplashViewModel;
    private LiveData<List<Product>> mMostVisitedProductItemsLiveData;
    private LiveData<List<Product>> mLatestProductItemsLiveData;
    private LiveData<List<Product>> mHighestScoreProductItemsLiveData;
    private LiveData<List<Product>> mSpecialProductsLiveData1;
    private LiveData<List<Product>> mSpecialProductsLiveData2;
    private LiveData<List<Product>> mSpecialProductsLiveData3;
    private List<Product> mSpecialProducts;
    private boolean mFlagMostVisit,mFlagLatest,mFlagHighest,mFlagSpecial;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSplashBinding = DataBindingUtil.setContentView(this,R.layout.activity_spalsh);

        getSupportActionBar().hide();
        mSpecialProducts = new ArrayList<>();
        mFlagMostVisit = false;
        mFlagLatest = false;
        mFlagHighest = false;
        mFlagSpecial = false;

        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimation);

        //request from server using retrofit. [play animation]
        getProductsFromProductViewModel();
        mSplashViewModel.setInConnectionActivity(false);
        lottieAnimationView.playAnimation();



        //get the result in observer livedata.[go to home activity and finish this one]
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOnline()) {
//                    mSplashBinding.progressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(mSplashBinding.splashLayout,"No Internet Access",Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                    View view = snackbar.getView();
                    FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snackbar.show();
                    mSplashViewModel.setWiFiEnable(false);
                } else {
                    setObserver();
                    mSplashViewModel.setWiFiEnable(true);
                }
            }
        }, 2000);
    }

    private void getProductsFromProductViewModel() {
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mSplashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        mProductViewModel.fetchMostVisitedProductItems();
        mProductViewModel.fetchLatestProductItems();
        mProductViewModel.fetchHighestScoreProductItems();
        mMostVisitedProductItemsLiveData = mProductViewModel.getLiveDateMostVisitedProducts();
        mLatestProductItemsLiveData = mProductViewModel.getLiveDateLatestProducts();
        mHighestScoreProductItemsLiveData = mProductViewModel.getLiveDateHighestScoreProducts();

        for (int i = 1; i < 4; i++) {
            mProductViewModel.fetchSpecialProductItems(String.valueOf(119), i + "");
            if (i == 1)
                mSpecialProductsLiveData1 = mProductViewModel.getLiveDataSpecialProduct1();
            else if (i==2)
                mSpecialProductsLiveData2 = mProductViewModel.getLiveDataSpecialProduct2();
            else if (i==3)
                mSpecialProductsLiveData3 = mProductViewModel.getLiveDataSpecialProduct3();
        }

    }

    private void setObserver() {
        mMostVisitedProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mSplashViewModel.setMostVisitedProduct(products);
                mFlagMostVisit = true;
                startHomeActivity();
            }
        });
        mLatestProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mSplashViewModel.setLatestProduct(products);
                mFlagLatest = true;
                startHomeActivity();
            }
        });
        mHighestScoreProductItemsLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mSplashViewModel.setHighestScoreProduct(products);
                mFlagHighest = true;
                startHomeActivity();
            }
        });
        mSpecialProductsLiveData1.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSpecialProducts.addAll(productList);

            }
        });
        mSpecialProductsLiveData2.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSpecialProducts.addAll(productList);

            }
        });
        mSpecialProductsLiveData3.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSpecialProducts.addAll(productList);
                mSplashViewModel.setSpecialProduct(mSpecialProducts);
                mFlagSpecial = true;
                startHomeActivity();

            }
        });
    }

    private void startHomeActivity() {
        if (!mFlagSpecial || !mFlagLatest || !mFlagMostVisit || !mFlagHighest )
            return;

        startActivity(HomeActivity.newIntent(SplashActivity.this));
        finish();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}