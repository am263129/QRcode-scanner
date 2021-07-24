package com.learntodroid.androidqrcodescanner.Util;

public class Category {
    public String id;
    public String name;
    public String des;

    public Category(String id, String name, String des){
        this.id = id;
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getId() {
        return id;
    }
}
