package com.example.justcook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CameraJson extends AppCompatActivity {

    TextView tv;
    TextView tv_ing_list;
    TextView tv_ing1;
    TextView tv_ing2;
    TextView tv_ing3;
    List<String> Ingredients = new ArrayList<String>();
    int btn1_cnt = 0;
    int btn2_cnt = 0;
    int btn3_cnt = 0;
    Button btn_cf_1;
    Button btn_cf_2;
    Button btn_cf_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_json);

        tv = findViewById(R.id.textView2);
        tv_ing_list = findViewById(R.id.textView);
        tv_ing1 = findViewById(R.id.tv_ingredient1);
        tv_ing2 = findViewById(R.id.tv_ingredient2);
        tv_ing3 = findViewById(R.id.tv_ingredient3);
        btn_cf_1 = findViewById(R.id.btn_confirm_1);
        btn_cf_2 = findViewById(R.id.btn_confirm_2);
        btn_cf_3 = findViewById(R.id.btn_confirm_3);
    }

    public void clickBtn(View view){
        AssetManager assetManager = getAssets();
        try{
            //지정한 json파일 이름으로 바꿔주기 나중에
            InputStream is = assetManager.open("tomato87.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line!=null){
                buffer.append(line+"\n");
                line = reader.readLine();
            }
            String jsonData = buffer.toString();
            //읽어온 json문자열 확인
            //tv.setText(jsonData);

            //json 분석
            //json 객체 생성
//            JSONObject jsonObject= new JSONObject(jsonData);
//            String name= jsonObject.getString("name");
//            String msg= jsonObject.getString("msg");
//
//            tv.setText("이름 : "+name+"\n"+"메세지 : "+msg);

            //json 데이터가 []로 시작하는 배열일때..
            JSONArray jsonArray = new JSONArray(jsonData);
            String s="";
            String[] labels = new String[jsonArray.length()];
            for (int i = 0; i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);

                String label = jo.getString("label");
                String confidence = jo.getString("confidence");
                JSONObject topleft = jo.getJSONObject("topleft");
                JSONObject bottomright = jo.getJSONObject("bottomright");
                int tx = topleft.getInt("x");
                int ty = topleft.getInt("y");
                int bx =  bottomright.getInt("x");
                int by = bottomright.getInt("y");

                s += label+", confidence : "+confidence+", topleft("+tx+", "+ty+"), bottonright("+bx+", "+by+") \n";
                labels[i] = label;
            }
            tv.setText(s);
            tv_ing1.setText(labels[0]);
            tv_ing2.setText(labels[1]);
            tv_ing3.setText(labels[2]);

        }catch (IOException e){e.printStackTrace();
        }catch(JSONException e){e.printStackTrace();}
    }

    public void clickCFBtn(View view){
        //목록 리스트에 재료 값들을 저장한다.
        switch(view.getId()){
            case R.id.btn_confirm_1:
                Log.v("실행", "1버튼 눌림");
                if (btn1_cnt ==0) {
                    //취소 상태이면 값 추가하고 1추가(확인 상태로 변경)
                    Ingredients.add((String) tv_ing1.getText());
                    btn_cf_1.setText("취소");
                    btn_cf_1.setBackgroundColor(Color.parseColor("#EA4F1A"));
                    btn1_cnt=1;
                }else if(btn1_cnt == 1){
                    //확인상태면 값 제거하고 1빼기(취소상태로 변경)
                    Ingredients.remove((String) tv_ing1.getText());
                    btn_cf_1.setText("확인");
                    btn_cf_1.setBackgroundColor(Color.parseColor("#FCA120"));
                    btn1_cnt=0;
                }
                break;
            case R.id.btn_confirm_2:
                Log.v("실행", "2버튼 눌림");
                if (btn2_cnt ==0) {
                    Ingredients.add((String) tv_ing2.getText());
                    btn_cf_2.setText("취소");
                    btn_cf_2.setBackgroundColor(Color.parseColor("#EA4F1A"));
                    btn2_cnt=1;
                    Log.v("👍확인to취소", "값 : " + btn1_cnt);
                }else if(btn2_cnt == 1){
                    Ingredients.remove((String) tv_ing2.getText());
                    btn_cf_2.setText("확인");
                    btn_cf_2.setBackgroundColor(Color.parseColor("#FCA120"));
                    btn2_cnt=0;
                    Log.v("👍취소to확인", "값 : " + btn1_cnt);
                }
                break;
            case R.id.btn_confirm_3:
                Log.v("실행", "3버튼 눌림");
                if (btn3_cnt ==0) {
                    Ingredients.add((String) tv_ing3.getText());
                    btn_cf_3.setText("취소");
                    btn_cf_3.setBackgroundColor(Color.parseColor("#EA4F1A"));
                    btn3_cnt=1;
                }else if(btn3_cnt == 1){
                    Ingredients.remove((String) tv_ing3.getText());
                    btn_cf_3.setText("확인");
                    btn_cf_3.setBackgroundColor(Color.parseColor("#FCA120"));
                    btn3_cnt=0;
                }
                break;
        }
    }
    public void clickIngList(View view){
        Log.v("실행", "목록 확인 눌림");
        String temp = "";
        for (String ing : Ingredients){
            temp += (String) ing;
            temp += ", ";
        }
        tv_ing_list.setText(temp);
    }

}