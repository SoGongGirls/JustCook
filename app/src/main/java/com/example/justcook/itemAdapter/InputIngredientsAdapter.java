package com.example.justcook.itemAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justcook.R;

import java.util.ArrayList;

public class InputIngredientsAdapter extends BaseAdapter implements Filterable {

    Filter listFilter ;

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList. (원본 데이터 리스트)
    private ArrayList<InputIngredientsItem> ingredientsList = new ArrayList<InputIngredientsItem>();
    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private ArrayList<InputIngredientsItem> filteredItemList = ingredientsList;


    // IngredientsAdapter의 생성자
    public InputIngredientsAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int i) {
        return filteredItemList.get(i);
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int i) {
        return i;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable img, String name, Drawable icon) {
        InputIngredientsItem item = new InputIngredientsItem();

        item.setI_img(img);
        item.setI_name(name);
        item.setI_icon(icon);

        ingredientsList.add(item);
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ingredient_item, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView i_img = (ImageView) view.findViewById(R.id.i_img) ;
        TextView i_name = (TextView) view.findViewById(R.id.i_name) ;
        ImageView i_icon = (ImageView) view.findViewById(R.id.i_icon);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        InputIngredientsItem listViewItem = filteredItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        i_img.setImageDrawable(listViewItem.getI_img());
        i_name.setText(listViewItem.getI_name());
        i_icon.setImageDrawable(listViewItem.getI_icon());

        return view;



    }

    @Override
    public android.widget.Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends android.widget.Filter {

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = ingredientsList ;
                results.count = ingredientsList.size() ;
            } else {
                ArrayList<InputIngredientsItem> itemList = new ArrayList<InputIngredientsItem>() ;

                for (InputIngredientsItem item : ingredientsList) {
                    if (item.getI_name().contains(constraint.toString()))
                    {
                        itemList.add(item) ;
                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // update listview by filtered data list.
            filteredItemList = (ArrayList<InputIngredientsItem>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }

}
