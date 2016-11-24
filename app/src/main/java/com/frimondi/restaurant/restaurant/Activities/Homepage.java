package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.view.ViewPager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.frimondi.restaurant.restaurant.HomepageSlider;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.R;
import com.frimondi.restaurant.restaurant.Services.FrimondiClient;
import com.frimondi.restaurant.restaurant.Services.ServiceClient;
import com.frimondi.restaurant.restaurant.Utils.Preferences;
import com.frimondi.restaurant.restaurant.Utils.RestConstant;

import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Homepage extends AppCompatActivity {
    private ViewPager mViewPager;
    HomepageSlider adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getFoodItems();
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

    public void getFoodItems(){
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Getting stuff from the kitchen")
                .content("Please wait while we retrieve the latest items from the kitchen :)")
                .progress(true, 0)
                .show();
        //getting food items
        FrimondiClient client1 = ServiceClient.getInstance()
                .getClient(Homepage.this, FrimondiClient.class, RestConstant.DOMAIN);
        client1.getFoodItems(Preferences.token, new Callback<FoodItems>() {
            @Override
            public void success(FoodItems foodItems, Response response) {
                dialog.dismiss();
                //save the json to a list of food
                Preferences.saveFoodItems(getApplicationContext(), foodItems);
                               /* List<FoodItems.FoodItem> foodList= Preferences
                                        .getFoodItems(getApplicationContext())
                                        .getFoodItems();
                                Gson gson = new Gson();
                                String json = gson.toJson(foodItems);
                                Log.w("Fooditems", "Here's your response " + json);*/
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Log.w("Fooditems", "getFood error response " + error.toString());
            }
        });
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
