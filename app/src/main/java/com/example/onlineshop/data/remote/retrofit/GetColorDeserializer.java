package com.example.onlineshop.data.remote.retrofit;

import com.example.onlineshop.data.model.ColorAttribute;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetColorDeserializer implements JsonDeserializer<List<ColorAttribute>> {


    @Override
    public List<ColorAttribute> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        List<ColorAttribute> colorAttributes = new ArrayList<>();

        JsonArray bodyArray = json.getAsJsonArray();
        for (int i = 0; i < bodyArray.size(); i++) {
            JsonObject colorObject = bodyArray.get(i).getAsJsonObject();
            int id = colorObject.get("id").getAsInt();
            String name = colorObject.get("name").getAsString();
            String description = colorObject.get("description").getAsString();
            int count = colorObject.get("count").getAsInt();


            ColorAttribute item = new ColorAttribute(id, name, description, count);
            colorAttributes.add(item);
        }

        return colorAttributes;
    }

}
