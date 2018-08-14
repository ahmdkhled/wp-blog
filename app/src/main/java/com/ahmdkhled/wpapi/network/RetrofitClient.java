package com.ahmdkhled.wpapi.network;

import retrofit2.Retrofit;

/**
 * Created by Ahmed Khaled on 7/26/2018.
 */

public class RetrofitClient {

    //private static String BaseUrl="https://ahmdkhaled.000webhostapp.com/wp-json/wp/v2/";
    private static String BaseUrl="http://androidhive.info/wp-json/wp/v2/";
    //private static String BaseUrl="http://www.hendiware.com/wp-json/wp/v2/";

    private static Retrofit retrofit;
    private static WpApiService wpApiService;

    public static Retrofit getInstance(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BaseUrl).build();
        }
        return retrofit;
    }

    public static WpApiService getApiService(){
        if (wpApiService==null){
        wpApiService=getInstance().create(WpApiService.class);
        }
        return wpApiService;
    }
}
