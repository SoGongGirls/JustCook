package com.example.justcook.fragment;

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

import com.example.justcook.CameraActivity;
import com.example.justcook.CameraJson;
import com.example.justcook.DetectorActivity;
import com.example.justcook.Input_Ingredients;
import com.example.justcook.R;
import com.example.justcook.auto_guide;
import com.example.justcook.manual_guide;


public class Fragment1 extends Fragment {

    View btn_auto;
    View btn_manual;
    View btn_json; //json 파일 확인용 추후 삭제
    View btn_rinfo; //db연동 확인용 추후 삭제
    Button btn_guide1;
    Button btn_guide2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_1, container, false);
        btn_auto = rootView.findViewById(R.id.btn_auto);
        btn_manual = rootView.findViewById(R.id.btn_manual);
        btn_json = rootView.findViewById(R.id.button_json);
        //btn_rinfo = rootView.findViewById(R.id.button_rinfo);
        btn_guide1 = rootView.findViewById(R.id.btn_guide1);
        btn_guide2 = rootView.findViewById(R.id.btn_guide2);

        // 영상 인식 모델 실행 버튼
        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_auto = new Intent(getActivity(), DetectorActivity.class);
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
        //json 파일 확인 용 추후 삭제
        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_json = new Intent(getActivity(), CameraJson.class);
                startActivity(i_json);
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