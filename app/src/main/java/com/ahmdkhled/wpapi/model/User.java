package com.ahmdkhled.wpapi.model;

/**
 * Created by Ahmed Khaled on 8/14/2018.
 */

public class User {

    private String id;
    private String name;
    private String userName;
    private String avatar_url;

    public User(String id, String name, String userName, String avatar_url) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.avatar_url = avatar_url;
    }

    public User( String name, String avatar_url) {
        this.name = name;
        this.avatar_url = avatar_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
