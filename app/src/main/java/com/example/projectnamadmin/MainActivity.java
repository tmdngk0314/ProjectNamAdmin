package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
    ImageButton btnOK;
    TextView new_account;
    EditText edt_inputid;
    EditText edt_inputpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOK = (ImageButton) findViewById(R.id.btnOk);
        new_account = (TextView)findViewById(R.id.new_account);
        new_account.setPaintFlags(new_account.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        edt_inputid = (EditText)findViewById(R.id.edt_inputid);
        edt_inputpw = (EditText)findViewById(R.id.edt_inputpw);

        // 푸시 알림 토큰 가져오기
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                CurrentLoggedInID.setFCMToken(newToken);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_id=edt_inputid.getText().toString();
                String input_pw=edt_inputpw.getText().toString();
                if(input_id.length()>0 && input_pw.length()>0){
                    CallRestApi apiCaller = new CallRestApi();
                    String result = apiCaller.login(input_id, input_pw);
                    switch(result){
                        case "success":
                            Log.i("로그인", "로그인 성공");
                            CurrentLoggedInID.isLoggedIn=true;
                            Toast.makeText(MainActivity.this, "반갑습니다!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "no match":
                            Toast.makeText(MainActivity.this, "아이디/패스워드 불일치", Toast.LENGTH_SHORT).show();
                            Log.e("로그인", "아이디/비밀번호 불일치");
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "서버 연결 오류", Toast.LENGTH_SHORT).show();
                            Log.e("로그인", "unknown:알 수 없는 오류");
                    }
                }
            }
        });

        btnOK.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btnOK.setBackgroundResource(R.drawable.ok_touch);
                        return false;
                    case MotionEvent.ACTION_UP:
                        btnOK.setBackgroundResource(R.drawable.ok);
                        return false;
                    default: return false;
                }

            }
        });

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        new_account.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        new_account.setTextColor(Color.GRAY);
                        return false;
                    case MotionEvent.ACTION_UP:
                        new_account.setTextColor(Color.BLACK);
                        return false;
                    default: return false;
                }

            }
        });
    }

    
}