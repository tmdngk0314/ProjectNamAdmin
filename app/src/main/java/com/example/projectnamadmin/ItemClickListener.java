package com.example.projectnamadmin;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class ItemClickListener implements AdapterView.OnItemClickListener {
    Context context;

    public ItemClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ((LockerDetailActivity) context).popup_lockernum.setText(((LockerDetailActivity) context).detailInfo.getLockernum());
        ((LockerDetailActivity) context).txt_status.setText(((LockerDetailActivity) context).statusInfo.getStatus(position));
        /*detail api caller 사용해서 사용자 정보 불러오기*/
        ((LockerDetailActivity) context).Rela_locker.setVisibility(VISIBLE);
    }
}
