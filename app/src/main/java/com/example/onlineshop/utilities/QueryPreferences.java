package com.example.onlineshop.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_FILTER_COLOR = "filterColor";
    private static final String PREF_FILTER_PRODUCT_ID = "filterProductId";
    private static final String PREF_NUMBER_OF_PRODUCT = "lastId";
    private static final String PREF_NOTIFICATION_TIME = "notificationTime";

    public static String getSearchQuery(Context context) {
        return getSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setSearchQuery(Context context, String query) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getFilterColor(Context context) {
        return getSharedPreferences(context).getString(PREF_FILTER_COLOR, null);
    }

    public static void setFilterColor(Context context, String color) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_FILTER_COLOR, color)
                .apply();
    }

    public static String getFilterProductId(Context context) {
        return getSharedPreferences(context).getString(PREF_FILTER_PRODUCT_ID,null);
    }

    public static void setFilterProductId(Context context, String productId) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_FILTER_PRODUCT_ID, productId)
                .apply();
    }

    public static String getNumberOfProduct(Context context) {
        return getSharedPreferences(context).getString(PREF_NUMBER_OF_PRODUCT, null);
    }

    public static void setNumberOfProduct(Context context, String numberOfProduct) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_NUMBER_OF_PRODUCT, numberOfProduct)
                .apply();
    }

    public static long getNotificationTime(Context context) {
        return getSharedPreferences(context).getLong(PREF_NOTIFICATION_TIME,0);
    }

    public static void setNotificationTime(Context context, long notificationTime) {
        getSharedPreferences(context)
                .edit()
                .putLong(PREF_NOTIFICATION_TIME, notificationTime)
                .apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
