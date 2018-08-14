package com.ahmdkhled.wpapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmdkhled.wpapi.R;
import com.ahmdkhled.wpapi.model.Comment;

import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/31/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder>{

    private Context context;
    private ArrayList<Comment>comments;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(context).inflate(R.layout.comment_row,parent,false);
        return new CommentHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.populateData(position);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder{
        TextView content,authorName;
        public CommentHolder(View itemView) {
            super(itemView);
            content=itemView.findViewById(R.id.commentContent);
            authorName=itemView.findViewById(R.id.commentAuthor);
        }

        void populateData(int pos){
            Spanned commentContent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                commentContent = Html.fromHtml(comments.get(pos).getContent(), Html.FROM_HTML_MODE_LEGACY);
            } else {
                commentContent = Html.fromHtml(comments.get(pos).getContent());
            }

            content.setText(commentContent);
            authorName.setText(comments.get(pos).getAuthorName());
        }
    }
}
