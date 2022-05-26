package com.example.projectnamadmin;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

public class ItemClickListener implements AdapterView.OnItemClickListener {
    Context context;

    public ItemClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < 7; i++) {
            ((NoticeActivity) context).pageBtn[i].setClickable(false);
        }
        /*
        parent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
        ((NoticeActivity) context).noticeListView.setEnabled(false);
        ((NoticeActivity) context).noticeTitle.setText(((NoticeActivity) context).noticeInfo.title[position]);
        ((NoticeActivity) context).noticeDate.setText(((NoticeActivity) context).noticeInfo.date[position]);
        ((NoticeActivity) context).noticeBody.setText(((NoticeActivity) context).noticeInfo.body[position]);
        ((NoticeActivity) context).noticeRelative.setVisibility(VISIBLE);
    }
}
