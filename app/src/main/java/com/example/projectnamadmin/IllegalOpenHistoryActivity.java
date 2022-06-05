package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IllegalOpenHistoryActivity extends AppCompatActivity {
    private int illegalCount;
    private int pageValue=1;
    private int pageOffset=10;

    private TextView txt_lockername;
    private TextView txt_location;

    public ListView illegalView;
    private IllegalOpenListAdapter adapter;

    public Button[] pageBtn = new Button[7];
    private int[] pageBtnName = {
            R.id.pageBtnLeft, R.id.pageBtn1, R.id.pageBtn2, R.id.pageBtn3,
            R.id.pageBtn4, R.id.pageBtn5, R.id.pageBtn6
    };
    public IllegalOpenHistoryInfo illegalInfo = new IllegalOpenHistoryInfo();

    CallRestApi apiCaller = new CallRestApi();
    ImageButton goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illegal_open_history);
        
        illegalView=(ListView) findViewById(R.id.listview_illegalOpenHistory);
        goBack=(ImageButton)findViewById(R.id.goBack);
        txt_lockername=(TextView) findViewById(R.id.txt_lockername);
        txt_location=(TextView) findViewById(R.id.txt_lockeraddress);
        for (int i = 0; i < 7; i++) pageBtn[i] = (Button) findViewById(pageBtnName[i]);


        txt_lockername.setText(CurrentLoggedInID.getLockername());
        txt_location.setText(CurrentLoggedInID.getLocation());

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        illegalInfo = apiCaller.loadIllegalOpenHistory();
        illegalCount=illegalInfo.count;
        if(illegalInfo.result.equals("diffIP")){
            Log.e("Login Session", "다른 기기에서 로그인되었음" );
            Toast.makeText(this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.finishAffinity(IllegalOpenHistoryActivity.this);
            System.exit(0);
        }else if(!illegalInfo.result.equals("success")){
            Log.e("server error", "서버 오류" );
            Toast.makeText(this, "서버 오류.", Toast.LENGTH_SHORT).show();
            apiCaller.logout();
            ActivityCompat.finishAffinity(IllegalOpenHistoryActivity.this);
            System.exit(0);
        }

        ///////////////////////////////////////////////
        if( illegalCount /10 == pageValue-1) pageOffset = illegalCount - (pageValue-1)*10;
        else pageOffset = 10;

        adapter = new IllegalOpenListAdapter(getApplicationContext(), illegalInfo, pageValue, pageOffset, illegalCount);
        illegalView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭

        PageChangeActivity pageChange = new PageChangeActivity(pageBtn);
        pageChange.setPage(pageValue, illegalCount);

        for (int i = 1; i < 6; i++) { // 페이지 전환
            pageBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pageComp;
                    Button btn = findViewById(v.getId());
                    pageComp = Integer.parseInt(btn.getText().toString());
                    if (pageComp != pageValue) {
                        pageValue = pageComp;
                        illegalInfo = apiCaller.loadIllegalOpenHistory();
                        illegalCount=illegalInfo.count;
                        if( illegalCount /10 == pageValue-1) pageOffset = illegalCount - (pageValue-1)*10;
                        else pageOffset = 10;
                        pageChange.setPage(pageValue, illegalCount);
                        illegalView.smoothScrollToPositionFromTop(0, 10, 300);
                        adapter.putInfo(illegalInfo, pageValue, pageOffset, illegalCount);
                        illegalView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                        illegalView.smoothScrollToPositionFromTop(0, 10, 300);
                    }
                }
            });
        }
        pageBtn[6].setOnClickListener(new View.OnClickListener() { // 다음 5페이지
            @Override
            public void onClick(View v) {
                if (pageValue % 5 == 0) pageValue += 1;
                else pageValue = pageValue + 6 - (pageValue % 5);
                illegalInfo= apiCaller.loadIllegalOpenHistory();
                illegalCount = illegalInfo.count;
                if( illegalCount /10 == pageValue-1) pageOffset = illegalCount - (pageValue-1)*10;
                else pageOffset = 10;
                pageChange.setPage(pageValue, illegalCount);
                adapter.putInfo(illegalInfo, pageValue, pageOffset, illegalCount);
                illegalView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                illegalView.smoothScrollToPositionFromTop(0, 10, 300);
            }
        });
        pageBtn[0].setOnClickListener(new View.OnClickListener() { // 이전 5페이지
            @Override
            public void onClick(View v) {
                if (pageValue % 5 == 0) pageValue -= 5;
                else pageValue = pageValue - (pageValue % 5);
                illegalInfo= apiCaller.loadIllegalOpenHistory();
                illegalCount = illegalInfo.count;
                if( illegalCount /10 == pageValue-1) pageOffset = illegalCount - (pageValue-1)*10;
                else pageOffset = 10;
                pageChange.setPage(pageValue, illegalCount);
                adapter.putInfo(illegalInfo, pageValue, pageOffset, illegalCount);
                illegalView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                illegalView.smoothScrollToPositionFromTop(0, 10, 300);
            }
        });
        
    }
}