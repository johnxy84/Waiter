package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.frimondi.restaurant.restaurant.Database.LocalDataSource;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.R;
import com.squareup.picasso.Picasso;

public class ItemDetail extends AppCompatActivity {

    private FoodItems.FoodItem item;
    private TextView title;
    private TextView price;
    private ImageView itemImage;
    private TextView detail;
    private TextView quantity;
    private LocalDataSource dataSource;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent=getIntent();
        item=(FoodItems.FoodItem)intent.getSerializableExtra("item");

        //set plus and minus button listeners and functions
        ImageButton reduceButton,addButton;
        reduceButton= (ImageButton) findViewById(R.id.reduceButton);
        reduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceValue();
            }
        });
        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue();
            }
        });

        setTitle(item.getFoodName());
        bindValues();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Order", Snackbar.LENGTH_SHORT).show();
                addItem();
            }
        });
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
                startActivity(new Intent(this, Homepage.class));
                break;
            case R.id.action_cart:
                startActivity(new Intent(this,OrderActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    void addItem()
    {
        String Quantity= (String) quantity.getText();
        //int discount=(item.getDiscount()/100)*item.getPrice();
        int totalPrice=(Integer.parseInt( (String) quantity.getText())*Integer.parseInt(item.getPrice()) );//-discount;
        item.setPrice(Integer.toString(totalPrice));
        dataSource = new LocalDataSource(this);
        dataSource.open();
        dataSource.createItem(item, Integer.parseInt(Quantity));
        //UpdateItemDetails();
    }
    void UpdateItemDetails()
    {
        //int discount=(item.getDiscount()/100)*item.getPrice();
        int totalPrice= item.getQuantity()*Integer.parseInt(item.getPrice());
        title.setText(item.getFoodName());
        price.setText("Total Price: ₦"+Integer.toString(totalPrice));
        detail.setText(item.getFoodDescription());
        quantity.setText(Integer.toString(item.getQuantity()));

    }

    void reduceValue()
    {
        int value=Integer.parseInt((String) quantity.getText());
        if (value>0)
        {
            value--;
            item.setQuantity(value);
            UpdateItemDetails();
        }
    }

    void addValue()
    {
        int value=Integer.parseInt((String) quantity.getText());
        value++;
        item.setQuantity(value);
        UpdateItemDetails();
    }

    void bindValues()
    {
        quantity= (TextView) findViewById(R.id.quantity);
        title= (TextView) findViewById(R.id.itemName);
        price= (TextView) findViewById(R.id.totalPrice);
        detail= (TextView) findViewById(R.id.detailText);
        itemImage= (ImageView) findViewById(R.id.itemImage);
        Picasso.with(getApplicationContext()).load(item.getImageUrl())
                .placeholder(R.drawable.ic_add_circle_outline_24dp)
                .resize(100,100)
                .into(itemImage);
        itemImage.setScaleType(ImageView.ScaleType.FIT_XY);
        UpdateItemDetails();
    }
}
