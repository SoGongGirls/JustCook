package com.example.justcook.itemAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<com.example.justcook.itemAdapter.Ingredientitem> items = new ArrayList<>();

    public IngredientAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){return items.size();}

    public void addItem(com.example.justcook.itemAdapter.Ingredientitem item){items.add(item);}

    @Override
    public Object getItem(int position){return items.get(position);}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        IngredientitemView view = null;
        if(convertView == null){
            view = new IngredientitemView(mContext);

        }else{
            view = (IngredientitemView) convertView;
        }

        com.example.justcook.itemAdapter.Ingredientitem item = items.get(position);
        view.setINGname(item.getName());
        view.setINGQuantity(item.getQuantity());

        return view;
    }

}
