package com.example.justcook;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SearchRealRcode {
    String TAG = "SearchRealRcode";

    public static ArrayList<Integer> searchVal(ArrayList<Integer> oriL, ArrayList<ArrayList> L){
        Log.v("SearchRealRcode", "여러개 비교 실행");
        for(ArrayList<Integer> smallL:L){
            oriL.retainAll(smallL);
            if (oriL.size() == 0) break;
        }
        Log.v("SearchRealRcode", oriL.size()+"개 일치");
        return oriL;
    }



}
