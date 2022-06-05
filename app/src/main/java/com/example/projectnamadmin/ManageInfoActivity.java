package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ManageInfoActivity extends AppCompatActivity {
    RelativeLayout ChangePWrela;
    RelativeLayout Rela_reissueOTP, Rela_deleteAccount;
    TextView textname;
    TextView textemail;
    SharedPreferences deviceInfo;
    ImageButton im_delaccount, im_otpre, im_chagepw, goSelectAct;

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
    protected void onDestroy() {
        CallRestApi apiCaller = new CallRestApi();
        apiCaller.unverifyingCode();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_info);

        textname=(TextView)findViewById(R.id.textname);
        textemail=(TextView)findViewById(R.id.textEmail);
        textname.setText(CurrentLoggedInID.ID);
        textemail.setText(CurrentLoggedInID.email);
        ChangePWrela = (RelativeLayout)findViewById(R.id.ChangePWrela);
        Rela_reissueOTP=(RelativeLayout)findViewById(R.id.OTPReissurela);
        Rela_deleteAccount = (RelativeLayout)findViewById(R.id.AccountDelrela);
        im_chagepw = (ImageButton)findViewById(R.id.ChangePWimg);
        im_otpre = (ImageButton) findViewById(R.id.OTPReissuimg);
        im_delaccount = (ImageButton)findViewById(R.id.AccountDelimg);
        goSelectAct = (ImageButton)findViewById(R.id.goSelectAct);

        deviceInfo=getSharedPreferences("accountOTP", 0);

        goSelectAct.setOnClickListener(new View.OnClickListener() { // 뒤로가기
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ChangePWrela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageInfoActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        ChangePWrela.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event, ChangePWrela);

            }
        });
        im_chagepw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchReserve(event, ChangePWrela);
            }
        });

        Rela_reissueOTP.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event, Rela_reissueOTP);

            }
        });
        im_otpre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchReserve(event,Rela_reissueOTP);
            }
        });
        im_delaccount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchReserve(event, Rela_deleteAccount);
            }
        });

        Rela_deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageInfoActivity.this, delaccount_pop_up_Activity.class);
                startActivity(intent);
            }
        });

        Rela_deleteAccount.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                return onTouchReserve(event, Rela_deleteAccount);

            }
        });
        Rela_reissueOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller=new CallRestApi();
                String result=apiCaller.reissuanceotp(deviceInfo, CurrentLoggedInID.ID);
                if(result.equals("success")){
                    Toast.makeText(ManageInfoActivity.this, "OTP Key가 갱신되었습니다.", Toast.LENGTH_SHORT).show();
                }else if(result.equals("diffIP")){
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(ManageInfoActivity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    apiCaller.logout();
                    ActivityCompat.finishAffinity(ManageInfoActivity.this);
                    System.exit(0);
                }else if(result.equals("not verified")){
                    Toast.makeText(ManageInfoActivity.this, "본인인증이 진행되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    apiCaller.unverifyingCode();
                    finish();
                }else{
                    Toast.makeText(ManageInfoActivity.this, "server error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}