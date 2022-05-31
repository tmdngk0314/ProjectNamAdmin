package com.example.projectnamadmin;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;

public class NoticeActivity extends AppCompatActivity {

    private int noticeMax;
    private int pageValue = 1;
    private int pageOffset=10;

    public ListView noticeListView;
    private NoticeListAdapter adapter;

    private Integer[] index = new Integer[10];
    private String[] title = new String[10];
    private String[] date = new String[10];
    private String[] body = new String[10];

    public Button[] pageBtn = new Button[7];

    private int[] pageBtnName = {
            R.id.pageBtnLeft, R.id.pageBtn1, R.id.pageBtn2, R.id.pageBtn3,
            R.id.pageBtn4, R.id.pageBtn5, R.id.pageBtn6};

    public NoticeInfo noticeInfo = new NoticeInfo();

    public TextView noticeTitle, noticeDate, noticeBody;
    public RelativeLayout noticeRelative;
    public ImageButton noticeExitBtn, goSelectAct, noticeDeleteBtn;
    public ImageButton btn_newNotice;

    public Integer selectedIndex=0;

    CallRestApi apiCaller = new CallRestApi();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeTitle= (TextView)findViewById(R.id.noticeTitle);
        noticeDate = (TextView)findViewById(R.id.noticeDate);
        noticeBody = (TextView)findViewById(R.id.noticeBody);
        noticeRelative = (RelativeLayout)findViewById(R.id.noticeRelative);
        noticeExitBtn = (ImageButton)findViewById(R.id.noticeExit);
        btn_newNotice = (ImageButton)findViewById(R.id.btn_newNotice);
        noticeDeleteBtn = (ImageButton)findViewById(R.id.noticeDelete);
        NoticeItemClickListener noticeitemClickListener = new NoticeItemClickListener(this);
        PageChangeActivity pageChange = new PageChangeActivity(pageBtn);

        goSelectAct = (ImageButton)findViewById(R.id.goSelectAct) ;

        for (int i = 0; i < 7; i++) pageBtn[i] = (Button) findViewById(pageBtnName[i]);

