package com.example.justcook.itemAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.justcook.FavoriteQuery;
import com.example.justcook.R;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter implements Filterable {

    Filter listFilter ;
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<RecipeItem> items = new ArrayList<RecipeItem>();
    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private ArrayList<RecipeItem> filteredItemList = items;


    public RecipeAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(RecipeItem item) {
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItemAll(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecipeItemView view = null;
        //RecyclerView.ViewHolder viewHolder;
        if (convertView == null) {
            view = new RecipeItemView(mContext);
        } else {
            view = (RecipeItemView) convertView;
        }

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        RecipeItem item = null;
        if (position != 0){
            item = filteredItemList.get(position);
        }
        else {
            item = items.get(position);
        }

       // viewHolder = new RecyclerView.ViewHolder();

        ImageButton btnBookmark = (ImageButton) view.findViewById(R.id.bookmark_icon);
        final Integer[] btn_cnt = {0};
        RecipeItem finalItem = item;
        btnBookmark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (btn_cnt[0] == 0){
                    //즐겨찾기를 실행
                    FavoriteQuery.insertBookmarkRcode(finalItem.getRcode());
                    Toast.makeText(mContext, finalItem.getName()+"을/를 북마크에 추가해습니다.", Toast.LENGTH_LONG).show();
                    btn_cnt[0] = 1;
                }else if (btn_cnt[0] == 1){
                    //즐겨찾기 해제
                    FavoriteQuery.deleteBookmarkRcode(finalItem.getRcode());
                    Toast.makeText(mContext, finalItem.getName()+"을/를 북마크에서 삭제했습니다.", Toast.LENGTH_LONG).show();
                    btn_cnt[0] = 0;
                }

            }
        });

        view.setName(item.getName());
        view.setFoodType(item.getFoodtype());
        view.setImage(item.getImglink());

        return view;
    }

    ///////////////////////////////////////////////////////////

    @Override
    public android.widget.Filter getFilter() {
        if (listFilter == null) {
            listFilter = new RecipeAdapter.ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends android.widget.Filter {

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = items ;
                results.count = items.size() ;
            } else {
                ArrayList<RecipeItem> itemList = new ArrayList<RecipeItem>() ;

                for (RecipeItem item : items) {
                    if (item.getName().contains(constraint.toString()))
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
            filteredItemList = (ArrayList<RecipeItem>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }
}

