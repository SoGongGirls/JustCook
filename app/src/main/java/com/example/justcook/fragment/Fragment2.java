package com.example.justcook.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.justcook.MySQLiteOpenHelper;
import com.example.justcook.R;
import com.example.justcook.Recommend;
import com.example.justcook.bookmark.BookmarkQuery;
import com.example.justcook.bookmark.CameraDBQuery;
import com.example.justcook.itemAdapter.InputIngredientsAdapter;
import com.example.justcook.itemAdapter.InputIngredientsItem;

import java.util.ArrayList;
import java.util.Arrays;

public class Fragment2 extends Fragment {
    ListView first_item;
    ListView second_item;
    ListView third_item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm_ing);
        Button btn_recipe_recommend = (Button) view.findViewById(R.id.btn_recipe_recommend);
        EditText editIng = (EditText) view.findViewById(R.id.edit_ing);

        first_item = (ListView) view.findViewById(R.id.first_item);
        second_item = (ListView) view.findViewById(R.id.second_item);
        third_item = (ListView) view.findViewById(R.id.third_item);

        InputIngredientsAdapter adapter1st = new InputIngredientsAdapter();
        InputIngredientsAdapter adapter2nd = new InputIngredientsAdapter();
        InputIngredientsAdapter adapter3th = new InputIngredientsAdapter();

        //????????? ????????? ??????????????? ????????????. DB??? ????????????
        //ArrayList<String> item1st = new ArrayList<String>(Arrays.asList("?????????", "????????????", "??????"));

        CameraDBQuery CQ = new CameraDBQuery(getContext());
        ArrayList<String> item1st = CQ.AllCmrDBData();

        for (int i = 0; i<item1st.size(); i++){
            adapter1st.addItem(ContextCompat.getDrawable(getContext(), R.drawable.appicon1),
                                item1st.get(i),
                                ContextCompat.getDrawable(getContext(), R.drawable.add));
        }

        first_item.setAdapter(adapter1st);
        setListViewHeightBasedOnChildren(first_item);
        setListViewHeightBasedOnChildren(second_item);
        setListViewHeightBasedOnChildren(third_item);


        //????????? ?????? ?????? ??????
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> add_ingredients_list = new ArrayList<String>();
                for (int i = 0; i<adapter1st.getCount(); i++){
                    InputIngredientsItem item = (InputIngredientsItem) adapter1st.getItem(i);
                    if(item.getCheck()){
                        //true ?????? = ????????? ??????!
                        Log.v("??????", item.getI_name());
                        //add_ingredients_list.add(i, item.getI_name());
                        add_ingredients_list.add(item.getI_name());
                    }else{

                    }

                }

                String temp = "";
                for (String ing : add_ingredients_list){
                    temp += (String) ing;
                    temp += " ";
                }
                if(temp != null) editIng.setText(temp);
            }
        });



        //????????? ???????????? ??????
        btn_recipe_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String opt = editIng.getText().toString();
                Intent rcm_recipe = new Intent(getActivity(), Recommend.class);
                rcm_recipe.putExtra("ing_list", opt);
                Log.v("???????????????2", opt);
                startActivity(rcm_recipe);

            }
        });



        return view;
    }


    /* ???????????? ?????? ?????? ????????? */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}