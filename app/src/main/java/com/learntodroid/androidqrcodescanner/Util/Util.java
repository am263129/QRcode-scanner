package com.learntodroid.androidqrcodescanner.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Util {
    public static ArrayList<Item> itemList = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
//    public static String URL = "192.168.114.29";
    public static String URL = "192.168.0.114/GazaRESTOO";
    public static Restaurant currentRes;
    public static boolean start = false;

    public static void saveSetting(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("usersetting",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("First",false);
        editor.commit();
    }

    public static boolean loadSetting(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("usersetting",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("First",true);
    }
}
