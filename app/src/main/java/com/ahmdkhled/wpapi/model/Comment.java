package com.ahmdkhled.wpapi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed Khaled on 7/31/2018.
 */

public class Comment implements Parcelable{
    int id;
    String date;
    String content;
    String status;
    String authorName;


    public Comment(int id, String date, String content, String status, String authorName) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.status = status;
        this.authorName = authorName;
    }
    public Comment(){}

    protected Comment(Parcel in) {
        id = in.readInt();
        date = in.readString();
        content = in.readString();
        status = in.readString();
        authorName = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthorName() {
        return authorName;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(date);
        parcel.writeString(content);
        parcel.writeString(status);
        parcel.writeString(authorName);
    }
}
