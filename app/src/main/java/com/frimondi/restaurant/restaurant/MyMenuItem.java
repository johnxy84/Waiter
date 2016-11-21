package com.frimondi.restaurant.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Created by John on 13-Oct-16.
 */
public class MyMenuItem implements Serializable{
    //Model class for each individual MenuItem

    private int id=0;
    private String name="";
    private int price=1;
    private String imageURL="www.image.com";
    private String detail="";
    private int discount=0;
    private int quantity=1;


    //constructor for test purposes
    public MyMenuItem(int id,String name, int price) {
        this.name = name;
        this.id=id;
        this.price = price;
        this.discount=0;
    }

    public MyMenuItem()
    {

    }
    public MyMenuItem(int id,String name, int price,String detail) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.detail=detail;
        //this.imageURL=imageURL;
        //this.discount=discount;
    }
    public MyMenuItem(int id, String name, int price,String imageURL,String detail,int discount) {
        this.name = name;
        this.detail=detail;
        this.price = price;
        this.discount=discount;
        this.id=id;
    }

    //getters and setters for variables
    public void setImageURL(String imageURL)
    {
        this.imageURL=imageURL;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setQuantity(int quantity){this.quantity=quantity;}

    public int getQuantity(){return quantity;}
}