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

import java.util.List;

/**
 * Created by John on 12-Oct-16.
 */


public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.MyViewHolder> {
    private Context context;

    private List<FoodItems.FoodItem> itemList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView mCardView;
        private RelativeLayout actuallist;
        public TextView discountTextView;
        public ImageView imageView;
        public TextView titleTextView;
        public MyViewHolder(View v) {
            super(v);

            actuallist= (RelativeLayout) v.findViewById(R.id.actual_special_card);
            mCardView = (CardView) v.findViewById(R.id.specialCard_view);
            imageView= (ImageView) v.findViewById(R.id.specialThumbnail);
            discountTextView = (TextView) v.findViewById(R.id.myDiscount);
            titleTextView=(TextView)v.findViewById(R.id.specialTitle);
            actuallist.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(),ItemDetail.class);

            //Send the selected item to the itemDetail page
            intent.putExtra("item",itemList.get(getPosition()));
            v.getContext().startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DiscountAdapter(Context context, List<FoodItems.FoodItem> itemList)
    {
        this.itemList=itemList;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DiscountAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FoodItems.FoodItem menuItem=itemList.get(position);
        holder.discountTextView.setText("%"+menuItem.getPrice());
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
