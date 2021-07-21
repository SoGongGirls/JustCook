package com.example.justcook.itemAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.justcook.R;

import java.io.InputStream;
import java.net.URL;

public class RecipeItemView extends LinearLayout {
    TextView tvName;
    TextView tvfoodtype;
    ImageView imageView;
    ImageButton imageButton;




    public RecipeItemView(Context context){
        super(context);
        init(context);
    }

    public RecipeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recipe_item, this, true);

        tvName = findViewById(R.id.r_name);
        tvfoodtype = findViewById(R.id.r_type);
        imageView = findViewById(R.id.r_img);
        imageButton = findViewById(R.id.bookmark_icon);
    }

    public void setName(String name){
        tvName.setText(name);
    }

    public void setFoodType(String foodType){
        tvfoodtype.setText(foodType);
    }

    public void setImage(String url){
        //Drawable mDrawable = loadImage(url);
        //imageView.setImageDrawable(mDrawable);

        //imageView.setImageResource(resId);

        Glide.with(this).load(url).error(R.drawable.title1).into(imageView);

    }


    private Drawable loadImage(String url){
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "srcName");
            return d;
        }catch (Exception e){
            return null;
        }
    }
}
