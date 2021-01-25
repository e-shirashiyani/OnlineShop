package com.example.onlineshop.data.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.onlineshop.data.model.ColorAttribute;
import com.example.onlineshop.data.model.Comment;
import com.example.onlineshop.data.model.Coupons;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.model.ProductCategory;
import com.example.onlineshop.data.model.SalesReport;
import com.example.onlineshop.data.remote.NetworkParams;
import com.example.onlineshop.data.remote.retrofit.APIService;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceCategory;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceColor;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceComments;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceCoupons;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceListOfProduct;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceProduct;
import com.example.onlineshop.data.remote.retrofit.RetrofitInstanceSales;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnlineStoreRepository {
    private static final String TAG = "PhotoRepository";

    private final APIService mAPIServiceListOfProduct;
    private final APIService mAPIServiceProduct;
    private final APIService mAPIServiceCategory;
    private final APIService mAPIServiceSalesReport;
    private final APIService mAPIServiceCustomer;
    private final APIService mAPIServiceComment;
    private final APIService mAPIServiceCoupons;
    private MutableLiveData<List<Product>> mProductItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductWithParentIdLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mMostVisitedProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mLatestProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mHighestScoreProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductCategory>> mCategoryItemsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mFilterSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedLowToHighSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedHighToLowSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSortedTotalSalesSearchProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductsLiveData1 = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductsLiveData2 = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductsLiveData3 = new MutableLiveData<>();
    private MutableLiveData<Customer> mCustomerLiveData = new MutableLiveData<>();
    private MutableLiveData<Comment> mCommentLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> mLiveDataComment = new MutableLiveData<>();
    private MutableLiveData<List<ColorAttribute>> mLiveDataColor = new MutableLiveData<>();
    private MutableLiveData<Comment> mLiveDataOneComment = new MutableLiveData<>();
    private MutableLiveData<Comment> mLiveDataPUTComment = new MutableLiveData<>();
    private MutableLiveData<Comment> mLiveDataDeleteComment = new MutableLiveData<>();
    private MutableLiveData<List<Coupons>> mLiveDataCoupons = new MutableLiveData<>();
    private static int mSort;

    public MutableLiveData<Comment> getLiveDataOneComment() {
        return mLiveDataOneComment;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int sort) {
        mSort = sort;
    }

    public MutableLiveData<List<Product>> getSearchProductsLiveData() {
        return mSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getFilterSearchProductsLiveData() {
        return mFilterSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getProductWithParentIdLiveData() {
        return mProductWithParentIdLiveData;
    }

    public MutableLiveData<List<ProductCategory>> getCategoryItemsLiveData() {
        return mCategoryItemsLiveData;
    }

    public MutableLiveData<List<Product>> getMostVisitedProductsLiveData() {
        return mMostVisitedProductsLiveData;
    }

    public MutableLiveData<List<Product>> getLatestProductsLiveData() {
        return mLatestProductsLiveData;
    }

    public MutableLiveData<List<Product>> getHighestScoreProductsLiveData() {
        return mHighestScoreProductsLiveData;
    }

    public MutableLiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public MutableLiveData<List<Product>> getSortedLowToHighSearchProductsLiveData() {
        return mSortedLowToHighSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSortedHighToLowSearchProductsLiveData() {
        return mSortedHighToLowSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSortedTotalSalesSearchProductsLiveData() {
        return mSortedTotalSalesSearchProductsLiveData;
    }

    public MutableLiveData<List<Product>> getSpecialProductsLiveData1() {
        return mSpecialProductsLiveData1;
    }

    public MutableLiveData<List<Product>> getSpecialProductsLiveData2() {
        return mSpecialProductsLiveData2;
    }

    public MutableLiveData<List<Product>> getSpecialProductsLiveData3() {
        return mSpecialProductsLiveData3;
    }

    public MutableLiveData<List<ColorAttribute>> getLiveDataColor() {
        return mLiveDataColor;
    }

    public MutableLiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public MutableLiveData<List<Comment>> getLiveDataComment() {
        return mLiveDataComment;
    }

    public MutableLiveData<List<Coupons>> getLiveDataCoupons() {
        return mLiveDataCoupons;
    }

    public OnlineStoreRepository() {
        Retrofit retrofitListOfProduct = RetrofitInstanceListOfProduct.getInstance().getRetrofit();
        mAPIServiceListOfProduct = retrofitListOfProduct.create(APIService.class);

        Retrofit retrofitCategory = RetrofitInstanceCategory.getInstance().getRetrofit();
        mAPIServiceCategory = retrofitCategory.create(APIService.class);

        Retrofit retrofitProduct = RetrofitInstanceProduct.getInstance().getRetrofit();
        mAPIServiceProduct = retrofitProduct.create(APIService.class);

        Retrofit retrofitSalesReport = RetrofitInstanceSales.getInstance().getRetrofit();
        mAPIServiceSalesReport = retrofitSalesReport.create(APIService.class);

        Retrofit retrofitCustomer = RetrofitInstanceSales.getInstance().getRetrofit();
        mAPIServiceCustomer = retrofitCustomer.create(APIService.class);

        Retrofit retrofitComment = RetrofitInstanceComments.getInstance().getRetrofit();
        mAPIServiceComment = retrofitComment.create(APIService.class);

        Retrofit retrofitCoupons = RetrofitInstanceCoupons.getInstance().getRetrofit();
        mAPIServiceCoupons = retrofitCoupons.create(APIService.class);
    }

    //this method must run on background thread.
    public List<SalesReport> fetchSalesReport() {
        Call<List<SalesReport>> call = mAPIServiceSalesReport.sales(NetworkParams.getMainAddress());
        try {
            Response<List<SalesReport>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    //this method can be run in any thread.
    public void fetchProductItemsAsync(String page) {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.products(NetworkParams.getProducts(page));

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                mProductItemsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchMostVisitedProductItemsAsync() {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.products(NetworkParams.getMostVisitedProducts());

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                mMostVisitedProductsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchLatestProductItemsAsync() {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.products(NetworkParams.getLatestProducts());

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                mLatestProductsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchHighestScoreProductItemsAsync() {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.products(NetworkParams.getHighestScoreProducts());

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                mHighestScoreProductsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductItemsWithParentIdAsync(String id) {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.products(NetworkParams.getProductsWithParentId(id));

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                mProductWithParentIdLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSpecialProductItemsAsync(String id, String page) {
        Call<List<Product>> call =
                mAPIServiceListOfProduct.products(NetworkParams.getSpecialProducts(id, page));

        call.enqueue(new Callback<List<Product>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                //update adapter of recyclerview
                if (page.equalsIgnoreCase("1"))
                    mSpecialProductsLiveData1.postValue(items);
                else if (page.equalsIgnoreCase("2"))
                    mSpecialProductsLiveData2.postValue(items);
                else if (page.equalsIgnoreCase("3"))
                    mSpecialProductsLiveData3.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategoryItemsAsync() {
        Call<List<ProductCategory>> call =
                mAPIServiceCategory.categories(NetworkParams.getCategories());

        call.enqueue(new Callback<List<ProductCategory>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                List<ProductCategory> items = response.body();

                //update adapter of recyclerview
                mCategoryItemsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSubCategoryItemsAsync(String id) {
        Call<List<ProductCategory>> call =
                mAPIServiceCategory.categories(NetworkParams.subCategories(id));

        call.enqueue(new Callback<List<ProductCategory>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<ProductCategory>> call, Response<List<ProductCategory>> response) {
                List<ProductCategory> items = response.body();

                //update adapter of recyclerview
                mCategoryItemsLiveData.postValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<ProductCategory>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductItemAsync(int id) {
        Call<Product> call =
                mAPIServiceProduct.getProduct(id, NetworkParams.getProducts("1"));

        call.enqueue(new Callback<Product>() {

            //this run on main thread
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product item = response.body();

                //update adapter of recyclerview
                mProductLiveData.postValue(item);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.products(NetworkParams.getSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchFilterSearchItemsAsync(String query,String colorId) {
        Call<List<Product>> call =
                mAPIServiceProduct.products(NetworkParams.getFilterSearchProducts(query,colorId));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mFilterSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSortedLowToHighSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.products(NetworkParams.getSortedLowToHighSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSortedLowToHighSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSortedHighToLowSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.products(NetworkParams.getSortedHighToLowSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSortedHighToLowSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSortedTotalSalesSearchItemsAsync(String query) {
        Call<List<Product>> call =
                mAPIServiceProduct.products(NetworkParams.getSortedTotalSalesSearchProducts(query));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();

                mSortedTotalSalesSearchProductsLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchColorAttributeAsync() {
        Call<List<ColorAttribute>> call =
                mAPIServiceComment.colors(NetworkParams.getMainAddress());

        call.enqueue(new Callback<List<ColorAttribute>>() {
            @Override
            public void onResponse(Call<List<ColorAttribute>> call, Response<List<ColorAttribute>> response) {
                List<ColorAttribute> items = response.body();

                mLiveDataColor.postValue(items);
            }

            @Override
            public void onFailure(Call<List<ColorAttribute>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCreateCustomerAsync(Customer customer) {
        Call<Customer> call =
                mAPIServiceCustomer.customer(customer,NetworkParams.getMainAddress());

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Customer items = response.body();

                mCustomerLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchAddCommentAsync(Comment comment) {
        Call<Comment> call =
                mAPIServiceComment.addComment(comment.getProduct_id(),comment.getReview()
                        ,comment.getReviewer(),comment.getReviewer_email(),comment.getRating(),
                        NetworkParams.getMainAddress());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mCommentLiveData.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchCommentAsync(String productId) {
        Call<List<Comment>> call =
                mAPIServiceComment.comments(NetworkParams.getCommentOfProduct(productId));

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> items = response.body();

                mLiveDataComment.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchOneCommentAsync(int commentId) {
        Call<Comment> call =
                mAPIServiceComment.getCommentWithId(commentId,NetworkParams.getMainAddress());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mLiveDataOneComment.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchPUTCommentAsync(Comment comment) {
        Call<Comment> call =
                mAPIServiceComment.putCommentWithId(comment,comment.getId(),
                        NetworkParams.getMainAddress());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mLiveDataPUTComment.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchDeleteCommentAsync(int commentId) {
        Call<Comment> call =
                mAPIServiceComment.deleteCommentWithId(commentId,
                        NetworkParams.deleteCommentOfProduct());

        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment items = response.body();

                mLiveDataDeleteComment.postValue(items);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCouponsAsync() {
        Call<List<Coupons>> call =
                mAPIServiceCoupons.coupons(NetworkParams.getMainAddress());

        call.enqueue(new Callback<List<Coupons>>() {
            @Override
            public void onResponse(Call<List<Coupons>> call, Response<List<Coupons>> response) {
                List<Coupons> items = response.body();

                mLiveDataCoupons.postValue(items);
            }

            @Override
            public void onFailure(Call<List<Coupons>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

}