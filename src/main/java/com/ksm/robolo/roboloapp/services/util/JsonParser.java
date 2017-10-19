package com.ksm.robolo.roboloapp.services.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
//TODO we don't need it for now, remove it
@Component
public class JsonParser {

    private Gson gson;

    public JsonParser() {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    public <T> String toJson(T input){
        return gson.toJson(input);
    }

    public <T> T fromJson(String input, Type type){
        return gson.fromJson(input, type);
    }

}
