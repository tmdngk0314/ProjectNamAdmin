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

        ((LockerDetailActivity) context).popup_lockernum.setText(((LockerDetailActivity) context).detailInfo.getLockernum());
        ((LockerDetailActivity) context).txt_status.setText(((LockerDetailActivity) context).loadLockerDetails.getStatus());
        /*detail api caller 사용해서 사용자 정보 불러오기*/
        ((LockerDetailActivity) context).txt_name.setText(((LockerDetailActivity) context).loadLockerDetails.getname());
        ((LockerDetailActivity) context).txt_id.setText(((LockerDetailActivity) context).loadLockerDetails.getid());
        ((LockerDetailActivity) context).txt_email.setText(((LockerDetailActivity) context).loadLockerDetails.getemail());
        ((LockerDetailActivity) context).txt_startdate.setText(((LockerDetailActivity) context).loadLockerDetails.getStartdate());
        ((LockerDetailActivity) context).txt_enddate.setText(((LockerDetailActivity) context).loadLockerDetails.getEnddate());
        ((LockerDetailActivity) context).Rela_locker.setVisibility(VISIBLE);


    }
}
