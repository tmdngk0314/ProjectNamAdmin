package com.example.projectnamadmin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.List;

public class CheckLockerAdapter extends BaseAdapter {
    private Context context;
    private List<CheckLockerInfo> checkLockerlist;
    public CheckLockerAdapter(Context context, List<CheckLockerInfo> checkLockerlist){
        this.context = context;
        this.checkLockerlist = checkLockerlist;}

    @Override
    public int getCount(){
        return checkLockerlist.size();}//리스트뷰의 총 갯수

    @Override
    public Object getItem(int position){
        return checkLockerlist.get(position);}//해당 위치의 값을 리스트뷰에 뿌려줌

    @Override
    public long getItemId(int position){
        return position;}

    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    View v = View.inflate(context, R.layout.locker_shape, null);
    TextView lockertext = (TextView)v.findViewById(R.id.lockertext);
    TextView statetext = (TextView)v.findViewById(R.id.stateText);
       lockertext.setText(checkLockerlist.get(position).getLockernum());
       statetext.setText(checkLockerlist.get(position).getLockerState());
       v.setTag(checkLockerlist.get(position).getLockernum());
        return v;}



}
