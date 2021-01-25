package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.repository.SplashDBRepository;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class SplashViewModel extends AndroidViewModel {

    private SplashDBRepository mSplashRepository;


    public SplashViewModel(Application application){
        super(application);
        mSplashRepository=SplashDBRepository.getInstance(application);
    }
    public List<Product> getMostVisitedProduct(){
        return mSplashRepository.getMostVisitedProduct();

    }
    public void setMostVisitedProduct(List<Product> mostVisitedProduct){
        mSplashRepository.setMostVisitedProduct(mostVisitedProduct);
    }
    public List<Product> getLatestProduct(){
        return mSplashRepository.getLatestProduct();
    }
    public void setLatestProduct(List<Product> latestProduct) {
        mSplashRepository.setLatestProduct(latestProduct);
    }

    public List<Product> getHighestScoreProduct() {
        return mSplashRepository.getHighestScoreProduct();
    }

    public void setHighestScoreProduct(List<Product> highestScoreProduct) {
        mSplashRepository.setHighestScoreProduct(highestScoreProduct);
    }

    public List<Product> getSpecialProduct() {
        return mSplashRepository.getSpecialProduct();
    }

    public void setSpecialProduct(List<Product> specialProduct) {
        mSplashRepository.setSpecialProduct(specialProduct);
    }

    public boolean isWiFiEnable() {
        return mSplashRepository.isWiFiEnable();
    }

    public void setWiFiEnable(boolean wiFiEnable) {
        mSplashRepository.setWiFiEnable(wiFiEnable);
    }

    public boolean isInConnectionActivity() {
        return mSplashRepository.isInConnectionActivity();
    }

    public void setInConnectionActivity(boolean inConnectionActivity) {
        mSplashRepository.setInConnectionActivity(inConnectionActivity);
    }
}
