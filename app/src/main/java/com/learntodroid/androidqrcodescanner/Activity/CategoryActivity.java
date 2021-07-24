package com.learntodroid.androidqrcodescanner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.learntodroid.androidqrcodescanner.R;
import com.learntodroid.androidqrcodescanner.Util.Category;
import com.learntodroid.androidqrcodescanner.Util.Item;
import com.learntodroid.androidqrcodescanner.Util.ItemAdapter;
import com.learntodroid.androidqrcodescanner.Util.Restaurant;
import com.learntodroid.androidqrcodescanner.Util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView itemList;
    ArrayList<Item> currentitems = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    TextView catefory, resName, resNumber, resDesc;
    int category_id;
    ItemAdapter adapter;
    public static CategoryActivity self;
    String resname;
    ImageView resProfile;

    Handler timeoutHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(CategoryActivity.this,"Connection Failed",Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        resname = getIntent().getStringExtra("resname").replace("username:","");
//        resname = "thailandi";
        self = this;
        itemList = findViewById(R.id.item_list);
        catefory = findViewById(R.id.category);
        resName = findViewById(R.id.res_name);
        resDesc = findViewById(R.id.res_des);
        resNumber = findViewById(R.id.res_number);
        resProfile = findViewById(R.id.res_profile);
        catefory.setText(getResources().getStringArray(R.array.category)[0]);
        category_id = 99;



        timeoutHandler.sendEmptyMessageDelayed(0,5000);

        Toast.makeText(CategoryActivity.this,"Connecting",Toast.LENGTH_SHORT).show();
        new Thread(new initData()).start();
    }

    public class initData implements Runnable{

        @Override
        public void run() {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("http://"+Util.URL+"/api/product/read.php?id="+resname).openConnection();
                InputStream inputStream = connection.getInputStream();
                String response = "";
                byte[] buffer = new byte[1024];
                int read;
                while((read = inputStream.read(buffer))>0){
                    response += new String(buffer);
                }
                Log.e("result",response);
                JSONObject baseResponse = new JSONObject(response);
                JSONArray items = baseResponse.getJSONArray("records");
                JSONArray restaurant = baseResponse.getJSONArray("restaurant");
                JSONArray category = baseResponse.getJSONArray("category");
                for(int i = 0;i<items.length(); i++){
                    JSONObject item = new JSONObject(items.get(i).toString());
                    Util.itemList.add(new Item(item.getString("item_id"),
                            item.getString("name"),
                            item.getString("item_image"),
                            item.getString("description"),
                            item.getString("category_id"),
                            item.getString("price"),
                            item.getString("res_name"),
                            item.getString("res_image"),
                            item.getString("views")));

                    Log.e("item",item.getString("name"));
                }
                for(int i = 0;i<category.length(); i++){
                    JSONObject cat = new JSONObject(category.get(i).toString());
                    Util.categories.add(new Category(cat.getString("cat_id"),
                            cat.getString("cat_name"),
                            cat.getString("cat_des")));

                    Log.e("category",cat.getString("cat_id"));
                }
                for(int i = 0;i<restaurant.length(); i++){
                    JSONObject res = new JSONObject(restaurant.get(i).toString());
                    Util.currentRes = new Restaurant(res.getString("res_name"),
                            res.getString("res_des"),res.getString("res_imagepath"),
                            res.getString("res_number"),res.getString("res_address"));

                    Log.e("Restaurant",res.getString("res_name"));
                }
                currentitems = Util.itemList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UpdateUI();

                    }
                });

            }catch (Exception E){
                E.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CategoryActivity.this,"Connecting failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public void UpdateUI(){
        resName.setText(Util.currentRes.getName());
        resDesc.setText(Util.currentRes.getDes());
        resNumber.setText(Util.currentRes.getNumber());
        Picasso.get().load("http://"+Util.URL+"/uploads/"+Util.currentRes.getImgpath()).into(resProfile);
        timeoutHandler.removeMessages(0);
        adapter = new ItemAdapter(currentitems);
        itemList.setAdapter(adapter);
        itemList.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        ArrayList<String> filters = new ArrayList<>();
        for(Category category :Util.categories){
            filters.add(category.getName());
        }
        spinnerDialog = new SpinnerDialog(this, filters,
                "Select or Search Category");
        spinnerDialog.setTitleColor(getResources().getColor(R.color.colorAccent));
        spinnerDialog.setSearchIconColor(getResources().getColor(R.color.colorAccent));
        spinnerDialog.setSearchTextColor(getResources().getColor(R.color.colorAccent));
        spinnerDialog.setItemColor(getResources().getColor(R.color.colorAccent));
        spinnerDialog.setItemDividerColor(getResources().getColor(R.color.colorAccent));
        spinnerDialog.setCloseColor(getResources().getColor(R.color.colorAccent));
        spinnerDialog.setCancellable(true);
        spinnerDialog.setShowKeyboard(false);

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                for(Category category :Util.categories){
                    if(category.getName().equals(item)) {
                        category_id = Integer.parseInt(category.getId());
                        break;
                    }
                }
                catefory.setText(item);
                updateAdapter();
            }
        });
    }
    public void filter(View view){
        spinnerDialog.showSpinerDialog();
    }

    public void updateAdapter(){
        if(Util.itemList.size()>0 && adapter!=null) {
            ArrayList<Item> itemlist = new ArrayList<>();

            for (Item item : Util.itemList) {
                if (item.getCatId().equals(String.valueOf(category_id))) {
                    itemlist.add(item);
                }
            }
            currentitems = itemlist;
            adapter.update(itemlist);
        }
    }

    public static CategoryActivity getInstance(){
        return self;
    }
}