package com.example.justcook.itemAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ProcessAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<com.example.justcook.itemAdapter.Processitem> items = new ArrayList<>();

    public ProcessAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount(){return items.size();}

    public void addItem(com.example.justcook.itemAdapter.Processitem item){items.add(item);}

    @Override
    public Object getItem(int position){return items.get(position);}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ProcessitemView view = null;
        if(convertView == null){
            view = new ProcessitemView(mContext);

        }else{
            view = (ProcessitemView) convertView;
        }
        com.example.justcook.itemAdapter.Processitem item = items.get(position);
        view.setOrder(item.getOrder());
        view.setImage(item.getImg_url());
        view.setExplain(item.getExplain());
        view.setTip(item.getTip());

        return view;
    }
}
