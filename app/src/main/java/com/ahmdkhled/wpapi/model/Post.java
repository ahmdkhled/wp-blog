package com.ahmdkhled.wpapi.model;

import android.content.Loader;
import android.os.Parcel;
import android.os.Parcelable;

import com.ahmdkhled.wpapi.activities.PostActivity;

import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/26/2018.
 */

public class Post  implements Parcelable{

    private int id;
    private String title;
    private int mediaId;
    private String image;
    private String content;
    private String renderedContent;
    private String Date;
    private int authorId;
    private ArrayList<Integer> categories;

    public Post(int id, String title, int mediaId, String image, String content, String renderedContent, String date, int authorId, ArrayList<Integer> categories) {
        this.id = id;
        this.title = title;
        this.mediaId = mediaId;
        this.image = image;
        this.content = content;
        this.renderedContent = renderedContent;
        Date = date;
        this.authorId = authorId;
        this.categories = categories;
    }


    public Post(){}

    protected Post(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        content = in.readString();
        renderedContent = in.readString();
        Date = in.readString();
        authorId = in.readInt();
        mediaId = in.readInt();
        categories=in.readArrayList(Post.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public int getId() {
        return id;}

    public String getTitle() {
        return title;}

    public String getImage() {
        return image;}

    public String getContent() {
        return content;}

    public String getRenderedContent() {
        return renderedContent;}

    public String getDate() {
        return Date;}

    public int getAuthorId() {
        return authorId;}

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public void setImage(String image) {
        this.image = image;}

    public ArrayList<Integer> getCategories() {
        return categories;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(content);
        parcel.writeString(renderedContent);
        parcel.writeString(Date);
        parcel.writeInt(authorId);
        parcel.writeInt(mediaId);
        parcel.writeList(categories);
    }
}
