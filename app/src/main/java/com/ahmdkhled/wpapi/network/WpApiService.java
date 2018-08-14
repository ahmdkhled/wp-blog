package com.ahmdkhled.wpapi.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ahmed Khaled on 7/26/2018.
 */

public interface WpApiService {

    @GET("posts")
    Call<ResponseBody> getPosts(@Query("_embed") int _embed,@Query("page") int page);


    @GET("categories")
    Call<ResponseBody> getCategory(@Query("include") String ids);

    @GET("comments")
    Call<ResponseBody> getComments(@Query("post") int postId,@Query("page") int page,@Query("parent") int parent);

    @GET("comments")
    Call<ResponseBody> getReplies(@Query("page") int page,@Query("parent") int parent);
}
