package com.example.projectnamadmin;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class ItemClickListener implements AdapterView.OnItemClickListener {
    Context context;
    LoadLockerDetails lld;
    CallRestApi callRestApi=new CallRestApi();

    public ItemClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("onItenClick", Integer.toString(position));
        lld = callRestApi.loadLockerDetails(position+1);

        ((LockerDetailActivity) context).popup_lockernum.setText(Integer.toString(position+1));

        if(lld.getStatus().equals("using")){
        ((LockerDetailActivity) context).txt_status.setText("사물함 이용중");
        /*detail api caller 사용해서 사용자 정보 불러오기*/
        ((LockerDetailActivity) context).txt_name.setText(lld.getname());
        ((LockerDetailActivity) context).txt_id.setText(lld.getid());
        ((LockerDetailActivity) context).txt_email.setText(lld.getemail());
        ((LockerDetailActivity) context).txt_startdate.setText(lld.getStartdate());
        ((LockerDetailActivity) context).txt_enddate.setText(lld.getEnddate());}

        if(lld.getStatus().equals("reserved")||lld.getStatus().equals("idle")) {
            ((LockerDetailActivity) context).txt_status.setText("이용중이 아닙니다.");
            ((LockerDetailActivity) context).txt_name.setText("이용중이 아닙니다.");
            ((LockerDetailActivity) context).txt_id.setText("이용중이 아닙니다.");
            ((LockerDetailActivity) context).txt_email.setText("이용중이 아닙니다.");
            ((LockerDetailActivity) context).txt_startdate.setText("이용중이 아닙니다.");
            ((LockerDetailActivity) context).txt_enddate.setText(" ");
        }
        ((LockerDetailActivity) context).checklockerlist.setEnabled(false);
        ((LockerDetailActivity) context).btn_back.setEnabled(false);
        ((LockerDetailActivity) context).Rela_locker.setVisibility(VISIBLE);



    }
}
