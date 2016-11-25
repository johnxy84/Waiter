package com.frimondi.restaurant.restaurant.Services;

import android.text.Editable;

import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.Models.Logout;
import com.frimondi.restaurant.restaurant.Models.OrderDetails;
import com.frimondi.restaurant.restaurant.Models.OrderItemDetails;
import com.frimondi.restaurant.restaurant.Models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by lawrence on 10/31/16.
 */
public interface FrimondiClient {

    @FormUrlEncoded
    @POST("/api/v1/authenticate")
    void login(@Field("email") String email, @Field("password") String password, Callback<User> callback);

    @GET("/api/v1/fooditems")
    void getFoodItems(@Header("Authorization") String token, Callback<FoodItems> callback);

    @FormUrlEncoded
    @POST("/api/v1/orders")
    void makeOrder(@Header("Authorization")String token, @Field("table") String table, Callback<OrderDetails> callback);

    @FormUrlEncoded
    @POST("/api/v1/orderitems")
    void createOrderItem(@Header("Authorization")String token, @Field("order_id") int orderId,
                   @Field("fooditem_id") int fooditemId, @Field("qty") int quantity,
                         Callback<OrderItemDetails> callback);

    @FormUrlEncoded
    @POST("/api/v1/invalidate")
    void invalidateUser(@Field("token")String token, Callback<Logout> callback);


}
