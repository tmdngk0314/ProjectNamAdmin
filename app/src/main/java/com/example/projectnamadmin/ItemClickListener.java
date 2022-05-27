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

        ((LockerDetailActivity) context).noticeListView.setEnabled(false);
        ((LockerDetailActivity) context).noticeTitle.setText(((NoticeActivity) context).noticeInfo.title[position]);
        ((LockerDetailActivity) context).noticeDate.setText(((NoticeActivity) context).noticeInfo.date[position]);
        ((LockerDetailActivity) context).noticeBody.setText(((NoticeActivity) context).noticeInfo.body[position]);
        ((LockerDetailActivity) context).noticeRelative.setVisibility(VISIBLE);
    }
}
