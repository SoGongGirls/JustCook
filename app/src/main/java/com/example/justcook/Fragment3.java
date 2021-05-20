package com.example.justcook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

public class Fragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3, container, false);
        // 초기화, 참조 및 생성
        final ListView R_ListView = (ListView) view.findViewById(R.id.R_ListView);
        EditText r_search = (EditText) view.findViewById(R.id.r_search);
        RecipeAdapter adapter = new RecipeAdapter();

        // 리스트뷰 참조 및 Adapter 연결
        R_ListView.setAdapter(adapter);

        // 리스트 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.test_food),
                "토마토 달걀 볶음", ContextCompat.getDrawable(getActivity(), R.drawable.bookmark_none));

        // EditText 변경 이벤트 처리
        r_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                ((RecipeAdapter)R_ListView.getAdapter()).getFilter().filter(filterText) ;
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

        });


        // 레시피 상세 보기
        R_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent i_recipe = new Intent(getActivity(), recipe.class);
                startActivity(i_recipe);
            }
        });

        return view;


    }//onCreateView

    /* 리스트뷰 크기 조절 메소드 */
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