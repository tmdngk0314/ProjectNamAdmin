package com.example.projectnamadmin;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class OverdueListAdapter extends BaseAdapter {
    private Context context;
    private OverdueListInfo overdueInfo;
    int pageValue, pageOffset, overduecount;


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
        int index = 0;
        index = overduecount - i - ((pageValue - 1) * 10 + 1);


        txt_name.setText(overdueInfo.name[index]);
        txt_id.setText(overdueInfo.id[index]);
        txt_email.setText(overdueInfo.email[index]);
        txt_startdate.setText(overdueInfo.startdate[index]);
        txt_enddate.setText(overdueInfo.enddate[index]);
        txt_lockernum.setText(Integer.toString(overdueInfo.lockernum[index]));
        if (overdueInfo.iscollected[index].equals("true")){
            txt_status.setText("미반환");
            txt_status.setTextColor(Color.parseColor("#FF8C00"));
            // 회수버튼 비활성화, 반환버튼 활성화 + 이미지 설정
        }
        else {
            txt_status.setText("미회수");
            txt_status.setTextColor(Color.RED);
            // 회수버튼 활성화, 반환버튼 비활성화 + 이미지 설정
        }
        if(overdueInfo.returntime[index].equals("none")){
            txt_returntime.setText("N/A");
        }else{
            txt_returntime.setText(overdueInfo.returntime[index]);
            txt_status.setTextColor(Color.parseColor("#32CD32"));
            txt_status.setText("반환 완료");
            // 회수버튼 비활성화, 반환버튼 비활성화 + 이미지 설정
        }

        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회수 처리 api 호출
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 반환 처리 api 호출
            }
        });


        v.setTag(overdueInfo.num[i]);    //태그를 붙여줌
        return v;

    }

}
