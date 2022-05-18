package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class LockerListActivity extends AppCompatActivity {

    public ListView lockerlistView;
    private LockerListAdapter adapter;


    private String[] location = new String[20];
    private String[] lockername = new String[20];

    public LockerInfo lockerInfo = new LockerInfo();

    int OverrallList;

    CallRestApi apiCaller = new CallRestApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker_list);
        LockerClickListener lockerClickListener = new LockerClickListener(this);

        lockerlistView = (ListView) findViewById(R.id.lockerlistView);
        lockerInfo = apiCaller.loadLockerlist();

        try {
            OverrallList = apiCaller.receivedJSONObject.getInt("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

     /*   if(lockerInfo.result.equals("diffIP")){
            Log.e("Login Session", "다른 기기에서 로그인되었음" );
            Toast.makeText(this, "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
            finishAndRemoveTask();
            System.exit(0);
        }

        adapter = new LockerListAdapter(getApplicationContext(), lockerInfo,OverrallList);
        lockerlistView.setAdapter(adapter);

       lockerlistView.setOnItemClickListener(lockerClickListener);*/


    }
}