        btn_newNotice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Integer action=motionEvent.getAction();
                if(action==MotionEvent.ACTION_DOWN){
                    btn_newNotice.setBackgroundResource(R.drawable.newnotice_touch);
                }else if(action==MotionEvent.ACTION_UP){
                    btn_newNotice.setBackgroundResource(R.drawable.newnotice);
                }
                return false;
            }
        });
        btn_newNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoticeActivity.this, WriteNoticeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        try {
            apiCaller.getRestAPI("notice/loadcount");
            noticeMax = apiCaller.receivedJSONObject.getInt("maxindex");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        noticeInfo = apiCaller.loadNotice(pageValue);

        if(noticeInfo.result.equals("diffIP")){
            Log.e("Login Session", "다른 기기에서 로그인되었음" );
            Toast.makeText(this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.finishAffinity(NoticeActivity.this);
            System.exit(0);
        }

        if( noticeMax/10 == pageValue-1) pageOffset = noticeMax - (pageValue-1)*10;
        else pageOffset = 10;
        NoticeInfo swap = new NoticeInfo();
        for(int j=0;j<pageOffset;j++){
            swap.index[j] = noticeInfo.index[9-j-(10-pageOffset)];
            swap.title[j] = noticeInfo.title[9-j-(10-pageOffset)];
            swap.date[j] = noticeInfo.date[9-j-(10-pageOffset)];
            swap.body[j] = noticeInfo.body[9-j-(10-pageOffset)];
        }
        noticeInfo = swap;

        adapter = new NoticeListAdapter(getApplicationContext(), noticeInfo, pageValue, pageOffset, noticeMax);
        noticeListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭

        pageChange.setPage(pageValue, noticeMax);

        for (int i = 1; i < 6; i++) {
            pageBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pageComp;
                    Button btn = findViewById(v.getId());
                    pageComp = Integer.parseInt(btn.getText().toString());
                    if (pageComp != pageValue) {
                        pageValue = pageComp;
                        try {
                            apiCaller.getRestAPI("notice/loadcount");
                            noticeMax = apiCaller.receivedJSONObject.getInt("maxindex");

                        } catch (Exception e) {
                            Log.e("Notice Activity","페이지 버튼 클릭 오류");
                            e.printStackTrace();
                        }
                        noticeInfo = apiCaller.loadNotice(pageValue);

                        if( noticeMax/10 == pageValue-1) pageOffset = noticeMax - (pageValue-1)*10;
                        else pageOffset = 10;
                        NoticeInfo swap = new NoticeInfo();
                        for(int j=0;j<pageOffset;j++){
                            swap.index[j] = noticeInfo.index[9-j-(10-pageOffset)];
                            swap.title[j] = noticeInfo.title[9-j-(10-pageOffset)];
                            swap.date[j] = noticeInfo.date[9-j-(10-pageOffset)];
                            swap.body[j] = noticeInfo.body[9-j-(10-pageOffset)];
                        }
                        noticeInfo = swap;

                        pageChange.setPage(pageValue, noticeMax);
                        noticeListView.smoothScrollToPositionFromTop(0, 10, 300);
                        adapter.putInfo(noticeInfo, pageValue, pageOffset, noticeMax);
                        noticeListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                        noticeListView.smoothScrollToPositionFromTop(0, 10, 300);
                    }
                }
            });
        }
        pageBtn[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageValue % 5 == 0) pageValue += 1;
                else pageValue = pageValue + 6 - (pageValue % 5);
                try {
                    apiCaller.getRestAPI("notice/loadcount");
                    noticeMax = apiCaller.receivedJSONObject.getInt("maxindex");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                noticeInfo = apiCaller.loadNotice(pageValue);
                if( noticeMax/10 == pageValue-1) pageOffset = noticeMax - (pageValue-1)*10;
                else pageOffset = 10;
                NoticeInfo swap = new NoticeInfo();
                for(int j=0;j<pageOffset;j++){
                    swap.index[j] = noticeInfo.index[9-j-(10-pageOffset)];
                    swap.title[j] = noticeInfo.title[9-j-(10-pageOffset)];
                    swap.date[j] = noticeInfo.date[9-j-(10-pageOffset)];
                    swap.body[j] = noticeInfo.body[9-j-(10-pageOffset)];
                }
                noticeInfo = swap;
                pageChange.setPage(pageValue, noticeMax);
                adapter.putInfo(noticeInfo, pageValue, pageOffset, noticeMax);
                noticeListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                noticeListView.smoothScrollToPositionFromTop(0, 10, 300);
            }
        });
        pageBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageValue % 5 == 0) pageValue -= 5;
                else pageValue = pageValue - (pageValue % 5);
                try {
                    apiCaller.getRestAPI("notice/loadcount");
                    noticeMax = apiCaller.receivedJSONObject.getInt("maxindex");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                noticeInfo = apiCaller.loadNotice(pageValue);
                if( noticeMax/10 == pageValue-1) pageOffset = noticeMax - (pageValue-1)*10;
                else pageOffset = 10;
                NoticeInfo swap = new NoticeInfo();
                for(int j=0;j<pageOffset;j++){
                    swap.index[j] = noticeInfo.index[9-j-(10-pageOffset)];
                    swap.title[j] = noticeInfo.title[9-j-(10-pageOffset)];
                    swap.date[j] = noticeInfo.date[9-j-(10-pageOffset)];
                    swap.body[j] = noticeInfo.body[9-j-(10-pageOffset)];
                }
                noticeInfo = swap;
                pageChange.setPage(pageValue, noticeMax);
                adapter.putInfo(noticeInfo, pageValue, pageOffset, noticeMax);
                noticeListView.setAdapter(adapter);  //리스트 뷰에 해당 어뎁터 매칭
                noticeListView.smoothScrollToPositionFromTop(0, 10, 300);
            }
        });
        noticeListView.setOnItemClickListener(noticeitemClickListener);
        noticeExitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeRelative.setVisibility(View.INVISIBLE);
                for(int i=0;i<7;i++){
                    pageBtn[i].setClickable(true);
                }
                //noticeListView.setOnItemClickListener(noticeitemClickListener);
                noticeListView.setEnabled(true);
            }
        });
        goSelectAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noticeDeleteBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Integer action=motionEvent.getAction();
                if(action==MotionEvent.ACTION_DOWN){
                    noticeDeleteBtn.setBackgroundResource(R.drawable.noticedel_touch);
                }else if(action==MotionEvent.ACTION_UP){
                    noticeDeleteBtn.setBackgroundResource(R.drawable.notice_del);
                }
                return false;
            }
        });
        noticeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller = new CallRestApi();
                Log.e("NoticeActivity", "selectedIndex:"+Integer.toString(selectedIndex));
                String result=apiCaller.delete_notice(selectedIndex);
                if(result.equals("success")){
                    ActivityCompat.recreate(NoticeActivity.this);
                }else if(result.equals("diffIP")){
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(NoticeActivity.this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(NoticeActivity.this);
                    System.exit(0);
                }else{
                    Toast.makeText(NoticeActivity.this, "공지사항 삭제를 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        noticeExitBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Integer action=motionEvent.getAction();
                if(action==MotionEvent.ACTION_DOWN){
                    noticeExitBtn.setBackgroundResource(R.drawable.noticeok_touch);
                }else if(action==MotionEvent.ACTION_UP){
                    noticeExitBtn.setBackgroundResource(R.drawable.notice_ok);
                }
                return false;
            }
        });
    }
}
