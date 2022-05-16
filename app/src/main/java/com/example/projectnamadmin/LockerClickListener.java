package com.example.projectnamadmin;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class LockerClickListener implements AdapterView.OnItemClickListener {
    Context context;
    String location, lockername;

    public LockerClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lockername = ((LockerListActivity) context).lockerInfo.lockername[position];
        location = ((LockerListActivity) context).lockerInfo.location[position];
        /*Intent intent = new Intent(context, ReserveActivity.class);
        intent.putExtra("lockername",lockername);
        intent.putExtra("location",location);
        context.startActivity(intent);
        ((LockerListActivity) context).finish();*/


    }
}

