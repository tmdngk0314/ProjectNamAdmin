package com.example.projectnamadmin;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

public class PageChangeActivity {
    Button[] pageBtn;

    public PageChangeActivity(Button[] pageBtn) {
        this.pageBtn = pageBtn;
    }

    public void setPage(int pageNow, int pageMax) {
        // 1~5페이지는 <가 없다
        int madeFive = 5 - (pageNow % 5);
        int startPage = pageNow - (pageNow % 5) + 1;
        if ( pageNow % 5 == 0){
            madeFive = 0;
            startPage = pageNow - 4;
        }


        for(int i=0;i<7;i++){
            pageBtn[i].setVisibility(VISIBLE);
            pageBtn[i].setTextColor(Color.parseColor("#888888"));
            pageBtn[i].setClickable(true);
        }

        if (pageNow - 5 <= 0){
            pageBtn[0].setVisibility(INVISIBLE);
            pageBtn[0].setClickable(false);
        }


        for(int i=0;i<5;i++) {
            pageBtn[5-i].setText(Integer.toString(pageNow+madeFive-i));
        }

        if ((pageNow + madeFive) * 10 >= pageMax){
            pageBtn[6].setVisibility(INVISIBLE);
            pageBtn[6].setClickable(false);
            for(int i=0; (pageNow+madeFive-i - 1)*10>=pageMax;i++){
                pageBtn[5-i].setVisibility(INVISIBLE);
                pageBtn[5-i].setClickable(false);
            }
        }

        pageBtn[pageNow - startPage + 1].setTextColor(Color.parseColor("#000000"));
    }

}
