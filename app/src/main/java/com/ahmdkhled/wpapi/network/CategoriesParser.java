package com.ahmdkhled.wpapi.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/28/2018.
 */

public class CategoriesParser  {

    public static ArrayList<String> parse(String jsonString){
        ArrayList<String> categories=new ArrayList<>();
        try {
            JSONArray categoryArr=new JSONArray(jsonString);
            for (int i = 0; i < categoryArr.length(); i++) {
                JSONObject currentObject=categoryArr.optJSONObject(i);
                categories.add(currentObject.optString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
