package com.example.projectnamadmin;

import android.view.MotionEvent;
import android.view.View;

public class OkTouch implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundResource(R.drawable.ok_touch);
                break;
            case MotionEvent.ACTION_UP:
                v.setBackgroundResource(R.drawable.ok);
                break;
        }
        return false;
    }
}
