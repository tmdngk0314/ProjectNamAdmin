package com.example.projectnamadmin;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class IllegalOpenListAdapter extends BaseAdapter {
    private Context context;
    private IllegalOpenHistoryInfo illegalInfo;
    int pageValue, pageOffset, illegalCount;
    int index=0;


    public void putInfo(IllegalOpenHistoryInfo illegalInfo, int pageValue, int pageOffset, int illegalCount){
        this.illegalInfo=illegalInfo;
        this.pageValue=pageValue;
        this.pageOffset=pageOffset;
        this.illegalCount =illegalCount;
    }

    public IllegalOpenListAdapter(Context context, IllegalOpenHistoryInfo illegalInfo, int pageValue, int pageOffset, int illegalCount) {
        this.context = context;
        this.illegalInfo = illegalInfo;
        this.pageValue = pageValue;
        this.pageOffset = pageOffset;
        this.illegalCount = illegalCount;
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
        View v = View.inflate(context, R.layout.illegalopenhistory_shape, null);

        index = illegalCount - i - ((pageValue - 1) * 10 + 1);
        TextView txt_num=v.findViewById(R.id.btn_incidentnumber);
        TextView txt_lockernum=v.findViewById(R.id.txt_lockernum);
        TextView txt_date=v.findViewById(R.id.txt_date);

        txt_num.setText(Integer.toString(illegalInfo.num[index]));
        txt_lockernum.setText(Integer.toString(illegalInfo.lockernum[index]));
        txt_date.setText(illegalInfo.time[index]);


        v.setTag(illegalInfo.num[i]);    //태그를 붙여줌
        return v;

    }

}
