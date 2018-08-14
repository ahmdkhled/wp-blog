package com.ahmdkhled.wpapi.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.ahmdkhled.wpapi.R;
import com.ahmdkhled.wpapi.adapters.PostsAdapter;
import com.ahmdkhled.wpapi.model.Post;
import com.ahmdkhled.wpapi.network.PostsParser;
import com.ahmdkhled.wpapi.network.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
       PostsAdapter.OnNavigationButtonsClickListener{

    RecyclerView recyclerView;
    PostsAdapter postsAdapter;
    int currentPage=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.postsRecyclerView);
        loadPosts(currentPage);
    }



    void loadPosts(int page){
        RetrofitClient.getApiService().getPosts(0,page).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.d("TAG", result);
                        ArrayList<Post> posts = PostsParser.parse(result);
                        populatePosts(posts);
                    } catch (IOException e) {
                        Log.d("TAG", e.getMessage());
                        e.printStackTrace();
                    }
                }else{
                    currentPage--;
                    if (response.code()==400){
                        Toast.makeText(getApplicationContext(),"this is the last page",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"failed to load posts ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void populatePosts(ArrayList<Post> posts){
        postsAdapter=new PostsAdapter(this,posts,this);
        recyclerView.setAdapter(postsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    public void onNextButtonClickListener() {
        currentPage++;
        loadPosts(currentPage);
    }

    @Override
    public void onLastButtonClickListener() {
        if (currentPage==1){
            Toast.makeText(getApplicationContext(),"this is the first page",Toast.LENGTH_SHORT).show();
        }else {
            currentPage--;
            loadPosts(currentPage);
        }
    }
}
