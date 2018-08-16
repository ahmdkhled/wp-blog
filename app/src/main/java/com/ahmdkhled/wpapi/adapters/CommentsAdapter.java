package com.ahmdkhled.wpapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.wpapi.R;
import com.ahmdkhled.wpapi.model.Comment;
import com.ahmdkhled.wpapi.network.CommentsParser;
import com.ahmdkhled.wpapi.network.RetrofitClient;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmed Khaled on 7/31/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<Comment>comments;
    private static final int COMMENT_VIEW_TYPE=1;
    private static final int NAVIGATION_VIEW_TYPE=2;
    private OnNavigationButtonsClickListener onNavigationButtonsClickListener;


    public CommentsAdapter(Context context, ArrayList<Comment> comments, OnNavigationButtonsClickListener onNavigationButtonsClickListener) {
        this.context = context;
        this.comments = comments;
        this.onNavigationButtonsClickListener = onNavigationButtonsClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==COMMENT_VIEW_TYPE){
            View row= LayoutInflater.from(context).inflate(R.layout.comment_row,parent,false);
            return new CommentHolder(row);
        }else {
            View row= LayoutInflater.from(context).inflate(R.layout.navigation_view,parent,false);
            return new NavigationHolder(row);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==COMMENT_VIEW_TYPE){
            CommentHolder commentHolder= (CommentHolder) holder;
            commentHolder.populateData(position);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==comments.size()){
            return NAVIGATION_VIEW_TYPE;
        }else {
            return COMMENT_VIEW_TYPE;
        }
    }

    class CommentHolder extends RecyclerView.ViewHolder{
        TextView content,authorName;
        ImageView authorAvatar;
        RecyclerView repliesReycler;
        public CommentHolder(View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.commentContent);
            authorName=itemView.findViewById(R.id.commentAuthor);
            authorAvatar=itemView.findViewById(R.id.commentAuthorAvatar);
            repliesReycler=itemView.findViewById(R.id.repliesRecycler);
        }

        void populateData(int pos){
            Spanned commentContent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                commentContent = Html.fromHtml(comments.get(pos).getContent(), Html.FROM_HTML_MODE_LEGACY);
            } else {
                commentContent = Html.fromHtml(comments.get(pos).getContent());
            }
            content.setText(commentContent);
            authorName.setText(comments.get(pos).getAuthor().getName());

            if (!TextUtils.isEmpty(comments.get(pos).getAuthor().getAvatar_url())) {
                Glide.with(context).load(comments.get(pos).getAuthor().getAvatar_url())
                        .into(authorAvatar);
            }

            loadReplies(comments.get(pos).getId());
        }



        void loadReplies(int parent){
            RetrofitClient.getApiService().getReplies(1,parent).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    try {
                        String result =response.body().string();
                        ArrayList<Comment> replies= CommentsParser.parse(result);
                        comments.get(getAdapterPosition()).setReplies(replies);
                        RepliesAdapter repliesAdapter=new RepliesAdapter(context,replies);
                        repliesReycler.setAdapter(repliesAdapter);
                        repliesReycler.setLayoutManager(new LinearLayoutManager(context));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                }
            });
        }

    }

    class NavigationHolder extends RecyclerView.ViewHolder{
        ImageView nextPage,lastPage;
        NavigationHolder(View itemView) {
            super(itemView);
            nextPage=itemView.findViewById(R.id.nextPage);
            lastPage=itemView.findViewById(R.id.lastPage);

            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNavigationButtonsClickListener.onNextButtonClickListener();
                }
            });
            lastPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNavigationButtonsClickListener.onLastButtonClickListener();
                }
            });
        }
    }
    public interface OnNavigationButtonsClickListener{
        void onNextButtonClickListener();
        void onLastButtonClickListener();
    }

}
