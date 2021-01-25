package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.ProductCategory;
import com.example.onlineshop.data.remote.NetworkParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceCoupons {
    private static RetrofitInstanceCoupons sInstance;
    private Retrofit mRetrofit;

    public static RetrofitInstanceCoupons getInstance(){
        if(sInstance==null)
            sInstance=new RetrofitInstanceCoupons();
        return sInstance;
    }
    private static Converter.Factory createGsonConverter() {
        Type type = new TypeToken<List<ProductCategory>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, new GetCouponsDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private RetrofitInstanceCoupons() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetworkParams.BASE_URL)
                .addConverterFactory(createGsonConverter())
                .build();
    }

}
