package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class SelectActivity extends AppCompatActivity {
    RelativeLayout firstRela, secondRela, thirdRela, forthRela;
    ImageButton lockermanage, noticemanage, otpcheck, overdue;

    public boolean onTouchReserve(MotionEvent event, RelativeLayout a){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                a.setBackgroundResource(R.drawable.select_box_touch);
                return false;
            case MotionEvent.ACTION_UP:
                a.setBackgroundResource(R.drawable.select_box);
                return false;
            default: return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        firstRela = (RelativeLayout) findViewById(R.id.firstRela);
        secondRela = (RelativeLayout) findViewById(R.id.secondRela);
        thirdRela = (RelativeLayout)findViewById(R.id.thirdRela);
        forthRela = (RelativeLayout)findViewById(R.id.forthRela);
        lockermanage = (ImageButton) findViewById(R.id.img_lockermanage);
        noticemanage = (ImageButton) findViewById(R.id.img_noticemanage);
        otpcheck = (ImageButton)findViewById(R.id.img_otpcheck);
        overdue = (ImageButton)findViewById(R.id.img_overdue);


        firstRela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,firstRela);

            }
        });
        firstRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, SelectDayActivity.class);
                startActivity(intent);
            }
        });

        thirdRela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,thirdRela);

            }
        });
        thirdRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, OtpActivity.class);
                //otp 액티비티로 바꿔야함
                startActivity(intent);
            }
        });

        secondRela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,secondRela);

            }
        });
        lockermanage.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,firstRela);

            }
        });
        lockermanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, SelectDayActivity.class);
                startActivity(intent);
            }
        });
        noticemanage.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,secondRela);

            }
        });
        otpcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, OtpActivity.class);
                //otp액티비티로 바꿔야함
                startActivity(intent);
            }
        });
        otpcheck.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,thirdRela);

            }
        });


    }
}