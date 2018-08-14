package com.ahmdkhled.wpapi.network;

import com.ahmdkhled.wpapi.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/31/2018.
 */

public class CommentsParser {

    public static ArrayList<Comment> parse(String jsonString){
        ArrayList<Comment> comments=new ArrayList<>();

        try {
            JSONArray mainArr=new JSONArray(jsonString);
            for (int i = 0; i < mainArr.length(); i++) {
                JSONObject currentObj=mainArr.optJSONObject(i);
                int id=currentObj.optInt("id");
                String date=currentObj.optString("date");
                String status=currentObj.optString("status");
                JSONObject contentObj=currentObj.optJSONObject("content");
                String content=contentObj.optString("rendered").replaceAll("[<p></p>]", "");
                String authorName=currentObj.optString("author_name");
                Comment comment=new Comment(id,date,content,status,authorName);
                comments.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comments;
    }




    public static int getCommentsCount(String jsonString){
        try {
            JSONArray mainArr=new JSONArray(jsonString);
            return mainArr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
