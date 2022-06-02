package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class delaccount_pop_up_Activity extends AppCompatActivity {
    Button okbtn,cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delaccount_pop_up);

        okbtn = (Button) findViewById(R.id.okBtn);
        cancelbtn = (Button) findViewById(R.id.cancelBtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences deviceSettings = getSharedPreferences("deviceSettings", 0);
                CallRestApi apiCaller=new CallRestApi();
                String result = apiCaller.deleteAccount(deviceSettings);
                if(result.equals("success")){
                    Toast.makeText(delaccount_pop_up_Activity.this, "계정이 삭제되었습니다.\n앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(delaccount_pop_up_Activity.this);
                    System.exit(0);
                }else if(result.equals("not idle")){
                    Toast.makeText(delaccount_pop_up_Activity.this, "사물함 이용 중 계정을 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }else if(result.equals("diffIP")){
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(delaccount_pop_up_Activity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(delaccount_pop_up_Activity.this);
                    System.exit(0);
                }else if(result.equals("not verified")){
                    Toast.makeText(delaccount_pop_up_Activity.this, "인증이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(delaccount_pop_up_Activity.this, "unknown statement", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}