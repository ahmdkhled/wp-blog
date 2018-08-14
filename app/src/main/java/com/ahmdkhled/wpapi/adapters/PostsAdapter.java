package com.ahmdkhled.wpapi.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmdkhled.wpapi.activities.PostActivity;
import com.ahmdkhled.wpapi.R;
import com.ahmdkhled.wpapi.model.Post;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/26/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Post> postsList;

    private static final int POST_VIEW_TYPE=1;
    private static final int NAVIGATION_VIEW_TYPE=2;
    OnNavigationButtonsClickListener onNavigationButtonsClickListener;

    public PostsAdapter(Context context, ArrayList<Post> postsList, OnNavigationButtonsClickListener onNavigationButtonsClickListener) {
        this.context = context;
        this.postsList = postsList;
        this.onNavigationButtonsClickListener = onNavigationButtonsClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==POST_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.post_row,parent,false);
            return new PostHolder(view);
        }else if (viewType==NAVIGATION_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.navigation_view,parent,false);
            return new NavigationHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==POST_VIEW_TYPE) {
            PostHolder postHolder=(PostHolder)holder;
            postHolder.title.setText(postsList.get(position).getTitle());
            Spanned content;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                content = Html.fromHtml(postsList.get(position).getRenderedContent(), Html.FROM_HTML_MODE_LEGACY);
            } else {
                content = Html.fromHtml(postsList.get(position).getRenderedContent());
            }
            postHolder.content.setText(content);
            String imgUrl = postsList.get(position).getImage();
            if (TextUtils.isEmpty(imgUrl)) {
                postHolder.img.setImageResource(R.drawable.empty);
            } else {
                    Glide.with(context).load(imgUrl).into(postHolder.img);
            }
        }
    }

    @Override
    public int getItemCount() {
        return postsList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==postsList.size()){
            return NAVIGATION_VIEW_TYPE;
        }else {
            return POST_VIEW_TYPE;
        }
    }

    class PostHolder extends RecyclerView.ViewHolder{
        TextView title,content;
        ImageView img;
        PostHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.postTitle_TV);
            content=itemView.findViewById(R.id.postContent_TV);
            img=itemView.findViewById(R.id.postImage_IV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PostActivity.class);
                    intent.putExtra(PostActivity.POST_KEY,postsList.get(getAdapterPosition()));
                    context.startActivity(intent);
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
