package com.example.projectnamadmin;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class WriteNoticeActivity extends AppCompatActivity {
    private ImageButton btn_back;
    private EditText edt_title;
    private EditText edt_body;
    private CheckBox chk_push;
    private ImageButton btn_upload;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notice);

        btn_back=findViewById(R.id.btn_back);
        edt_title=findViewById(R.id.edt_title);
        edt_body=findViewById(R.id.edt_body);
        chk_push=findViewById(R.id.chk_push);
        btn_upload=findViewById(R.id.btn_upload);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteNoticeActivity.this, NoticeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=edt_title.getText().toString();
                String body=edt_body.getText().toString();
                String title1=title.replaceAll("'", "\\\\'");
                String title2=title1.replaceAll("\"", "\\\\\"");
                String body1=body.replaceAll("'", "\\\\'");
                String body2=body1.replaceAll("\"", "\\\\\"");
                Log.e("body", body2);
                Boolean ispush=chk_push.isChecked();
                if(title.length()<2){
                    Toast.makeText(WriteNoticeActivity.this, "제목을 2자 이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(body.length()<5){
                    Toast.makeText(WriteNoticeActivity.this, "내용을 5자 이상 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                CallRestApi apiCaller = new CallRestApi();
                String result=apiCaller.uploadNotice(title2, body2, ispush);
                if(result.equals("success")){
                    Toast.makeText(WriteNoticeActivity.this, "공지사항이 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(WriteNoticeActivity.this, NoticeActivity.class);
                    startActivity(intent);
                    finish();
                }else if(result.equals("diffIP")){
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(WriteNoticeActivity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(WriteNoticeActivity.this);
                    System.exit(0);
                }else{
                    Toast.makeText(WriteNoticeActivity.this, "공지사항 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}