package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailVerificationActivity extends AppCompatActivity {

    private EditText edt_code;
    private Button btn_sendEmail;
    private ImageButton btn_verify,goSelectAct;
    private TextView tv_notice;
    private TextView tv_remaintime;
    Integer seconds_remains;
    public ScheduledExecutorService exeService;
    Boolean isServiceActivated=false;
    int a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        edt_code=(EditText)findViewById(R.id.edt_code);
        btn_sendEmail=(Button)findViewById(R.id.btn_sendEmail);
        btn_verify=(ImageButton)findViewById(R.id.btn_verify);
        tv_notice=(TextView) findViewById(R.id.tv_notice);
        tv_remaintime=(TextView)findViewById(R.id.tv_remaintime);
        goSelectAct = (ImageButton)findViewById(R.id.goSelectAct);
        Runnable runn = new Runnable() {
            @Override
            public void run() {
                //1초마다 동작시킬 코드
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(seconds_remains>0)
                                    seconds_remains--;
                                Integer mRemains, sRemains;
                                mRemains=seconds_remains/60;
                                sRemains=seconds_remains%60;
                                String sRemainsString=Integer.toString(sRemains);
                                if(seconds_remains<10)
                                    sRemainsString="0"+sRemainsString;
                                tv_remaintime.setText("남은 시간 : "+ Integer.toString(mRemains) +": "+sRemainsString);

                            }
                        });
                    }
                }).start();
            }
        };
        goSelectAct.setOnClickListener(new View.OnClickListener() { // 뒤로가기
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller=new CallRestApi();
                String result = apiCaller.sendVerifyingEmail();
                seconds_remains=120;
                if(result.equals("diffIP")){
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(EmailVerificationActivity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    moveTaskToBack(true);
                    finishAndRemoveTask();
                    System.exit(0);
                }
                else if(result.equals("success")) {
                    btn_verify.setVisibility(View.VISIBLE);
                    tv_notice.setVisibility(View.VISIBLE);
                    tv_remaintime.setVisibility(View.VISIBLE);
                    btn_sendEmail.setText("인증번호 재전송");


                    // 1초마다 실행하기 시작
                    exeService= Executors.newSingleThreadScheduledExecutor();
                    exeService.scheduleAtFixedRate(runn, 0,1, TimeUnit.SECONDS);
                    isServiceActivated=true;
                }

            }

        });
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller = new CallRestApi();
                String code=edt_code.getText().toString();
                if(code.isEmpty()){
                    Toast.makeText(EmailVerificationActivity.this, "인증번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String result=apiCaller.verifyingCode(code);
                if(result.equals("expired")){
                    Toast.makeText(EmailVerificationActivity.this, "인증번호가 만료되었습니다. 인증번호를 재전송해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("not match")){
                    Toast.makeText(EmailVerificationActivity.this, "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("diffIP")){
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    if(isServiceActivated==true)
                        exeService.shutdownNow();
                    Toast.makeText(EmailVerificationActivity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    moveTaskToBack(true);
                    finishAndRemoveTask();
                    System.exit(0);
                }
                else if(result.equals("success")){
                    if(isServiceActivated==true)
                        exeService.shutdownNow();
                    Toast.makeText(EmailVerificationActivity.this, "인증되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EmailVerificationActivity.this, ManageInfoActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btn_verify.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btn_verify.setBackgroundResource(R.drawable.ok_touch);
                        return false;
                    case MotionEvent.ACTION_UP:
                        btn_verify.setBackgroundResource(R.drawable.ok);
                        return false;
                    default: return false;
                }

            }
        });


    }
    @Override
    protected void onDestroy() {
        if(isServiceActivated==true)
            exeService.shutdownNow();
        super.onDestroy();
    }
}