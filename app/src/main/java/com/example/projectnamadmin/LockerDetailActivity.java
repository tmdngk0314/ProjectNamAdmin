package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LockerDetailActivity extends AppCompatActivity {
    private int lockerSize;
    private int pageValue = 1;
    private int pageOffset=10;

    public ListView checklockerlist;
    private LockerDetailAdapter adapter;
    private List<LockerDetailInfo> lockerDetailInfo;

    public Button[] pageBtn = new Button[7];

    private int[] pageBtnName = {
            R.id.pageBtnLeft, R.id.pageBtn1, R.id.pageBtn2, R.id.pageBtn3,
            R.id.pageBtn4, R.id.pageBtn5, R.id.pageBtn6};


    TextView txt_lockername, txt_lockeraddress,popup_lockernum, txt_status, txt_name, txt_id, txt_email, txt_startdate, txt_enddate;
    LockerStatusInfo statusInfo;
    CallRestApi apiCaller = new CallRestApi();
    RelativeLayout Rela_status,Rela_locker;
    LoadLockerDetails loadLockerDetails;
    ImageButton btn_reservecancel, btn_ok, btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_detail);
        checklockerlist = (ListView)findViewById(R.id.lockerlistView);
        txt_lockername = (TextView)findViewById(R.id.txt_lockername);
        txt_lockeraddress = (TextView)findViewById(R.id.txt_lockeraddress);
        popup_lockernum = (TextView)findViewById(R.id.popup_lockernum);
        txt_status = (TextView)findViewById(R.id.txt_status);
        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_id = (TextView)findViewById(R.id.txt_id);
        txt_email = (TextView)findViewById(R.id.txt_email);
        txt_startdate = (TextView)findViewById(R.id.txt_startdate);
        txt_enddate = (TextView)findViewById(R.id.txt_enddate);
        Rela_status = (RelativeLayout)findViewById(R.id.Rela_status);
        Rela_locker = (RelativeLayout)findViewById(R.id.Rela_locker);
        btn_reservecancel = (ImageButton)findViewById(R.id.btn_reservecancel);
        btn_ok = (ImageButton)findViewById(R.id.btn_ok);
        btn_back = (ImageButton)findViewById(R.id.goSelectAct);

        statusInfo = apiCaller.loadLockerStatus();

        PageChangeActivity pageChangeActivity = new PageChangeActivity(pageBtn);
        ItemClickListener itemClickListener = new ItemClickListener(this);

        lockerDetailInfo = new ArrayList<LockerDetailInfo>();

        txt_lockername.setText(CurrentLoggedInID.getLockername());
        txt_lockeraddress.setText(CurrentLoggedInID.getLocation());

        if(statusInfo.result.equals("diffIP")){
            Log.e("Login Session", "다른 기기에서 로그인되었음" );
            Toast.makeText(this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.finishAffinity(LockerDetailActivity.this);
            System.exit(0);
        }
        if(statusInfo.result.equals("success")){
            for(int i = 1; i <= statusInfo.getCount(); i++){
                lockerDetailInfo.add(new LockerDetailInfo(i, statusInfo.getStatus(i)));
                adapter = new LockerDetailAdapter(getApplicationContext(), lockerDetailInfo);
                checklockerlist.setAdapter(adapter);
            }

        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rela_locker.setVisibility(View.INVISIBLE);
                checklockerlist.setEnabled(true);
                btn_back.setEnabled(true);
            }
        });
        btn_ok.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                switch(event.getAction()){
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


        checklockerlist.setOnItemClickListener(itemClickListener);


    }
}