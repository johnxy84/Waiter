package com.frimondi.restaurant.restaurant.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.frimondi.restaurant.restaurant.Adapters.OrderAdapter;
import com.frimondi.restaurant.restaurant.Database.LocalDataSource;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.Models.OrderDetails;
import com.frimondi.restaurant.restaurant.Models.OrderItemDetails;
import com.frimondi.restaurant.restaurant.R;
import com.frimondi.restaurant.restaurant.Services.FrimondiClient;
import com.frimondi.restaurant.restaurant.Services.ServiceClient;
import com.frimondi.restaurant.restaurant.Utils.Preferences;
import com.frimondi.restaurant.restaurant.Utils.RestConstant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lawrence on 11/25/16.
 */
public class OrderActivity1 extends AppCompatActivity implements View.OnClickListener {

    private List<FoodItems.FoodItem> itemList;
    private LocalDataSource dataSource;
    private OrderAdapter adapter;

    private static String TAG = OrderActivity1.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button clear= (Button) findViewById(R.id.clear_button);
        Button confirm=(Button) findViewById(R.id.confirm_button);
        clear.setOnClickListener(this);
        confirm.setOnClickListener(this);

        dataSource = new LocalDataSource(this);
        dataSource.open();


        RecyclerView rv = (RecyclerView) findViewById(R.id.order_recycler_view);
        rv.setHasFixedSize(true);

        itemList = new ArrayList<>();
        itemList = dataSource.getAllItems();
        adapter = new OrderAdapter(getApplicationContext(),itemList);

        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Intent intent;
        switch (id)
        {
            case R.id.action_home:
                intent=new Intent(this, Homepage.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //Clearing your order
    void ClearOrder()
    {
        if (!dataSource.isTableEmpty())
        {
            dataSource.clearTable();
            itemList.clear();
            adapter.notifyDataSetChanged();
            Snackbar.make(findViewById(R.id.RelativeLayout), "Your Order has been cleared", Snackbar.LENGTH_SHORT).show();
        }
        else
            Snackbar.make(findViewById(R.id.RelativeLayout), "Your Order is empty", Snackbar.LENGTH_SHORT).show();
    }

    //Confirm Order
    void ConfirmOrder(View v)
    {
        if (!dataSource.isTableEmpty())
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("What table is this?").setItems(R.array.table_list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int tableNumber) {
                    //where 'tableNumber' is the table number
                    String table = "Order for Table " + Integer.toString(tableNumber + 1) + " has been sent.";
                    //Sending Logic goes here
                    switch (tableNumber) {
                        default:
                            itemList = new ArrayList<>();
                            itemList = dataSource.getAllItems();
                            final MaterialDialog dialogue = new MaterialDialog.Builder(OrderActivity1.this)
                                    .title("Placing your order")
                                    .content("Please wait while we send the order to the kitchen :)")
                                    .progress(true, 0)
                                    .show();
                            makeOrder("table " + Integer.toString(tableNumber + 1), itemList, table, dataSource, adapter);
                            OrderDetails orderDetails = Preferences.getOrderDetails(getApplicationContext());
                            Gson gson = new Gson();
                            String json = gson.toJson(orderDetails);
                            Log.w(TAG, "testId: "  +  json);
                            for (FoodItems.FoodItem item : itemList){
                                createOrderItems(item, Integer.parseInt(orderDetails.id));
                            }
                            Snackbar.make(findViewById(R.id.RelativeLayout), table, Snackbar.LENGTH_SHORT).show();
                            itemList.clear();
                            dataSource.clearTable();
                            adapter.notifyDataSetChanged();
                            dialogue.dismiss();
                            break;
                    }
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else
            Snackbar.make(findViewById(R.id.RelativeLayout), "Your order is empty", Snackbar.LENGTH_SHORT).show();

    }

    public void makeOrder(String table, final List<FoodItems.FoodItem> itemList,
                          final String message, final LocalDataSource dataSource, final OrderAdapter adapter){
        //getting food items
        FrimondiClient client = ServiceClient.getInstance()
                .getClient(OrderActivity1.this, FrimondiClient.class, RestConstant.DOMAIN);
        client.makeOrder(Preferences.token, table, new Callback<OrderDetails>() {
            @Override
            public void success(OrderDetails orderDetails, Response response) {
                Gson gson = new Gson();
                String json = gson.toJson(orderDetails);
                Log.w(TAG, "success: " + json );
                Preferences.saveOrderDetails(OrderActivity1.this, orderDetails);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "failure: " + error.getMessage() );
            }
        });
    }

    public void createOrderItems(FoodItems.FoodItem item, int orderId){
        FrimondiClient client = ServiceClient.getInstance()
                .getClient(OrderActivity1.this, FrimondiClient.class, RestConstant.DOMAIN);
        int itemId = Integer.parseInt(item.getId());
        client.createOrderItem(Preferences.token, orderId+1, itemId,
                item.getQuantity(), new Callback<OrderItemDetails>() {
                    @Override
                    public void success(OrderItemDetails orderItemDetails, Response response) {
                        Log.w(TAG, "success: Order Item Added");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "failure: error adding order item " + error.getMessage() );
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.clear_button):
                ClearOrder();break;
            case (R.id.confirm_button):
                ConfirmOrder(v);break;

        }

    }
}