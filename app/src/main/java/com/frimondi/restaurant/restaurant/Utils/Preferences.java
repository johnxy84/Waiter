package com.frimondi.restaurant.restaurant.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.Models.OrderDetails;
import com.frimondi.restaurant.restaurant.Services.FrimondiClient;
import com.frimondi.restaurant.restaurant.Services.ServiceClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Preference Definition
 * Created by Lawrence Agbani
 */
public class Preferences {

    public static String TAG = Preferences.class.getSimpleName();

    public static final String PREFS_NAME = "FirimondiService";

    public static int appRunsFirstTime = 0;

    public static int userId = -1;

    public static String token;

    public static String domain;

    private static SharedPreferences settings;

    private static SharedPreferences.Editor editor;

    public static boolean isSignedIn = false;

    public static String username = "";

    public static void loadSettings(Context context) {
        final SharedPreferences settings = context.getSharedPreferences(
                PREFS_NAME, 0);

        isSignedIn = settings.getBoolean("isSignedIn", Preferences.isSignedIn);
//
//        userId = settings.getInt("userId", userId);

        token = settings.getString("token", token);

        // domain = settings.getString("domain", null);

//        username = settings.getString("username", username);
//
//        appRunsFirstTime = settings.getInt("AppRunsFirstTime", appRunsFirstTime);

    }

    public static void  clear(Context context){
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.clear();
    }

    private static boolean hasValue(String s){
        return s != null && !"".equals(s);
    }


    public static void saveSettings(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

//        editor.putInt("AppRunsFirstTime", appRunsFirstTime);
        editor.putBoolean("isSignedIn", isSignedIn);
//        editor.putInt("userId", userId);
        editor.putString("token", token);
        // editor.putString("domain", domain);
        // editor.putString("username", username);

        editor.apply();
    }

    public static void saveFoodItems(Context context, FoodItems items){
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("items", json);
        editor.apply();

    }

    public static FoodItems getFoodItems(Context context){
        final SharedPreferences settings = context.getSharedPreferences(
                PREFS_NAME, 0);
        Gson gson = new Gson();
        String json = settings.getString("items", "");
        FoodItems obj = gson.fromJson(json, FoodItems.class);
        return obj;
    }

    public static void saveOrderDetails(Context context, OrderDetails orderDetails){
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        Gson gson = new Gson();
        String json = gson.toJson(orderDetails);
        editor.putString("orderdetails", json);
        editor.apply();
    }

    public static OrderDetails getOrderDetails(Context context){
        final SharedPreferences settings = context.getSharedPreferences(
                PREFS_NAME, 0);
        Gson gson = new Gson();
        String json = settings.getString("orderdetails", "");
        OrderDetails obj = gson.fromJson(json, OrderDetails.class);
        return obj;
    }

}