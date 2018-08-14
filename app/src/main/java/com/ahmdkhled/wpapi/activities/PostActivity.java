package com.ahmdkhled.wpapi.activities;

import android.content.Loader;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmdkhled.wpapi.R;
import com.ahmdkhled.wpapi.adapters.CategoriesAdapter;
import com.ahmdkhled.wpapi.adapters.CommentsAdapter;
import com.ahmdkhled.wpapi.model.Comment;
import com.ahmdkhled.wpapi.model.Post;
import com.ahmdkhled.wpapi.network.CategoriesParser;
import com.ahmdkhled.wpapi.network.CommentsParser;
import com.ahmdkhled.wpapi.network.RetrofitClient;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {
    public static final String POST_KEY="post_key";
    Post post;
    ImageView thumnail;
    TextView title,commentsCount;
    int mediaId;
    RecyclerView categoriesRecyler;
    RecyclerView commentsRecyler;
    DividerItemDecoration dividerDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        WebView webView=findViewById(R.id.webview);
        thumnail=findViewById(R.id.postThumbnail);
        title=findViewById(R.id.title);
        commentsCount=findViewById(R.id.commentsCount);
        categoriesRecyler=findViewById(R.id.categriesRecycler);
        commentsRecyler=findViewById(R.id.commentsRecycler);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dividerDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        if (getIntent()!=null&&getIntent().hasExtra(POST_KEY)){
             post=getIntent().getParcelableExtra(POST_KEY);
        }

        mediaId=post.getMediaId();
        String content=post.getContent();
        //webView.loadData(content,"text/html; charset=utf-8","UTF-8");
        webView.loadDataWithBaseURL(null,content,"text/html; charset=utf-8","UTF-8",null);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        if (!TextUtils.isEmpty(post.getImage())){
                Glide.with(this).load(post.getImage()).into(thumnail);
        }
        title.setText(post.getTitle());
        loadCategories();
        loadComments(post.getId());

        Log.d("TAGG",appendCategoriesIds(post.getCategories()));

    }


    void loadComments(int postId){
        RetrofitClient.getApiService().getComments(postId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String result =response.body().string();
                    ArrayList<Comment> comments= CommentsParser.parse(result);
                    int count=CommentsParser.getCommentsCount(result);
                    populateComments(comments,count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }

    private void populateComments(ArrayList<Comment> comments,int count) {
        CommentsAdapter commentsAdapter=new CommentsAdapter(this,comments);
        commentsRecyler.setAdapter(commentsAdapter);
        commentsRecyler.removeItemDecoration(dividerDecoration);
        commentsRecyler.addItemDecoration(dividerDecoration);
        commentsRecyler.setLayoutManager(new LinearLayoutManager(this));
        commentsCount.setText(count+" comments");
    }

    void loadCategories(){
        RetrofitClient.getApiService().getCategory(appendCategoriesIds(post.getCategories()))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        try {
                            String result=response.body().string();
                            ArrayList<String> categories= CategoriesParser.parse(result);
                            populateCategories(categories);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                    }
                });

    }

    void populateCategories(ArrayList<String> categories){
        CategoriesAdapter categoriesAdapter=new CategoriesAdapter(categories);
        categoriesRecyler.setAdapter(categoriesAdapter);
        categoriesRecyler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
    }

    String appendCategoriesIds(ArrayList<Integer> categories){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < categories.size(); i++) {
            if (i==categories.size()-1){
                sb.append(categories.get(i));
            }else{
                sb.append(categories.get(i)).append(",");
            }
        }
        return sb.toString();
    }

}
