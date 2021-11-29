package com.example.myapplication4233424;

import java.util.ArrayList;
import java.util.Date;

public class PostInfo {
    private String title;
    private ArrayList<String> Contents;
    private String publisher;
    private Date createdAt;
    private String id;

    public PostInfo(String title, ArrayList<String> Contents, String publisher, Date createdAt, String id) {
        this.title = title;
        this.Contents = Contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }

    public PostInfo(String title, ArrayList<String> Contents, String publisher, Date createdAt) {
        this.title = title;
        this.Contents = Contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
    }

    public String gettitle() {
        return this.title;
    }
    public void settitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getContents() {
        return this.Contents;
    }
    public void setContents(ArrayList<String> Contents) {
        this.Contents = Contents;
    }

    public String getpublisher() {
        return this.publisher;
    }
    public void setpublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getcreatedAt() {
        return this.createdAt;
    }
    public void setcreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getid() {
        return this.id;
    }
    public void setid(String id) {
        this.id = id;
    }

}
