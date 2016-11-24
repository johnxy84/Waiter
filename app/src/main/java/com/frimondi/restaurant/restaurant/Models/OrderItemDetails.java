package com.frimondi.restaurant.restaurant.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lawrence on 11/24/16.
 */
public class OrderItemDetails {

    @SerializedName("id")
    public String Id;

    @SerializedName("order_id")
    public String orderId;

    @SerializedName("fooditem_id")
    public String foodItemId;

    @SerializedName("qty")
    public String quantity;

}
