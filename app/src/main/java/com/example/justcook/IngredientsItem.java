package com.example.justcook;

import android.graphics.drawable.Drawable;

public class IngredientsItem {
    // 얻어올 정보들을 저장한 클래스
    Drawable i_img;  // 재료 이미지
    String i_name;  // 재료 이름
    Drawable i_icon; // 재료 선택 아이콘


    public Drawable getI_img() {
        return i_img;
    }

    public void setI_img(Drawable i_img) {
        this.i_img = i_img;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public Drawable getI_icon() {
        return i_icon;
    }

    public void setI_icon(Drawable i_icon) {
        this.i_icon = i_icon;
    }
}