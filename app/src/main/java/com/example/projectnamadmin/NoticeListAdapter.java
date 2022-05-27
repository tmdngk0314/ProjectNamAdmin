package com.example.projectnamadmin;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private NoticeInfo noticeInfo;
    int pageValue, pageOffset, noticeMax;

    public NoticeListAdapter(Context context, NoticeInfo noticeInfo, int pageValue, int pageOffset, int noticeMax) {
        this.context = context;
        this.noticeInfo = noticeInfo;
        this.pageValue = pageValue;
        this.pageOffset = pageOffset;
        this.noticeMax = noticeMax;
    }

    public void putInfo(NoticeInfo noticeInfo, int pageValue, int pageOffset, int noticeMax) {
        this.noticeInfo = noticeInfo;
        this.pageValue = pageValue;
        this.pageOffset = pageOffset;
        this.noticeMax = noticeMax;
    }

    @Override
    public int getCount() {
        Log.e(Integer.toString(pageOffset),"HELOOOOOOOOOOOOOOO");
        return pageOffset;
    }

    @Override
    public Object getItem(int position) {
        return noticeInfo.title[position]+"\n"+noticeInfo.date[position];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice_shape,null);
        TextView noticeText = (TextView) v.findViewById(R.id.noticetext);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        noticeText.setText(noticeInfo.title[i]);
        dateText.setText(noticeInfo.date[i]);

        v.setTag(noticeInfo.title[i]);    //태그를 붙여줌
        return v;

    }
}
