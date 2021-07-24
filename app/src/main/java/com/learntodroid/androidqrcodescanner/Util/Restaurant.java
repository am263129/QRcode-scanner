package com.learntodroid.androidqrcodescanner.Util;

public class Restaurant {
    public String name;
    public String des;
    public String imgpath;
    public String number;
    public String address;

    public Restaurant(String name, String des, String imgpath, String number, String address){
        this.name = name;
        this.des = des;
        this.imgpath = imgpath;
        this.number = number;
        this.address = address;
    }

    public String getDes() {
        return des;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImgpath() {
        return imgpath;
    }

    public String getNumber() {
        return number;
    }


}
