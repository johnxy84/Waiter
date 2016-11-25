package com.frimondi.restaurant.restaurant.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.view.ViewPager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.frimondi.restaurant.restaurant.HomepageSlider;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.Models.Logout;
import com.frimondi.restaurant.restaurant.Models.OrderItemDetails;
import com.frimondi.restaurant.restaurant.R;
import com.frimondi.restaurant.restaurant.Services.FrimondiClient;
import com.frimondi.restaurant.restaurant.Services.ServiceClient;
import com.frimondi.restaurant.restaurant.Utils.Preferences;
import com.frimondi.restaurant.restaurant.Utils.RestConstant;
import com.frimondi.restaurant.restaurant.Utils.Preferences;

import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Homepage extends AppCompatActivity {
    private ViewPager mViewPager;
    HomepageSlider adapterView;
    private static String TAG = Homepage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences.loadSettings(Homepage.this);
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
                new MaterialDialog.Builder(this)
                        .title("Signing you out")
                        .content("Please wait while we Sign you out :)")
                        .progress(true, 0)
                        .show();
                FrimondiClient client = ServiceClient.getInstance()
                        .getClient(Homepage.this, FrimondiClient.class, RestConstant.DOMAIN);
                String token = Preferences.token;
                token = token.replace("Bearer ", "");
                Log.w(TAG, "onOptionsItemSelected:" + token );
                client.invalidateUser(token, new Callback<Logout>() {
                    @Override
                    public void success(Logout logout, Response response) {
                        Log.w(TAG, "success:  Logging out " + logout.getMessage());
                        Preferences.loadSettings(Homepage.this);
                        Preferences.isSignedIn=false;
                        Preferences.token = "";
                        Preferences.saveSettings(Homepage.this);
                        Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "failure: " + error.getMessage() );
                    }
                });
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
