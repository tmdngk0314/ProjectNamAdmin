package com.example.projectnamadmin;

// 메인 메뉴가 아닌 다른 액티비티에서 앱을 강제종료 했을 때 로그아웃하기 위한 서비스

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ForcedTerminationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분
        Log.e("ForcedTermination","onTaskRemoved - 강제 종료 " + rootIntent);
        if(CurrentLoggedInID.isLoggedIn==true) {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            CallRestApi apiCaller = new CallRestApi();
            apiCaller.logout();
            CurrentLoggedInID.resetInfo();
        }
        super.onDestroy();
        stopSelf(); //서비스 종료
    }
}
