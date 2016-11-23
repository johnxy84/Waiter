package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.frimondi.restaurant.restaurant.Fragments.CategoryFragment;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.R;
import com.frimondi.restaurant.restaurant.Utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuActivity extends AppCompatActivity {

    //List of MenuItems based on categories
    private List<FoodItems.FoodItem> foodItemList ;
    private List<FoodItems.FoodItem> drinksItemList ;
    private List<FoodItems.FoodItem> snacksItemList ;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmenu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        prepareItemsList();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


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
            case R.id.action_cart:
                intent=new Intent(this, OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.action_Home:
                intent=new Intent(this, Homepage.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //Populates the list of food
    void prepareItemsList()
    {
        foodItemList=new ArrayList<>();
        drinksItemList=new ArrayList<>();
        snacksItemList=new ArrayList<>();

        //Categorize the fooditems into drinks, snacks and foods
        for (FoodItems.FoodItem item:
                Preferences.getFoodItems(getApplicationContext())
                //getfoodlist from the foodItem class
                .getFoodItems())
        {
            switch (item.getCategoryName())
            {
                case "Food":
                    foodItemList.add(item);
                    break;
                case "Drinks":
                    drinksItemList.add(item);
                    break;
                case "Snacks":
                    snacksItemList.add(item);
                    break;
            }

        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CategoryFragment().newInstance(foodItemList);
                case 1:
                    return new CategoryFragment().newInstance(drinksItemList);
                case 2:
                    return new CategoryFragment().newInstance(snacksItemList);
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Foods";
                case 1:
                    return "Drinks";
                case 2:
                    return "Snacks";
            }
            return null;
        }
    }

}
