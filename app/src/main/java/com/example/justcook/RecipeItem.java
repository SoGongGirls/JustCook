package com.example.justcook;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class RecipeItem {

    Drawable r_img;
    String r_name;
    Drawable r_icon;

    public Drawable getR_img() {
        return r_img;
    }

    public void setR_img(Drawable r_img) {
        this.r_img = r_img;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public Drawable getR_icon() {
        return r_icon;
    }

    public void setR_icon(Drawable r_icon) {
        this.r_icon = r_icon;
    }
}
