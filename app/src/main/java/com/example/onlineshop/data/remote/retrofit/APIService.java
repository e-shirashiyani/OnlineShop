package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.ColorAttribute;
import com.example.onlineshop.data.model.Comment;
import com.example.onlineshop.data.model.Coupons;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.model.ProductCategory;
import com.example.onlineshop.data.model.SalesReport;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIService {
    @GET("products")
    Call<List<Product>> products(@QueryMap Map<String, String> options);

    @GET("products/categories")
    Call<List<ProductCategory>> categories(@QueryMap Map<String, String> options);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id,@QueryMap Map<String, String> options);

    @GET("reports/sales/top_sellers")
    Call<List<Product>> topSellers(@QueryMap Map<String, String> options);

    @GET("reports/sales")
    Call<List<SalesReport>> sales(@QueryMap Map<String, String> options);

    @GET("products/attributes/3/terms")
    Call<List<ColorAttribute>> colors(@QueryMap Map<String, String> options);

    @GET("products/reviews")
    Call<List<Comment>> comments(@QueryMap Map<String, String> options);

    @GET("products/reviews/{id}")
    Call<Comment> getCommentWithId(@Path("id") int id,@QueryMap Map<String, String> options);


    @PUT("products/reviews/{id}")
    @Headers({ "Content-Type: application/json"})
    Call<Comment> putCommentWithId(@Body Comment comment,@Path("id") int id,@QueryMap Map<String, String> options);

    @DELETE("products/reviews/{id}")
    Call<Comment> deleteCommentWithId(@Path("id") int id,@QueryMap Map<String, String> options);


    @FormUrlEncoded
    @POST("customers")
    Call<Customer> customer(@Body Customer customer,@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("products/reviews")
    Call<Comment> addComment(@Field("product_id") int product_id,@Field("review") String review,
                             @Field("reviewer") String reviewer,@Field("reviewer_email") String reviewer_email,
                             @Field("rating") int rating,@QueryMap Map<String, String> options);

    @GET("coupons")
    Call<List<Coupons>> coupons(@QueryMap Map<String, String> options);


}

