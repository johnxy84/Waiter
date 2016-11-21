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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.frimondi.restaurant.restaurant.Adapters.OrderAdapter;
import com.frimondi.restaurant.restaurant.Database.LocalDataSource;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.MyMenuItem;
import com.frimondi.restaurant.restaurant.R;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private List<FoodItems.FoodItem> itemList;
    private LocalDataSource dataSource;
    private OrderAdapter adapter;


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

        itemList=new ArrayList<>();
        itemList=dataSource.getAllItems();
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
            case R.id.action_Home:
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
            alertDialogBuilder.setTitle("Which table is this?").setItems(R.array.table_list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //where 'which' is the table number
                    String table = "Order for Table" + Integer.toString(which + 1) + " has been sent.";

                    //Sending Logic goes here

                    switch (which) {
                        default:
                            Snackbar.make(findViewById(R.id.RelativeLayout), table, Snackbar.LENGTH_SHORT).show();
                            dataSource.clearTable();
                            itemList.clear();
                            adapter.notifyDataSetChanged();
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
