package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.Coupons;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetCouponsDeserializer implements JsonDeserializer<List<Coupons>> {


@Override
public List<Coupons> deserialize(
        JsonElement json,
        Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
        List<Coupons> items = new ArrayList<>();

        JsonArray bodyArray = json.getAsJsonArray();
        for (int i = 0; i < bodyArray.size(); i++) {
        JsonObject DiscountCodeObject = bodyArray.get(i).getAsJsonObject();
        int id = DiscountCodeObject.get("id").getAsInt();
        String code = DiscountCodeObject.get("code").getAsString();
        String amount = DiscountCodeObject.get("amount").getAsString();


        Coupons item = new Coupons(id,code,amount);
        items.add(item);
        }

        return items;
        }
}
