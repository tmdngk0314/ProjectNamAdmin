package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

public class OverdueLockerManageActivity extends AppCompatActivity {
    private int overdueCount;
    private int pageValue=1;
    private int pageOffset=10;

    public ListView overdueListView;
    private OverdueListAdapter adapter;

    public Button[] pageBtn = new Button[7];
    private int[] pageBtnName = {
            R.id.pageBtnLeft, R.id.pageBtn1, R.id.pageBtn2, R.id.pageBtn3,
            R.id.pageBtn4, R.id.pageBtn5, R.id.pageBtn6
    };
    public OverdueListInfo overdueInfo = new OverdueListInfo();

    CallRestApi apiCaller = new CallRestApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_locker_manage);

        overdueListView=(ListView)findViewById(R.id.overdueListView);

        for (int i = 0; i < 7; i++) pageBtn[i] = (Button) findViewById(pageBtnName[i]);

        overdueInfo = apiCaller.loadOverdueList();
        overdueCount=overdueInfo.count;
        if(overdueInfo.result.equals("diffIP")){
            Log.e("Login Session", "다른 기기에서 로그인되었음" );
            Toast.makeText(this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.finishAffinity(OverdueLockerManageActivity.this);
            System.exit(0);
        }else if(!overdueInfo.result.equals("success")){
            Log.e("server error", "서버 오류" );
            Toast.makeText(this, "서버 오류.", Toast.LENGTH_SHORT).show();
            apiCaller.logout();
            ActivityCompat.finishAffinity(OverdueLockerManageActivity.this);
            System.exit(0);
        }

        ///////////////////////////////////////////////
        if( overdueCount /10 == pageValue-1) pageOffset = overdueCount - (pageValue-1)*10;
        else pageOffset = 10;

        adapter = new OverdueListAdapter(getApplicationContext(), overdueInfo, pageValue, pageOffset, overdueCount);
        overdueListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭

        PageChangeActivity pageChange = new PageChangeActivity(pageBtn);
        pageChange.setPage(pageValue, overdueCount);

        for (int i = 1; i < 6; i++) { // 페이지 전환
            pageBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pageComp;
                    Button btn = findViewById(v.getId());
                    pageComp = Integer.parseInt(btn.getText().toString());
                    if (pageComp != pageValue) {
                        pageValue = pageComp;
                        overdueInfo = apiCaller.loadOverdueList();
                        overdueCount=overdueInfo.count;
                        if( overdueCount /10 == pageValue-1) pageOffset = overdueCount - (pageValue-1)*10;
                        else pageOffset = 10;
                        pageChange.setPage(pageValue, overdueCount);
                        overdueListView.smoothScrollToPositionFromTop(0, 10, 300);
                        adapter.putInfo(overdueInfo, pageValue, pageOffset, overdueCount);
                        overdueListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                        overdueListView.smoothScrollToPositionFromTop(0, 10, 300);
                    }
                }
            });
        }
        pageBtn[6].setOnClickListener(new View.OnClickListener() { // 다음 5페이지
            @Override
            public void onClick(View v) {
                if (pageValue % 5 == 0) pageValue += 1;
                else pageValue = pageValue + 6 - (pageValue % 5);
                overdueInfo= apiCaller.loadOverdueList();
                overdueCount = overdueInfo.count;
                if( overdueCount /10 == pageValue-1) pageOffset = overdueCount - (pageValue-1)*10;
                else pageOffset = 10;
                pageChange.setPage(pageValue, overdueCount);
                adapter.putInfo(overdueInfo, pageValue, pageOffset, overdueCount);
                overdueListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                overdueListView.smoothScrollToPositionFromTop(0, 10, 300);
            }
        });
        pageBtn[0].setOnClickListener(new View.OnClickListener() { // 이전 5페이지
            @Override
            public void onClick(View v) {
                if (pageValue % 5 == 0) pageValue -= 5;
                else pageValue = pageValue - (pageValue % 5);
                overdueInfo= apiCaller.loadOverdueList();
                overdueCount = overdueInfo.count;
                if( overdueCount /10 == pageValue-1) pageOffset = overdueCount - (pageValue-1)*10;
                else pageOffset = 10;
                pageChange.setPage(pageValue, overdueCount);
                adapter.putInfo(overdueInfo, pageValue, pageOffset, overdueCount);
                overdueListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                overdueListView.smoothScrollToPositionFromTop(0, 10, 300);
            }
        });

        /*goSelectAct.setOnClickListener(new View.OnClickListener() { // 뒤로가기
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        ///////////////////////////////////////////////

    }
}