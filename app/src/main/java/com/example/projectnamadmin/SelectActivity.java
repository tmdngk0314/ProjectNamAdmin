package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SelectActivity extends AppCompatActivity {
    RelativeLayout firstRela, secondRela, thirdRela, forthRela, fifthRela;
    ImageButton lockermanage, noticemanage, otpcheck, overdue, btn_logout, btn_admininfo;
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
        fifthRela = (RelativeLayout)findViewById(R.id.fifthRela);
        lockermanage = (ImageButton) findViewById(R.id.img_lockermanage);
        noticemanage = (ImageButton) findViewById(R.id.img_noticemanage);
        otpcheck = (ImageButton)findViewById(R.id.img_otpcheck);
        overdue = (ImageButton)findViewById(R.id.img_overdue);
        btn_logout=(ImageButton)findViewById(R.id.imgBtnLogout);
        btn_admininfo = (ImageButton)findViewById(R.id.btn_admininfo);

        CallRestApi apiCaller = new CallRestApi();
        apiCaller.setFCMToken();


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller = new CallRestApi();
                String result=apiCaller.logout();
                if(result.equals("success")){
                    CurrentLoggedInID.resetInfo();
                    Toast.makeText(SelectActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        firstRela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,firstRela);

            }
        });
        firstRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, LockerDetailActivity.class);
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
                startActivity(intent);
            }
        });

        secondRela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,secondRela);

            }
        });
        secondRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
        fifthRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, EmailVerificationActivity.class);
                startActivity(intent);
            }
        });
        btn_admininfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, EmailVerificationActivity.class);
                startActivity(intent);
            }
        });
        fifthRela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,fifthRela);

            }
        });
        btn_admininfo.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event,fifthRela);

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
                Intent intent = new Intent(SelectActivity.this, LockerDetailActivity.class);
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
        overdue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return onTouchReserve(event, forthRela);
            }
        });
        forthRela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return onTouchReserve(event, forthRela);
            }
        });
        forthRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectActivity.this, OverdueLockerManageActivity.class);
                startActivity(intent);
            }
        });
        overdue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectActivity.this, OverdueLockerManageActivity.class);
                startActivity(intent);
            }
        });



        startService(new Intent(this, ForcedTerminationService.class)); // 앱 강제종료 시 로그아웃하는 서비스


    }

    @Override
    protected void onDestroy() {
        if(CurrentLoggedInID.isLoggedIn==true) {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            CallRestApi apiCaller = new CallRestApi();
            apiCaller.logout();
            CurrentLoggedInID.resetInfo();
        }
        super.onDestroy();
    }
}