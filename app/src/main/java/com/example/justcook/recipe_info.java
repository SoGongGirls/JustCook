package com.example.justcook;
/* recipe2.db  : recipe_info_ver4 */
public class recipe_info {
    public int rcode; //음식번호
    public String name; // 계정명
    public String summary; // 한줄설명?
    public int type; // 한중일양식 필터 코드
    public String type_name; // 한식 퓨전 서양 등등
    public int food_type; // 부침 밥 과자 세부 필터 코드
    public String food_type_name; // 부침 밥 과자 등등
    public int time;//조리시간
    public int calorie;
    public int person;//n인분의 n
    public String level;//초보환영 보통 어려움
    public String base;// 곡류, 밀가류 어패류 이런거

    public int getRcode() {
        return rcode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getFood_type() {
        return food_type;
    }

    public void setFood_type(int food_type) {
        this.food_type = food_type;
    }

    public String getFood_type_name() {
        return food_type_name;
    }

    public void setFood_type_name(String food_type_name) {
        this.food_type_name = food_type_name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String img_url;

    // TODO : get,set 함수 생략

}
