package com.ahmdkhled.wpapi.model;

import java.util.ArrayList;

/**
 * Created by Ahmed Khaled on 7/31/2018.
 */

public class Comment {
    private int id;
    private String date;
    private String content;
    private String status;
    private User author;
    private int parent;
    private ArrayList<Comment> replies;

    public Comment(int id, String date, String content, String status, User author, int parent) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.status = status;
        this.author = author;
        this.parent = parent;
    }

    public Comment(){}

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

    public User getAuthor() {
        return author;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public ArrayList<Comment> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Comment> replies) {
        this.replies = replies;
    }
}
