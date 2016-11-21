package com.frimondi.restaurant.restaurant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.MyMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 19-Oct-16.
 */
public class LocalDataSource {
    // Database fields
    private SQLiteDatabase database;
    private LocalDbHelper dbHelper;
    private String[] allColumns = {
            LocalDbHelper.COLUMN_ID,
            LocalDbHelper.COLUMN_ITEM_ID,
            LocalDbHelper.COLUMN_NAME,
            LocalDbHelper.COLUMN_SPECIAL,
            LocalDbHelper.COLUMN_CATEGORY,
            LocalDbHelper.COLUMN_PRICE,
            LocalDbHelper.COLUMN_IMAGEURL,
            LocalDbHelper.COLUMN_DESCRIPTION,
            LocalDbHelper.COLUMN_QTY };


    public LocalDataSource(Context context) {
        dbHelper = new LocalDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createItem(FoodItems.FoodItem item, int quantity) {
        ContentValues values = new ContentValues();
        values.put(LocalDbHelper.COLUMN_ITEM_ID, item.getId());
        values.put(LocalDbHelper.COLUMN_NAME, item.getFoodName());
        values.put(LocalDbHelper.COLUMN_SPECIAL, item.getIsSpecial());
        values.put(LocalDbHelper.COLUMN_CATEGORY, item.getCategoryName());
        values.put(LocalDbHelper.COLUMN_PRICE,Integer.parseInt(item.getPrice()) );
        values.put(LocalDbHelper.COLUMN_IMAGEURL,item.getImageUrl());
        values.put(LocalDbHelper.COLUMN_DESCRIPTION,item.getFoodDescription());
        values.put(LocalDbHelper.COLUMN_QTY,quantity);


        long insertId = database.insert(LocalDbHelper.TABLE_STOCK, null,
                values);
       /* Cursor cursor = database.query(LocalDbHelper.TABLE_STOCK,
                allColumns, LocalDbHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FoodItems.FoodItem newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;*/
    }

    public void deleteItem(MyMenuItem item) {
        long id = item.getID();
        System.out.println("Comment deleted with id: " + id);
        database.delete(LocalDbHelper.TABLE_STOCK, LocalDbHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<FoodItems.FoodItem> getAllItems() {
        List<FoodItems.FoodItem> items = new ArrayList<>();

        Cursor cursor = database.query(LocalDbHelper.TABLE_STOCK,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FoodItems.FoodItem item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return items;
    }

    private FoodItems.FoodItem cursorToItem(Cursor cursor) {
        FoodItems.FoodItem item = new FoodItems.FoodItem();
        item.setId(cursor.getString(1));
        item.setFoodName(cursor.getString(2));
        item.setIsSpecial(cursor.getString(3));
        item.setCategoryName(cursor.getString(4));
        item.setPrice(cursor.getString(5));
        item.setImageUrl(cursor.getString(6));
        item.setFoodDescription(cursor.getString(7));
        item.setQuantity(cursor.getInt(8));

        return item;
    }

    public void clearTable()
    {
       database.execSQL("delete from " + LocalDbHelper.TABLE_STOCK);
    }

    public boolean isTableEmpty()
    {
        String query="SELECT count(*) FROM "+LocalDbHelper.TABLE_STOCK;
        Cursor cursor=database.rawQuery(query, null);
        cursor.moveToFirst();
        int count=cursor.getInt(0);
        return count <= 0;

    }
}

