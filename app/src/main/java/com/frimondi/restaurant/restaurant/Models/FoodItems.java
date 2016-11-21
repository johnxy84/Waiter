package com.frimondi.restaurant.restaurant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrence on 10/31/16.
 */
public class FoodItems {

    @SerializedName("fooditems")
    @Expose
    private List<FoodItem> foodItems = new ArrayList<FoodItem>();

    /**
     * @return
     * The foodItems
     */
    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    /**
     * @param foodItems
     * The foodItems
     */
    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public static class FoodItem implements Serializable {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIsSpecial() {
            return isSpecial;
        }

        public void setIsSpecial(String isSpecial) {
            this.isSpecial = isSpecial;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getFoodDescription() {
            return foodDescription;
        }

        public void setFoodDescription(String foodDescription) {
            this.foodDescription = foodDescription;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        @SerializedName("item_id")
        @Expose
        private String id;

        @SerializedName("image_url")
        @Expose
        private String imageUrl;

        @SerializedName("food_name")
        @Expose
        private String foodName;

        @Expose
        private String  price;

        @SerializedName("item_special")
        @Expose
        private String isSpecial;

        @SerializedName("category")
        @Expose
        private String categoryId;

        @SerializedName("category_name")
        @Expose
        private String categoryName;

        @SerializedName("food_description")
        @Expose
        private String foodDescription;

        private int Quantity=1;
    }
}
