package com.example.justcook.itemAdapter;

public class RecipeItem {
    String name;//콩비지동그랑땡
    int rcode;
    String imglink;
    String foodtype; //foodtypename 부침 밥

    String summary;
    String type_name; //퓨전 서양 한식
    int time; //30(분)
    int calorie;
    String level;//어려움 초보환영 보통
    String base; //어류/패류


    public RecipeItem(String name, String foodtype, int rcode, String imglink){
        this.name = name;
        this.foodtype = foodtype;
        this.rcode = rcode;
        this.imglink = imglink;
    }

    public String getName() {
        return name;
    }
    public String getFoodtype() {
        return foodtype;
    }
    public int getRcode() {
        return rcode;
    }
    public String getImglink() {
        return imglink;
    }

}
