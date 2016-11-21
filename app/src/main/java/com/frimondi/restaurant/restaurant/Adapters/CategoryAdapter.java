package com.frimondi.restaurant.restaurant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frimondi.restaurant.restaurant.Activities.ItemDetail;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.MyMenuItem;
import com.frimondi.restaurant.restaurant.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John on 12-Oct-16.
 */


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    //List to hold the menu items
    private List<FoodItems.FoodItem> itemList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView mCardView;
        public RelativeLayout actualList;
        public TextView priceTextView;
        public TextView titleTextView;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            actualList = (RelativeLayout) v.findViewById(R.id.actual_card);
            mCardView = (CardView) v.findViewById(R.id.myCard_view);
            priceTextView= (TextView) v.findViewById(R.id.myprice);
            imageView= (ImageView) v.findViewById(R.id.thumbnail);
            titleTextView=(TextView)v.findViewById(R.id.mytitle);
            actualList.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,ItemDetail.class);
            //noinspection deprecation

            intent.putExtra("item", itemList.get(getPosition()));
            context.startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(Context context, List<FoodItems.FoodItem> itemList)
    {
        this.context=context;
       this.itemList=itemList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FoodItems.FoodItem menuItem=itemList.get(position);
        holder.priceTextView.setText("₦" + menuItem.getPrice());
        holder.titleTextView.setText(menuItem.getFoodName());
        Picasso.with(context).load(menuItem.getImageUrl())
                .placeholder(R.drawable.ic_add_circle_outline_24dp)
                .resize(100,100)
                .into(holder.imageView);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
