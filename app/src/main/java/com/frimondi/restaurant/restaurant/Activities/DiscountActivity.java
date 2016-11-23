package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.frimondi.restaurant.restaurant.Adapters.DiscountAdapter;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.R;
import com.frimondi.restaurant.restaurant.Utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    private String [] specialTitleList= new String[]{"Okra Soup","Fanta","Rice","Hamburger","Pounded Yam","Sprite"};
    private String detail=
            "This is the detail of the item, it is just dummy text.\n"+"This is the detail of the item, it is just dummy text.";
    private int [] specialDiscountList=new int[]{20,33,21,40,50,30};
    private List<FoodItems.FoodItem>itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_recycler_view);
        itemList=new ArrayList<>();
        rv.setHasFixedSize(true);

        prepareDiscountList();
        DiscountAdapter adapter = new DiscountAdapter(getApplicationContext(),itemList);

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

    void prepareDiscountList()
    {
        itemList=new ArrayList<>();
        for (FoodItems.FoodItem item: Preferences
                .getFoodItems(getApplicationContext())
                //getfoodList from the fooditem class
                .getFoodItems())
        {
            //check if item is a special item
            switch (item.getIsSpecial())
            {
                case "1":
                    itemList.add(item);
            }

        }
    }

}
