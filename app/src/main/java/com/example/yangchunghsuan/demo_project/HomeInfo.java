package com.example.yangchunghsuan.demo_project;

import android.graphics.Bitmap;

public class HomeInfo {
    private String id;
    private String address;
    private Bitmap bitmap;
    private String comment;



    public HomeInfo(String id,String address,String comment,Bitmap bitmap){
        this.id = id;
        this.bitmap = bitmap;
        this.address = address;
        this.comment = comment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getAddress() {
        return address;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
