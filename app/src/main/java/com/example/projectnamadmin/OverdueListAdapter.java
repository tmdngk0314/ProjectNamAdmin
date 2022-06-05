package com.example.projectnamadmin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class OverdueListAdapter extends BaseAdapter {
    private Context context;
    private OverdueListInfo overdueInfo;
    int pageValue, pageOffset, overduecount;
    int index=0;


    public void putInfo(OverdueListInfo overdueInfo, int pageValue, int pageOffset, int overduecount){
        this.overdueInfo=overdueInfo;
        this.pageValue=pageValue;
        this.pageOffset=pageOffset;
        this.overduecount=overduecount;
    }

    public OverdueListAdapter(Context context, OverdueListInfo overdueInfo, int pageValue, int pageOffset, int overduecount) {
        this.context = context;
        this.overdueInfo = overdueInfo;
        this.pageValue = pageValue;
        this.pageOffset = pageOffset;
        this.overduecount = overduecount;
    }

    @Override
    public int getCount() {
        return pageOffset;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return (pageValue-1)*10 + i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.overduelocker_shape, null);
        TextView txt_name = (TextView) v.findViewById(R.id.txt_name);
        TextView txt_id = (TextView) v.findViewById(R.id.txt_id);
        TextView txt_email = (TextView) v.findViewById(R.id.txt_email);
        TextView txt_startdate = (TextView) v.findViewById(R.id.txt_startdate);
        TextView txt_enddate = (TextView) v.findViewById(R.id.txt_enddate);
        TextView txt_returntime = (TextView) v.findViewById(R.id.txt_returntime);
        TextView txt_status = (TextView) v.findViewById(R.id.txt_status);
        TextView txt_lockernum = (TextView) v.findViewById(R.id.txt_lockernum);
        ImageButton btn_collect = (ImageButton) v.findViewById(R.id.btn_collect);
        ImageButton btn_return = (ImageButton) v.findViewById(R.id.btn_return);
        index = overduecount - i - ((pageValue - 1) * 10 + 1);


        txt_name.setText(overdueInfo.name[index]);
        txt_id.setText(overdueInfo.id[index]);
        txt_email.setText(overdueInfo.email[index]);
        txt_startdate.setText(overdueInfo.startdate[index]);
        txt_enddate.setText(overdueInfo.enddate[index]);
        txt_lockernum.setText(Integer.toString(overdueInfo.lockernum[index]));
        CallRestApi apiCaller = new CallRestApi();
        if (overdueInfo.iscollected[index].equals("true")){
            txt_status.setText("미반환");
            txt_status.setTextColor(Color.parseColor("#FF8C00"));
            btn_collect.setEnabled(false);
            btn_return.setEnabled(true);
            btn_collect.setBackgroundResource(R.drawable.btn_collect_inactive);
            btn_return.setBackgroundResource(R.drawable.btn_return);
            // 회수버튼 비활성화, 반환버튼 활성화 + 이미지 설정
        }
        else {
            txt_status.setText("미회수");
            txt_status.setTextColor(Color.RED);
            btn_return.setEnabled(false);
            btn_collect.setEnabled(true);
            btn_collect.setBackgroundResource(R.drawable.btn_collect);
            btn_return.setBackgroundResource(R.drawable.btn_return_inactive);
            // 회수버튼 활성화, 반환버튼 비활성화 + 이미지 설정
        }
        if(overdueInfo.returntime[index].equals("none")){
            txt_returntime.setText("N/A");
        }else{
            txt_returntime.setText(overdueInfo.returntime[index]);
            txt_status.setTextColor(Color.parseColor("#32CD32"));
            txt_status.setText("반환 완료");
            btn_collect.setEnabled(false);
            btn_collect.setEnabled(false);
            btn_collect.setBackgroundResource(R.drawable.btn_collect_inactive);
            btn_return.setBackgroundResource(R.drawable.btn_return_inactive);
            // 회수버튼 비활성화, 반환버튼 비활성화 + 이미지 설정
        }
        btn_collect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(btn_collect.isEnabled()) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn_collect.setBackgroundResource(R.drawable.btn_collect_touch);
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn_collect.setBackgroundResource(R.drawable.btn_collect);
                }
                return false;
            }
        });
        btn_return.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(btn_return.isEnabled()) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn_return.setBackgroundResource(R.drawable.btn_return_touch);
                    else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn_return.setBackgroundResource(R.drawable.btn_return);
                }
                return false;
            }
        });

        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = apiCaller.collectOverdueStorage(overdueInfo.num[index]);
                if (result.equals("diffIP")) {
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(context.getApplicationContext(), "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(ContextCompat.getSystemService(context, OverdueLockerManageActivity.class));
                    System.exit(0);
                }else if(result.equals("success")){
                    Toast.makeText(context.getApplicationContext(), "회수처리가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    txt_status.setText("미반환");
                    txt_status.setTextColor(Color.parseColor("#FF8C00"));
                    btn_collect.setEnabled(false);
                    btn_return.setEnabled(true);
                    btn_collect.setBackgroundResource(R.drawable.btn_collect_inactive);
                    btn_return.setBackgroundResource(R.drawable.btn_return);
                }
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = apiCaller.returnOverdueStorage(overdueInfo.num[index]);
                if (result.equals("diffIP")) {
                    Log.e("Login Session", "다른 기기에서 로그인되었음" );
                    Toast.makeText(context.getApplicationContext(), "다른 기기에서 로그인되어 종료합니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.finishAffinity(ContextCompat.getSystemService(context, OverdueLockerManageActivity.class));
                    System.exit(0);
                }else if(result.equals("success")){
                    Toast.makeText(context.getApplicationContext(), "반환처리가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    txt_status.setText("반환 완료");
                    txt_status.setTextColor(Color.parseColor("#32CD32"));
                    btn_collect.setEnabled(false);
                    btn_return.setEnabled(false);
                    btn_collect.setBackgroundResource(R.drawable.btn_collect_inactive);
                    btn_return.setBackgroundResource(R.drawable.btn_return_inactive);
                }
            }
        });


        v.setTag(overdueInfo.num[i]);    //태그를 붙여줌
        return v;

    }

}
