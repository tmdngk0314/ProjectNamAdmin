package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CheckLockerActivity extends AppCompatActivity {
    private ListView checklockerlist;
    private CheckLockerAdapter adapter;
    private List<CheckLockerInfo> lockerinfolist;
    String selectdate;
    TextView txt_lockername, txt_lockeraddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_locker);
        txt_lockername = (TextView)findViewById(R.id.txt_lockername);
        txt_lockeraddress = (TextView)findViewById(R.id.txt_lockeraddress);
        selectdate = getIntent().getExtras().getString("날짜");

        txt_lockername.setText(selectdate);

        checklockerlist = (ListView)findViewById(R.id.lockerlistView);
        lockerinfolist = new ArrayList<CheckLockerInfo>();
        lockerinfolist.add(new CheckLockerInfo("1", "예약 가능 합니다."));
        lockerinfolist.add(new CheckLockerInfo("2", "이용중입니다."));
        lockerinfolist.add(new CheckLockerInfo("3", "예약된 상태 입니다."));
        lockerinfolist.add(new CheckLockerInfo("4", "예약 가능 합니다."));

        adapter = new CheckLockerAdapter(getApplicationContext(), lockerinfolist);
        checklockerlist.setAdapter(adapter);


    }
}