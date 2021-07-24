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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.learntodroid.androidqrcodescanner.R;
import com.learntodroid.androidqrcodescanner.Util.Item;
import com.learntodroid.androidqrcodescanner.Util.ItemAdapter;
import com.learntodroid.androidqrcodescanner.Util.Util;

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
    TextView catefory;
    int category_id;
    ItemAdapter adapter;
    public static CategoryActivity self;
    String resname;

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
        self = this;
        itemList = findViewById(R.id.item_list);
        catefory = findViewById(R.id.category);
        catefory.setText(getResources().getStringArray(R.array.category)[0]);
        category_id = 99;
        spinnerDialog = new SpinnerDialog(this, new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(R.array.category))),
                "Select or Search City");
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
                String[] cat_list = getResources().getStringArray(R.array.category);
                for(int i = 0; i<cat_list.length;i++){
                    if(cat_list[i].equals(item)) {
                        category_id = i  + 1;
                        break;
                    }
                }
                catefory.setText(item);
//                Toast.makeText(CategoryActivity.this,String.valueOf(category_id),Toast.LENGTH_SHORT).show();
                updateAdapter();
            }
        });


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
                byte[] buffer = new byte[512];
                int read;
                while((read = inputStream.read(buffer))>0){
                    response += new String(buffer);
                }
                JSONObject baseResponse = new JSONObject(response);
                JSONArray items = baseResponse.getJSONArray("records");
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
                Toast.makeText(CategoryActivity.this,"Connected " + currentitems.size() + "items found",Toast.LENGTH_SHORT).show();
                currentitems = Util.itemList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeoutHandler.removeMessages(0);
                        adapter = new ItemAdapter(currentitems);
                        itemList.setAdapter(adapter);
                        itemList.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
                    }
                });

            }catch (Exception E){
                Toast.makeText(CategoryActivity.this,"Failed" + E.toString(),Toast.LENGTH_SHORT).show();
                E.printStackTrace();
            }


        }
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