package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.model.ProductCategory;
import com.example.onlineshop.data.repository.OnlineStoreRepository;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.view.activity.ProductDetailActivity;
import com.example.onlineshop.view.activity.SubCategoriesActivity;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private OnlineStoreRepository mRepository;
    private List<Product> mProductList;
    private List<ProductCategory> mCategoryList;
    private Context mContext;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new OnlineStoreRepository();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public List<ProductCategory> getCategoryList() {
        return mCategoryList;
    }

    public void setCategoryList(List<ProductCategory> categoryList) {
        mCategoryList = categoryList;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void fetchCategoryItems() {
        mRepository.fetchCategoryItemsAsync();
    }

    public void fetchSubCategoryItems(String parentId) {
        mRepository.fetchSubCategoryItemsAsync(parentId);
    }

    public void fetchProductItemsWithParentId(String parentId) {
        mRepository.fetchProductItemsWithParentIdAsync(parentId);
    }

    public LiveData<List<ProductCategory>> getLiveDataCategoryItems(){
        return mRepository.getCategoryItemsLiveData();
    }

    public LiveData<List<Product>> getLiveDataProductWithParentId(){
        return mRepository.getProductWithParentIdLiveData();
    }

    public void onClickListItem(int productId,String state) {
        if (state.equalsIgnoreCase("product"))
            mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
        else if (state.equalsIgnoreCase("category"))
            mContext.startActivity(SubCategoriesActivity.newIntent(mContext,productId));
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }

}
