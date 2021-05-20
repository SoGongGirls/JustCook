package com.example.justcook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment1 extends Fragment {

    View btn_auto;
    View btn_manual;
    Button btn_guide1;
    Button btn_guide2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_1, container, false);
        btn_auto = rootView.findViewById(R.id.btn_auto);
        btn_manual = rootView.findViewById(R.id.btn_manual);
        btn_guide1 = rootView.findViewById(R.id.btn_guide1);
        btn_guide2 = rootView.findViewById(R.id.btn_guide2);




        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_auto = new Intent(getActivity(), Camera.class);
                startActivity(i_auto);
            }
        });

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_manual = new Intent(getActivity(), Input_Ingredients.class);
                startActivity(i_manual);
            }
        });


        btn_guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_auto = new Intent(getActivity(), auto_guide.class);
                startActivity(i_auto);
            }
        });

        btn_guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_manual = new Intent(getActivity(), manual_guide.class);
                startActivity(i_manual);
            }
        });

        return rootView;
    }




}