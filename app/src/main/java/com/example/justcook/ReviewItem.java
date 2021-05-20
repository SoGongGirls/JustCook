package com.example.justcook;

public class ReviewItem {
    // 얻어올 정보들을 저장한 클래스
    String rv_name;  // 아이디
    String rv_time;  // 작성 시간
    String rv_comment;  // 리뷰

    public ReviewItem(String rv_name, String rv_time, String rv_comment) {
        this.rv_name = rv_name;
        this.rv_time = rv_time;
        this.rv_comment = rv_comment;
    }

    public String getRv_name() {
        return rv_name;
    }

    public void setRv_name(String rv_name) {
        this.rv_name = rv_name;
    }

    public String getRv_time() {
        return rv_time;
    }

    public void setRv_time(String rv_time) {
        this.rv_time = rv_time;
    }

    public String getRv_comment() {
        return rv_comment;
    }

    public void setRv_comment(String rv_comment) {
        this.rv_comment = rv_comment;
    }
}