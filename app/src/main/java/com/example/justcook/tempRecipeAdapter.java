//package com.example.justcook;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import java.util.ArrayList;
//
//public class tempRecipeAdapter extends BaseAdapter{
//
//        ArrayList<tempRecipeItem> items = new ArrayList<tempRecipeItem>();
//
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//
//        public void addItem(tempRecipeItem item){
//            items.add(item);
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            SingerItemView view = null;
//            if (convertView == null){
//                view = new SingerItemView(getApplicationContext());
//            }else{
//                view = (SingerItemView) convertView;
//            }
//
//
//            Singeritem item = items.get(position);
//            view.setName(item.getName());
//            view.setMobile(item.getMobile());
//            view.setImage(item.getResId());
//
//            return view;
//        }
//    }
//}
