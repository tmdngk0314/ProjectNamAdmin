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
        ((LockerDetailActivity) context).txt_status.setText(lld.getStatus());
        /*detail api caller 사용해서 사용자 정보 불러오기*/
        ((LockerDetailActivity) context).txt_name.setText(lld.getname());
        ((LockerDetailActivity) context).txt_id.setText(lld.getid());
        ((LockerDetailActivity) context).txt_email.setText(lld.getemail());
        ((LockerDetailActivity) context).txt_startdate.setText(lld.getStartdate());
        ((LockerDetailActivity) context).txt_enddate.setText(lld.getEnddate());
        ((LockerDetailActivity) context).Rela_locker.setVisibility(VISIBLE);


    }
}
