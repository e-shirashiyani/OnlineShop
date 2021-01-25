package com.example.onlineshop.data.remote;

import android.net.Uri;

import com.example.onlineshop.data.model.Images;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.PRODUCT;

public class NetworkParams {

    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String CONSUMER_KEY = "ck_671aacd21cdac7c8fafc30c6fa0bf09eba15b074";
    public static final String CONSUMER_SECRET = "cs_75d98b6177540214b3fb44fee036cf935f025616";
    public static final String CREATED_AT = "created_at";
    public static final String DESC = "desc";
    public static final String  PARENT_CATEGORY = "0";
    public static final String PARENT_OF_CATEGORY = "parent";
    public static final String ORDER = "order";
    public static final String CATEGORY = "category";
    public static final String SEARCH = "search";
    public static final String ORDERBY = "order_by";
    public static final String PAGE = "page";
    public static final String PRODUCT = "product";
    public static final String FORCE = "force";
    public static final String TRUE = "true";


    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("consumer_key", CONSUMER_KEY);
        put("consumer_secret", CONSUMER_SECRET);
    }};

    public static Map<String, String> getMostVisitedProducts() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put("orderby", "popularity");
        products.put("order", "desc");

        return products;
    }

    public static Map<String, String> getHighestScoreProducts() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put("orderby", "rating");
        products.put("order", "desc");

        return products;
    }

    public static Map<String, String> getLatestProducts() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(ORDERBY, CREATED_AT);
        products.put(ORDER, DESC);

        return products;
    }

    public static Map<String, String> getProducts(String page) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PAGE, page);

        return products;
    }

    public static Map<String, String> getProductsWithParentId(String parentId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(CATEGORY,parentId);

        return products;
    }

    public static Map<String, String> getSpecialProducts(String parentId,String page) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(CATEGORY,parentId);
        products.put(PAGE,page);

        return products;
    }

    public static Map<String, String> getCategories() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PARENT_OF_CATEGORY, PARENT_CATEGORY);

        return products;
    }

    public static Map<String, String> subCategories(String parentId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PARENT_OF_CATEGORY, parentId);

        return products;
    }

    public static Map<String, String> getSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);

        return products;
    }

    public static Map<String, String> getFilterSearchProducts(String query,String colorId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("attribute", "pa_color");
        products.put("attribute_term", colorId);

        return products;
    }

    public static Map<String, String> getSortedLowToHighSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("orderby", "price");
        products.put("order", "asc");

        return products;
    }

    public static Map<String, String> getSortedHighToLowSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("orderby", "price");
        products.put("order", "desc");

        return products;
    }

    public static Map<String, String> getSortedTotalSalesSearchProducts(String query) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(SEARCH, query);
        products.put("orderby", "slug");
        products.put("order", "desc");

        return products;
    }

    public static Map<String, String> getCommentOfProduct(String productId) {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(PRODUCT, productId);

        return products;
    }


    public static Map<String, String> getMainAddress() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);

        return products;
    }

    public static Map<String, String> deleteCommentOfProduct() {
        Map<String, String> products = new HashMap<>();
        products.putAll(BASE_OPTIONS);
        products.put(FORCE, TRUE);

        return products;
    }
}
