package com.frimondi.restaurant.restaurant.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import com.frimondi.restaurant.restaurant.Adapters.CategoryAdapter;
import com.frimondi.restaurant.restaurant.Models.FoodItems;
import com.frimondi.restaurant.restaurant.MyMenuItem;
import com.frimondi.restaurant.restaurant.R;

import java.util.List;

public class CategoryFragment extends Fragment {

    private List<FoodItems.FoodItem> itemList;


    public CategoryFragment() {
        // Required empty public constructor
    }
    //constructor of tab fragment
    public CategoryFragment newInstance(List<FoodItems.FoodItem> itemList)
    {
        CategoryFragment fragment=new CategoryFragment();
        fragment.setList(itemList);

        return fragment;
    }

    void setList(List<FoodItems.FoodItem>itemList)
    {
      this.itemList=itemList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        CategoryAdapter adapter = new CategoryAdapter(this.getContext(),itemList);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }



}