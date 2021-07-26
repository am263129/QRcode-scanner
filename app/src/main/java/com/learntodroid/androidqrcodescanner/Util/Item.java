package com.learntodroid.androidqrcodescanner.Util;

public class Item {
    public String ID;
    public String Name;
    public String ImgUrl;
    public String Desc;
    public String CatId;
    public String Price;
    public String ResName;
    public String ResImgUrl;
    public String Views;

    public Item(String id, String name, String imgUrl,String desc, String catId,String price,String resImgUrl, String views){
        this.ID = id;
        this.Name = name;
        this.ImgUrl = imgUrl;
        this.Desc = desc;
        this.CatId = catId;
        this.Price = price;
        this.ResImgUrl = resImgUrl;
        this.Views = views;
    }

    public String getCatId() {
        return CatId;
    }

    public String getDesc() {
        return Desc;
    }

    public String getID() {
        return ID;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getResImgUrl() {
        return ResImgUrl;
    }

    public String getResName() {
        return ResName;
    }

    public String getViews() {
        return Views;
    }
}
