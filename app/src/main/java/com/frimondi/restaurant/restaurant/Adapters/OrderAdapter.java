package com.frimondi.restaurant.restaurant.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.MyMenuItem;
import com.frimondi.restaurant.restaurant.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by John on 14-Oct-16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    //List to hold the menu items
    private List<FoodItems.FoodItem> itemList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView imageView;
        public TextView priceTextView;
        public TextView titleTextView;
        public TextView quantityTextView;
        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.orderCard_view);
            imageView= (ImageView) v.findViewById(R.id.orderthumbnail);
            priceTextView= (TextView) v.findViewById(R.id.orderPrice);
            titleTextView=(TextView) v.findViewById(R.id.orderTitle);
            quantityTextView= (TextView) v.findViewById(R.id.orderQuantity);

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderAdapter(Context context, List<FoodItems.FoodItem> itemList)
    {
        this.context=context;
        this.itemList=itemList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FoodItems.FoodItem menuItem=itemList.get(position);
        holder.priceTextView.setText("₦" + menuItem.getPrice());
        holder.titleTextView.setText(menuItem.getFoodName());
        holder.quantityTextView.setText(Integer.toString(menuItem.getQuantity()));
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
