package com.example.projectnamadmin;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Random;

public class CallRestApi {
    static int lastResponseCode=0;
    public JSONObject receivedJSONObject=new JSONObject();
    public JSONArray receivedJSONArray;
    public void getRestAPI(String link){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lastResponseCode = 0;
                try {
                    URL url = new URL("http://15.165.63.75:5000/"+link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoInput(true);
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    InputStream is = conn.getInputStream();

                    // Get the stream 넘어오는 결과 값들을 저장
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    // 해당 InputStream에 있는 내용들을 버퍼에 담아서 읽을 수 있도록 한다.
                    String line;
                    //String형 line 변수를 만들고 하나씩 불러와서  문자열 형태로 저장.
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    // Set the result
                    String result = builder.toString();
                    Log.i("json 결과", result);
                    JSONObject json = null;
                    json = new JSONObject(result);
                    receivedJSONObject = json;
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while(thread.isAlive());

    }
    public void postRestAPI(JSONObject sendJSON, String link){
        try {

            sendJSON.put("id", CurrentLoggedInID.ID);
            sendJSON.put("token", CurrentLoggedInID.getAuthToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lastResponseCode=0;
                    URL url = new URL("http://15.165.63.75:5000/"+link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    OutputStream outputStream;
                    outputStream = conn.getOutputStream();
                    outputStream.write(sendJSON.toString().getBytes());
                    int response = conn.getResponseCode();
                    Log.e("responseCode -> ", Integer.toString(response)); // 200 아니고 다른 숫자면 서버 문제
                    lastResponseCode=response;
                    String responseMessage = conn.getResponseMessage();
                    System.out.println("----responseMessage----- : "+responseMessage);
                    InputStream is = conn.getInputStream();
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    while((line=reader.readLine())!=null) {
                        builder.append(line);
                    }
                    String result = builder.toString();
                    Log.i("json 결과 ", result);
                    Object json= new JSONTokener(result).nextValue();
                    if(json instanceof JSONObject)
                        receivedJSONObject = new JSONObject(result);
                    else if(json instanceof JSONArray)
                        receivedJSONArray = new JSONArray(result);
                    conn.disconnect();

                }
                catch(Exception e){
                    Log.e("REST API", "POST method failed: " + e.getMessage());
                    e.printStackTrace();
                    lastResponseCode=503;
                }
            }
        });
        thread.start();
        while(thread.isAlive());
    }

    public NoticeInfo loadNotice(int page){
        JSONObject jsonPage=new JSONObject();
        Integer[] index=new Integer[10];
        String[] title=new String[10];
        String[] date=new String[10];
        String[] body=new String[10];
        try{
            jsonPage.put("page", page);
            postRestAPI(jsonPage,"notice/load");
            try {
                if (receivedJSONObject.getString("result").equals("diffIP")) {
                    NoticeInfo info = new NoticeInfo();
                    info.result = "diffIP";
                    return info;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            for(int i=0; i<receivedJSONArray.length(); i++) {
                index[i] = (Integer) receivedJSONArray.getJSONArray(i).get(0);
                title[i] = (String) receivedJSONArray.getJSONArray(i).get(1);
                date[i] = (String) receivedJSONArray.getJSONArray(i).get(2);
                body[i] = (String) receivedJSONArray.getJSONArray(i).get(3);
                Log.e("JSONArray로그", Integer.toString(index[i]) + title[i] + date[i] + body[i]);
            }
            NoticeInfo info = new NoticeInfo();
            info.result="success";
            info.setIndex(index);
            info.setTitle(title);
            info.setDate(date);
            info.setBody(body);

            return info;
        }
        catch(Exception e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            NoticeInfo info = new NoticeInfo();
            info.result="None";
            return info;
        }
    }
    public String uploadNotice(String title, String body, Boolean pushtoclient){
        JSONObject info=new JSONObject();
        String result="None";
        try {
            String pushString;
            if(pushtoclient) pushString="true";
            else pushString="false";
            info.put("title", title);
            info.put("body", body);
            info.put("pushtoclient", pushString);
            postRestAPI(info, "notice/upload");
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
            }
        } catch (JSONException e) {
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public String delete_notice(Integer index){
        JSONObject info=new JSONObject();
        String result="None";
        try {
            info.put("num", index);
            postRestAPI(info, "notice/delete");
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
            }
        } catch (JSONException e) {
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /*
    public LockerInfo loadLockerlist(){
        JSONObject info= new JSONObject();
        String[] location=new String[10];
        String[] lockername=new String[10];
        LockerInfo lockerinfo=new LockerInfo();
        try {
            getRestAPI("client/reservation/load_locker_count");
            Integer lockercount = receivedJSONObject.getInt("result");
            postRestAPI(info, "client/reservation/load_locker_names");
            lockerinfo.init(lockercount);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            for(int i=0; i<receivedJSONArray.length(); i++) {
                lockername[i] = (String) receivedJSONArray.getJSONArray(i).get(0);
                location[i] = (String) receivedJSONArray.getJSONArray(i).get(1);
                Log.e("JSONArray로그",location[i] + lockername[i]);
            }
            lockerinfo.result="success";
            lockerinfo.setLockername(lockername);
            lockerinfo.setLocation(location);

            return lockerinfo;
        }
        catch(Exception e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return new LockerInfo();
        }
    }*/


    public String newAccount(SharedPreferences deviceInfo, String email, String id, String pw, String lockercode){
        JSONObject info = new JSONObject();
        try {
            info.put("email", email);
            info.put("newid", id);
            info.put("pw", pw);
            info.put("lockercode", lockercode);
            postRestAPI(info, "admin/new_account");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
                switch (result) {
                    case "success":
                        SharedPreferences.Editor editor = deviceInfo.edit();
                        editor.putString(id, receivedJSONObject.getString("otpkey"));
                        editor.commit();
                }
            }
            return result;
        } catch (JSONException e) {
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "JSONException";
        }
    }
    public void setFCMToken(){
        JSONObject info=new JSONObject();
        try {
            info.put("fcmtoken", CurrentLoggedInID.getFCMtoken());
            postRestAPI(info, "setfcmtoken");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
                switch (result) {
                    case "success":

                }
            }
        } catch (JSONException e) {
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
        }
    }
    public String login(String id, String pw){
        JSONObject info = new JSONObject();
        try{
            info.put("id", id);
            info.put("pw", pw);
            CurrentLoggedInID.ID=id;

            postRestAPI(info, "admin/login");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
                if(result.compareTo("success")==0){
                    String token=receivedJSONObject.getString("token");
                    String email=receivedJSONObject.getString("email");
                    String lockername=receivedJSONObject.getString("lockername");
                    String location=receivedJSONObject.getString("location");
                    CurrentLoggedInID.setAuthToken(token);
                    CurrentLoggedInID.setLocation(location);
                    CurrentLoggedInID.setLockername(lockername);
                    int frontnum=email.indexOf('@')-1;
                    String resultEmail=email.substring(0, 4);
                    for(int i=3; i<frontnum; i++){
                        resultEmail+='*';
                    }
                    resultEmail+=email.substring(frontnum+1);
                    CurrentLoggedInID.email=resultEmail;
                    CurrentLoggedInID.ID=id;
                }else
                    CurrentLoggedInID.resetInfo();
            }
            else
                CurrentLoggedInID.resetInfo();
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }


    public String logout(){
        JSONObject info = new JSONObject();
        try{
            postRestAPI(info, "admin/logout");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
                if(result.equals("success")){
                    CurrentLoggedInID.resetInfo();
                }
            }
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }
    public LockerStatusInfo loadLockerStatus(){
        JSONObject info = new JSONObject();
        LockerStatusInfo resultInfo=new LockerStatusInfo();
        resultInfo.result="None";
        try{
            postRestAPI(info, "admin/manage/load_locker_status");
            if(lastResponseCode==200){
                String result=receivedJSONObject.getString("result");
                resultInfo.result=result;
                if(result.equals("success")){
                    int size=receivedJSONObject.getInt("length");
                    resultInfo.setSize(size);
                    for(int i=0; i<size; i++){
                        String status = receivedJSONObject.getString("#"+Integer.toString(i+1));
                        resultInfo.setStatus(status, i+1);
                    }
                }
            }
            return resultInfo;
        }catch(JSONException e){
            Log.e("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return resultInfo;
        }
    }

    public LoadLockerDetails loadLockerDetails(int lockernum) {
        JSONObject info = new JSONObject();
        LoadLockerDetails resultInfo=new LoadLockerDetails(lockernum);
        resultInfo.result="None";
        try{
            info.put("lockernum",lockernum);
            postRestAPI(info, "admin/manage/load_locker_details");
            if(lastResponseCode==200){
                String result=receivedJSONObject.getString("result");
                resultInfo.result=result;
                if(result.equals("success")){
                    String status = receivedJSONObject.getString("status");
                    if(!status.equals("idle")) {
                        String email = receivedJSONObject.getString("email");
                        String startdate = receivedJSONObject.getString("startdate");
                        String enddate = receivedJSONObject.getString("enddate");
                        String name = receivedJSONObject.getString("name");
                        String id = receivedJSONObject.getString("id");
                        resultInfo.setName(name);
                        resultInfo.setId(id);
                        resultInfo.setEmail(email);
                        resultInfo.setEnddate(enddate);
                        resultInfo.setStartdate(startdate);
                    }
                    resultInfo.setStatus(status);
                }
            }
            Log.e("loadLockerDetails","Done.");
            return resultInfo;
        }catch(JSONException e){
            Log.e("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return resultInfo;
        }

    }

    public OverdueListInfo loadOverdueList(){
        JSONObject info = new JSONObject();
        OverdueListInfo overdueListInfo = new OverdueListInfo();
        overdueListInfo.result="none";
        try{
            postRestAPI(info, "admin/load_overdue_list");
            if(lastResponseCode==200){
                String result=receivedJSONObject.getString("result");
                overdueListInfo.result=result;
                if(result.equals("success")){
                    int count=receivedJSONObject.getInt("length");
                    overdueListInfo.count=count;
                    overdueListInfo.init(count);
                    for(int i=0; i<count; i++){
                        overdueListInfo.num[i]=receivedJSONObject.getInt("num"+Integer.toString(i));
                        overdueListInfo.name[i]=receivedJSONObject.getString("name"+Integer.toString(i));
                        overdueListInfo.id[i]=receivedJSONObject.getString("id"+Integer.toString(i));
                        overdueListInfo.email[i]=receivedJSONObject.getString("email"+Integer.toString(i));
                        overdueListInfo.lockernum[i]=receivedJSONObject.getInt("lockernum"+Integer.toString(i));
                        overdueListInfo.startdate[i]=receivedJSONObject.getString("startdate"+Integer.toString(i));
                        overdueListInfo.enddate[i]=receivedJSONObject.getString("enddate"+Integer.toString(i));
                        overdueListInfo.iscollected[i]=receivedJSONObject.getString("iscollected"+Integer.toString(i));
                        overdueListInfo.returntime[i]=receivedJSONObject.getString("returntime"+Integer.toString(i));
                    }
                }

            }
        }catch(JSONException e){
            Log.e("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return overdueListInfo;
        }
        return overdueListInfo;
    }


    public String sendVerifyingEmail(){
        JSONObject info = new JSONObject();
        try{
            postRestAPI(info, "admin/send_verifying_email");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
            }
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }


    public String verifyingCode(String code){
        JSONObject info = new JSONObject();
        try{
            info.put("code", code);
            postRestAPI(info, "admin/verifying_code");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
            }
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }


    public String unverifyingCode(){
        JSONObject info = new JSONObject();
        try{
            postRestAPI(info, "admin/unverifying_code");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
            }
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }





    public String changePassword(String oldpw, String newpw){
        JSONObject info = new JSONObject();
        try{
            info.put("oldpw", oldpw);
            info.put("newpw", newpw);
            postRestAPI(info, "admin/change_password");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
            }
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }



    public String reissuanceotp(SharedPreferences deviceInfo, String id){
        JSONObject info = new JSONObject();
        try{
            postRestAPI(info, "admin/reissuance_otpkey");
            String result="None";
            if(lastResponseCode==200) {
                result = receivedJSONObject.getString("result");
                if(result.equals("success")){
                    SharedPreferences.Editor editor=deviceInfo.edit();
                    String newKey=receivedJSONObject.getString("otpkey");
                    editor.putString(id, newKey);
                    editor.commit();
                }
            }
            return result;
        }
        catch(JSONException e){
            Log.i("JSONException", "failed to put json data:"+e.getMessage());
            e.printStackTrace();
            return "unknown";
        }
    }



    public String deleteAccount(SharedPreferences deviceSettings){
        JSONObject info = new JSONObject();
        try{
            postRestAPI(info, "admin/delete_account");
            String result= "None";
            if(lastResponseCode==200){
                result=receivedJSONObject.getString("result");
                if(result.equals("success")){
                    SharedPreferences.Editor editor=deviceSettings.edit();
                    editor.putString("savedID", "");
                    editor.putString("savedPW", "");
                    editor.putBoolean("isSaved", false);
                    editor.commit();
                }
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return "None";
        }
    }

    public String collectOverdueStorage(int num){
        JSONObject info = new JSONObject();
        String result;
        try{
            info.put("num", num);
            postRestAPI(info, "admin/collect_overdue_storage");
            result= "None";
            if(lastResponseCode==200){
                result=receivedJSONObject.getString("result");
            }
        }catch(Exception e){
            e.printStackTrace();
            return "None";
        }
        return result;
    }
    public String returnOverdueStorage(int num){
        JSONObject info = new JSONObject();
        String result;
        try{
            info.put("num", num);
            postRestAPI(info, "admin/return_overdue_storage");
            result= "None";
            if(lastResponseCode==200)
                result=receivedJSONObject.getString("result");
        }catch (Exception e){
            e.printStackTrace();
            return "None";
        }
        return result;
    }

     /*
    public FullReservationInfo loadFullReservedDates(String lockername){
        JSONObject info = new JSONObject();
        FullReservationInfo response=new FullReservationInfo();
        try{
            info.put("lockername", lockername);
            postRestAPI(info, "client/reservation/load_full_reserved_dates");
            response.result = "None";
            if(lastResponseCode==200){
                try{
                    if(receivedJSONObject.getString("result").equals("no locker")){
                        response.result="no locker";
                        return response;
                    }
                }catch(Exception e){
                    response.list = receivedJSONArray;
                    response.result = "success";
                    e.printStackTrace();
                }

                return response;

            }
        }catch (Exception e){
            e.printStackTrace();
            response.result="None";
        }
        return response;
    }
    */

}
