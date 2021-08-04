package com.example.justcook.itemAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.justcook.R;
import com.example.justcook.bookmark.BookmarkQuery;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter implements Filterable {

    Filter listFilter;
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<RecipeItem> items = new ArrayList<RecipeItem>();
    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private ArrayList<RecipeItem> filteredItemList = items;


    public RecipeAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void addItem(RecipeItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
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

        //데이터 값 표시하기
        view.setName(item.getName());
        view.setFoodType(item.getFoodtype());
        view.setImage(item.getImglink());
        view.setBook(item.getRcode());

        //즐겨찾기 구현
        ImageButton btnBookmark = (ImageButton) view.findViewById(R.id.bookmark_icon);
        RecipeItem finalItem = item;
        btnBookmark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                BookmarkQuery BQ = new BookmarkQuery(mContext);
                boolean check = BQ.checkBookmarkData(finalItem.getRcode());

                if (check == false){
                    //즐겨찾기를 실행
                    BQ.insertBookmarkRcode(finalItem.getRcode());
                    Toast.makeText(mContext, finalItem.getName()+"을/를 북마크에 추가했습니다.", Toast.LENGTH_LONG).show();
                    btnBookmark.setImageResource(R.drawable.bookmark_selected);
                }else if (check== true){
                    //즐겨찾기 해제
                    BQ.deleteBookmarkRcode(finalItem.getRcode());
                    Toast.makeText(mContext, finalItem.getName()+"을/를 북마크에서 삭제했습니다.", Toast.LENGTH_LONG).show();
                    btnBookmark.setImageResource(R.drawable.bookmark_none);
                }

            }
        });

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

