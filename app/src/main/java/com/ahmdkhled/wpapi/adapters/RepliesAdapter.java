package com.ahmdkhled.wpapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ahmed Khaled on 8/14/2018.
 */

public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.ReplyHolder> {

    private Context context;
    private ArrayList<Comment> replies;

    public RepliesAdapter(Context context, ArrayList<Comment> replies) {
        this.context = context;
        this.replies = replies;
    }

    @NonNull
    @Override
    public ReplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_row,parent,false);
        return new ReplyHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyHolder holder, int position) {
        holder.populateData(position);
    }

    @Override
    public int getItemCount() {
        if (replies==null)
            return 0;
        return replies.size();
    }

    class ReplyHolder extends RecyclerView.ViewHolder{
        TextView replyContent,replyAuthor;
        CircleImageView authorAvatar;
        ReplyHolder(View itemView) {
            super(itemView);
            replyContent=itemView.findViewById(R.id.replyContent);
            replyAuthor=itemView.findViewById(R.id.replyAuthor);
            authorAvatar=itemView.findViewById(R.id.replyAuthorAvatar);
        }

        void populateData(int pos) {
            if (replies.get(getAdapterPosition()) != null) {
                Spanned commentContent;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    commentContent = Html.fromHtml(replies.get(pos).getContent(), Html.FROM_HTML_MODE_LEGACY);
                } else {
                    commentContent = Html.fromHtml(replies.get(pos).getContent());
                }
                replyContent.setText(commentContent);
                replyAuthor.setText(replies.get(pos).getAuthor().getName());
                if (!TextUtils.isEmpty(replies.get(pos).getAuthor().getAvatar_url())) {
                    Glide.with(context).load(replies.get(pos).getAuthor().getAvatar_url())
                            .into(authorAvatar);
                }

            }
        }
    }
}
