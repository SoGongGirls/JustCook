package com.example.justcook.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.justcook.R;
import com.example.justcook.Recommend;

public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_2, container, false);
        Button btn_recipe_recommend = (Button) view.findViewById(R.id.btn_recipe_recommend);
        EditText editIng = (EditText) view.findViewById(R.id.edit_ing);


        btn_recipe_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String opt = editIng.getText().toString();
                Intent rcm_recipe = new Intent(getActivity(), Recommend.class);
                rcm_recipe.putExtra("ing_list", opt);
                Log.v("프래그먼트2", opt);
                startActivity(rcm_recipe);

            }
        });

        return view;
    }
}