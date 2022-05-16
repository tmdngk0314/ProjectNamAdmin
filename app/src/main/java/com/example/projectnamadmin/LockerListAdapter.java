package com.example.projectnamadmin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LockerListAdapter extends BaseAdapter {
    private Context context;
    private LockerInfo lockerInfo;
    int Overallsize;

    public LockerListAdapter(Context context, LockerInfo lockerinfo, int Overallsize ) {
        this.context = context;
        this.lockerInfo = lockerinfo;
        this.Overallsize = Overallsize;

    }

    @Override
    public int getCount() {
        return Overallsize;
    }

    @Override
    public Object getItem(int position) {
        return lockerInfo.lockername[position]+"\n"+lockerInfo.location[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.notice_shape, null);
        TextView noticeText = (TextView) v.findViewById(R.id.noticetext);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        noticeText.setText(lockerInfo.lockername[i]);
        dateText.setText(lockerInfo.location[i]);

        v.setTag(lockerInfo.lockername[i]);
        //태그를 붙여줌
        return v;

    }
}
