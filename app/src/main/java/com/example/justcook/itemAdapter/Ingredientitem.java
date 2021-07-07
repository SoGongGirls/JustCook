package com.example.justcook.itemAdapter;

public class Ingredientitem {
    String name;
    String quantity;
    String type_code_name;

    public Ingredientitem(String name, String quantity, String type_code_name) {
        this.name = name;
        this.quantity = quantity;
        this.type_code_name = type_code_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getType_code_name() {
        return type_code_name;
    }

    public void setType_code_name(String type_code_name) {
        this.type_code_name = type_code_name;
    }
}
