package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import java.time.LocalDate;

public class SelectDayActivity extends AppCompatActivity {
    ImageButton ok;
    int Year = LocalDate.now().getYear(), Month = LocalDate.now().getMonthValue(), Day = LocalDate.now().getDayOfMonth()+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);

        ok = (ImageButton) findViewById(R.id.nextBtn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectDayActivity.this,CheckLockerActivity.class);
                intent.putExtra("연", Year);
                intent.putExtra("월", Month);
                intent.putExtra("일", Day);
                startActivity(intent);
                finish();
            }
        });

        ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ok.setBackgroundResource(R.drawable.ok_touch);
                        return false;
                    case MotionEvent.ACTION_UP:
                        ok.setBackgroundResource(R.drawable.ok);
                        return false;
                    default:
                        return false;
                }
            }
        });


    }
}