package com.learntodroid.androidqrcodescanner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.learntodroid.androidqrcodescanner.R;
import com.learntodroid.androidqrcodescanner.Util.Item;
import com.learntodroid.androidqrcodescanner.Util.Util;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Item Currentitem;
    ImageView pic,res_pic;
    TextView name,res_name, desc, price,views,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pic = findViewById(R.id.pic);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
//        views =
        String id = getIntent().getStringExtra("id");

        for(Item item: Util.itemList){
            if(item.getID().equals(id)){
                Currentitem = item;
            }
        }

        initView();
    }

    public void initView(){
        Picasso.get().load("http://"+Util.URL+"/uploads/"+Currentitem.getImgUrl()).into(pic);
        name.setText(Currentitem.getName());
        price.setText(Currentitem.getPrice());
        desc.setText(Currentitem.getDesc());

    }
}