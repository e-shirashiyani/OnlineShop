package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.SalesReport;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;

public class GetSalesDeserializer implements JsonDeserializer<SalesReport> {


    @Override
    public SalesReport deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        SalesReport salesReport;

        JsonArray bodyArray = json.getAsJsonArray();
        JsonObject jsonObject = bodyArray.get(0).getAsJsonObject();
        int total_items = jsonObject.get("total_items").getAsInt();

        salesReport = new SalesReport(total_items);

        return salesReport;
    }

}
