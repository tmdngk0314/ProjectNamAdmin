package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText currentPass_Edt,newPass_Edt,newPassCheck_Edt;
    TextView newPasstext,newPasschecktxt;
    RelativeLayout Repass,Renewpass,Renewpasscheck;
    ImageButton btn_ok,goSelectAct;
    boolean isAvailable_pw(String str){
        if(str.length()>16 || str.length()<8){
            return false;
        }
        if(str!=null  && str.matches("[a-z|A-Z|0-9|!|@|#|$|%|^|*|(|)]*")) {
            if(str.matches(".*[0-9].*")) {
                if (str.matches(".*[!|@|#|$|%|^|*|(|)].*"))
                    if(str.matches(".*[a-z|A-Z].*"))
                        return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPass_Edt = (EditText) findViewById(R.id.currentPass_edt);
        newPass_Edt = (EditText) findViewById(R.id.newPass_edt);
        newPassCheck_Edt = (EditText) findViewById(R.id.newpasscheck_edt);
        newPasstext = (TextView)findViewById(R.id.newPass_text);
        newPasschecktxt = (TextView)findViewById(R.id.newPasscheck_text);
        Repass = (RelativeLayout)findViewById(R.id.RePass);
        Renewpass = (RelativeLayout)findViewById(R.id.ChangePass);
        Renewpasscheck = (RelativeLayout)findViewById(R.id.newPassCheck);
        btn_ok=(ImageButton) findViewById(R.id.btnOk);
        goSelectAct = (ImageButton)findViewById(R.id.goSelectAct);
        btn_ok.setEnabled(false);

        goSelectAct.setOnClickListener(new View.OnClickListener() { // 뒤로가기
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newPass_Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                if(isAvailable_pw(txt)==false){
                    newPasstext.setVisibility(View.VISIBLE);
                    Renewpass.setBackgroundResource(R.drawable.border_red);
                    btn_ok.setEnabled(false);
                    btn_ok.setBackgroundResource(R.drawable.ok_fail);

                }
                else {
                    newPasstext.setVisibility(View.INVISIBLE);
                    Renewpass.setBackgroundResource(R.drawable.border_gray);
                    if(newPassCheck_Edt.getText().toString().equals(txt)){
                        btn_ok.setEnabled(true);
                        btn_ok.setBackgroundResource(R.drawable.ok);
                    }
                }

            }
        });
        newPassCheck_Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                String newPasstxt = newPass_Edt.getText().toString();
                int length = newPasstxt.length();

                if(txt.compareTo(newPasstxt)!=0){
                    newPasschecktxt.setVisibility(View.VISIBLE);
                    Renewpasscheck.setBackgroundResource(R.drawable.border_red);
                    btn_ok.setEnabled(false);
                    btn_ok.setBackgroundResource(R.drawable.ok_fail);
                }
                else{
                    newPasschecktxt.setVisibility(View.INVISIBLE);
                    Renewpasscheck.setBackgroundResource(R.drawable.border_gray);
                    if(isAvailable_pw(newPass_Edt.getText().toString()))
                        btn_ok.setEnabled(true);
                    btn_ok.setBackgroundResource(R.drawable.ok);
                }

            }
        });

        btn_ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btn_ok.setBackgroundResource(R.drawable.ok_touch);
                        return false;
                    case MotionEvent.ACTION_UP:
                        btn_ok.setBackgroundResource(R.drawable.ok);
                        return false;
                    default: return false;
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller= new CallRestApi();
                String result;
                result=apiCaller.changePassword(currentPass_Edt.getText().toString(), newPass_Edt.getText().toString());
                switch(result){
                    case "success":
                        SharedPreferences deviceSettings=getSharedPreferences("deviceSettings", 0);
                        SharedPreferences.Editor editor = deviceSettings.edit();
                        editor.putBoolean("isSaved", false);
                        editor.commit();
                        Toast.makeText(ChangePasswordActivity.this, "비밀번호가 변경되었습니다.\n앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                        apiCaller.logout();
                        ActivityCompat.finishAffinity(ChangePasswordActivity.this);
                        System.exit(0);
                        break;
                    case "diffIP":
                        Log.e("Login Session", "다른 기기에서 로그인되었음" );
                        Toast.makeText(ChangePasswordActivity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                        apiCaller.logout();
                        ActivityCompat.finishAffinity(ChangePasswordActivity.this);
                        System.exit(0);
                        break;
                    case "not verified":
                        Toast.makeText(ChangePasswordActivity.this, "인증이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "old pw incorrect":
                        Toast.makeText(ChangePasswordActivity.this, "기존 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(ChangePasswordActivity.this, "알 수 없는 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}