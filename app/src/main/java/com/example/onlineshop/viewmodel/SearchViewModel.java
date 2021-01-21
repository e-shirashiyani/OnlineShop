package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.onlineshop.data.model.ColorAttribute;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.repository.OnlineStoreRepository;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.view.activity.ProductDetailActivity;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private OnlineStoreRepository mRepository;
    private List<Product> mSearchProduct;
    private Context mContext;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = new OnlineStoreRepository();

    }

    public List<Product> getSearchProduct() {
        return mSearchProduct;
    }

    public void setSearchProduct(List<Product> searchProduct) {
        mSearchProduct = searchProduct;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public LiveData<List<Product>> getSearchItemsLiveData() {
        return mRepository.getSearchProductsLiveData();
    }

    public LiveData<List<Product>> getSortedLowToHighSearchItemsLiveData() {
        return mRepository.getSortedLowToHighSearchProductsLiveData();
    }

    public LiveData<List<Product>> getSortedHighToLowSearchItemsLiveData() {
        return mRepository.getSortedHighToLowSearchProductsLiveData();
    }

    public LiveData<List<Product>> getSortedTotalSalesSearchItemsLiveData() {
        return mRepository.getSortedTotalSalesSearchProductsLiveData();
    }

    public LiveData<List<ColorAttribute>> getColorsLiveData() {
        return mRepository.getLiveDataColor();
    }

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }

    public void fetchSearchItemsAsync(String query) {
        mRepository.fetchSearchItemsAsync(query);
    }

    public void fetchColorAttributeAsync() {
        mRepository.fetchColorAttributeAsync();
    }

    public void fetchSortedLowToHighSearchItemsAsync(String query) {
        mRepository.fetchSortedLowToHighSearchItemsAsync(query);
    }

    public void fetchSortedTotalSalesSearchItemsAsync(String query) {
        mRepository.fetchSortedTotalSalesSearchItemsAsync(query);
    }

    public void fetchSortedHighToLowSearchItemsAsync(String query) {
        mRepository.fetchSortedHighToLowSearchItemsAsync(query);
    }

    public void setQueryInPreferences(String query) {
        QueryPreferences.setSearchQuery(getApplication(), query);
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }

    public void setColorInPreferences(String color) {
        QueryPreferences.setFilterColor(getApplication(), color);
    }

    public String getColorFromPreferences() {
        return QueryPreferences.getFilterColor(getApplication());
    }

    public void setProductIdForFilterInPreferences(String productId) {
        QueryPreferences.setFilterProductId(getApplication(), productId);
    }

    public String getProductIdForFilterFromPreferences() {
        return QueryPreferences.getFilterProductId(getApplication());
    }

    public void fetchProductItems(int productId){
        mRepository.fetchProductItemAsync(productId);
    }

    public LiveData<Product> getLiveDateProduct(){
        return mRepository.getProductLiveData();
    }

    public void setSort(int sortId){
        mRepository.setSort(sortId);
    }

    public int getSort(){
        return mRepository.getSort();
    }
}
