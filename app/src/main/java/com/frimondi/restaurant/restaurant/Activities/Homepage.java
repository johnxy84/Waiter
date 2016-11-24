package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.view.ViewPager;

import com.frimondi.restaurant.restaurant.HomepageSlider;
import com.frimondi.restaurant.restaurant.R;
import com.frimondi.restaurant.restaurant.Utils.Preferences;

import java.util.Timer;
import java.util.TimerTask;

public class Homepage extends AppCompatActivity {
    private ViewPager mViewPager;
    HomepageSlider adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        adapterView = new HomepageSlider(this);
        mViewPager.setAdapter(adapterView);
        Timer timer = new Timer();
        timer.schedule(new UpdateTimeTask(), 5000, 5000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_home:
                Preferences.loadSettings(Homepage.this);
                Preferences.isSignedIn=false;
        }

        return super.onOptionsItemSelected(item);
    }


    public void menuButtonClick(View view) {
        Intent intent = new Intent(this, FoodMenuActivity.class);
        startActivity(intent);
    }

    public void aboutButtonClick(View view) {

    }

    public void specialbuttonclick(View view) {
        Intent intent = new Intent(this, DiscountActivity.class);
        startActivity(intent);
    }


    class UpdateTimeTask extends TimerTask {
        public void run() {
            mViewPager.post(new Runnable() {
                public void run() {

                    if (mViewPager.getCurrentItem() < adapterView
                            .getCount() - 1) {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);


                    } else {
                        mViewPager.setCurrentItem(0, true);

                    }
                }
            });
        }
    }
}
