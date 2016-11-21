package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.ViewPager;

import com.frimondi.restaurant.restaurant.HomepageSlider;
import com.frimondi.restaurant.restaurant.R;

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

    public void feedbackbuttonclick(View view) {

    }

    public void fbButtonClick(View view) {

    }

    public void twitterButtonClick(View view) {
    }

    public void phoneButtonClick(View view) {

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
