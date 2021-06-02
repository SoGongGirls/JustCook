package com.example.justcook;

public class tempRecipeItem {
    String name;
    String foodtypename;

    public tempRecipeItem(String name, String foodtypename) {
        this.name = name;
        this.foodtypename = foodtypename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodtypename() {
        return foodtypename;
    }

    public void setFoodtypename(String foodtypename) {
        this.foodtypename = foodtypename;
    }

    @Override
    public String toString() {
        return "tempRecipeItem{" +
                "name='" + name + '\'' +
                ", foodtypename='" + foodtypename + '\'' +
                '}';
    }
}
