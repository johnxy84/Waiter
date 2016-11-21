package com.frimondi.restaurant.restaurant.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by John on 19-Oct-16.
 */
public class LocalDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_STOCK="Stock";
    public static final String TABLE_ORDERITEM = "OrderItem";
    public static final String TABLE_ORDER ="OrderTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_ID = "ItemId";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SPECIAL = "IsSpecial";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_IMAGEURL = "ImageURL";
    public static final String COLUMN_DESCRIPTION= "Detail";
    public static final String COLUMN_QTY = "Quantity";


    private static final String DATABASE_NAME = "Local.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement

    //CREATING NORMAL TABLE ON THE PHONE
    private static final String DATABASE_CREATE_STOCK = "create table "
            + TABLE_STOCK + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ITEM_ID + " text,"
            + COLUMN_NAME + " text,"
            + COLUMN_SPECIAL +" text,"
            + COLUMN_CATEGORY + " text,"
            + COLUMN_PRICE +" text,"
            + COLUMN_IMAGEURL +" text,"
            + COLUMN_DESCRIPTION+" text,"
            + COLUMN_QTY+" integer);";

    /*
    //CREATING THE ORDER TABLE-INCOMPLETE
    private static final String DATABASE_CREATE_ORDER="create table "
            + TABLE_ORDER + "( " + COLUMN_ID
            + " integer primary key autoincrement, "
            + " text,"
            ;

    //CREATING THE ORDER ITEM TABLE-INCOMPLETE
    private static final String DATABASE_CREATE_ORDERITEM="create table "
            + TABLE_ORDER + "( " + COLUMN_ID
            + " integer primary key autoincrement, "
            + " text,"
            ;
            */

    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_STOCK);
       /* database.execSQL(DATABASE_CREATE_ORDERITEM);
        database.execSQL(DATABASE_CREATE_ORDER);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LocalDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);*/
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
        onCreate(db);
    }

}
