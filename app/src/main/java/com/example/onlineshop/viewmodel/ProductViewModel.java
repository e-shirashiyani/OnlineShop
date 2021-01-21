package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.data.model.Comment;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.repository.OnlineStoreRepository;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.view.activity.ProductDetailActivity;
import com.example.onlineshop.worker.PollWorker;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private OnlineStoreRepository mRepository;
    private List<Product> mProductListMostVisited;
    private List<Product> mProductListLatest;
    private List<Product> mProductListHighestScore;
    private Context mContext;
    private Product mDetailedProduct;
    private List<Comment> mCommentList;


    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new OnlineStoreRepository();

    }

    public List<Product> getProductListMostVisited() {
        return mProductListMostVisited;
    }

    public void setProductListMostVisited(List<Product> productListMostVisited) {
        mProductListMostVisited = productListMostVisited;
    }

    public List<Product> getProductListLatest() {
        return mProductListLatest;
    }

    public void setProductListLatest(List<Product> productListLatest) {
        mProductListLatest = productListLatest;
    }

    public List<Product> getProductListHighestScore() {
        return mProductListHighestScore;
    }

    public void setProductListHighestScore(List<Product> productListHighestScore) {
        mProductListHighestScore = productListHighestScore;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Product getDetailedProduct() {
        return mDetailedProduct;
    }

    public void setDetailedProduct(Product detailedProduct) {
        mDetailedProduct = detailedProduct;
    }

    public List<Comment> getCommentList() {
        return mCommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        mCommentList = commentList;
    }

    public void fetchProductItems(int productId){
        mRepository.fetchProductItemAsync(productId);
    }

    public void fetchComment(String productId){
        mRepository.fetchCommentAsync(productId);
    }

    public void fetchMostVisitedProductItems(){
        mRepository.fetchMostVisitedProductItemsAsync();
    }

    public void fetchLatestProductItems(){
        mRepository.fetchLatestProductItemsAsync();
    }

    public void fetchHighestScoreProductItems(){
        mRepository.fetchHighestScoreProductItemsAsync();
    }

    public LiveData<List<Product>> getLiveDateMostVisitedProducts(){
        return mRepository.getMostVisitedProductsLiveData();
    }

    public LiveData<List<Product>> getLiveDateLatestProducts(){
        return mRepository.getLatestProductsLiveData();
    }

    public LiveData<Product> getLiveDateProduct(){
        return mRepository.getProductLiveData();
    }

    public LiveData<List<Comment>> getLiveDateComment(){
        return mRepository.getLiveDataComment();
    }

    public LiveData<List<Product>> getLiveDateHighestScoreProducts(){
        return mRepository.getHighestScoreProductsLiveData();
    }

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }

    public void fetchSpecialProductItems(String parentId,String page) {
        mRepository.fetchSpecialProductItemsAsync(parentId,page);
    }

    public LiveData<List<Product>> getLiveDataSpecialProduct1(){
        return mRepository.getSpecialProductsLiveData1();
    }

    public LiveData<List<Product>> getLiveDataSpecialProduct2(){
        return mRepository.getSpecialProductsLiveData2();
    }

    public LiveData<List<Product>> getLiveDataSpecialProduct3(){
        return mRepository.getSpecialProductsLiveData3();
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        long time = QueryPreferences.getNotificationTime(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn,time);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }
}
