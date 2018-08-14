package com.ahmdkhled.wpapi.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ahmdkhled.wpapi.model.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmed Khaled on 7/26/2018.
 */

public class PostsParser {

    public static   ArrayList<Post> parse(String JsonString){
         ArrayList<Post> posts=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(JsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObj=jsonArray.optJSONObject(i);
                JSONObject titleObj=currentObj.optJSONObject("title");
                String title=titleObj.optString("rendered");
                int id=currentObj.optInt("id");
                int mediaId=currentObj.optInt("featured_media");
                JSONObject contentObj=currentObj.optJSONObject("content");
                String content=contentObj.optString("rendered");
                JSONObject excerptObj=currentObj.optJSONObject("excerpt");
                String renderdContent=excerptObj.optString("rendered").replaceAll("[<p></p>]", "");
                String date=currentObj.optString("date");
                int authorId=currentObj.optInt("author");
                JSONArray categoriesArr=currentObj.optJSONArray("categories");
                ArrayList<Integer> categories=new ArrayList<>();
                for (int j = 0; j < categoriesArr.length(); j++) {
                    categories.add(categoriesArr.optInt(j));
                }

                JSONObject embeddedObj=currentObj.optJSONObject("_embedded");
                String imageUrl="";
                if (embeddedObj.has("wp:featuredmedia")){
                    JSONArray featuredmediaArr=embeddedObj.optJSONArray("wp:featuredmedia");
                    JSONObject firstObj=featuredmediaArr.optJSONObject(0);
                    imageUrl=firstObj.optString("source_url");
                }
                final Post post=new Post(id,title,mediaId,imageUrl,content,renderdContent,date,authorId,categories);
                posts.add(post);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return posts;
    }


}
