package com.example.projectnamadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private ImageButton make_id;


    private RelativeLayout ReEmail;
    private RelativeLayout ReID;
    private RelativeLayout RePass;
    private RelativeLayout RePassCheck;
    private RelativeLayout ReLockercode;


    private EditText edt_email;
    private EditText edt_id;
    private EditText edt_pw;
    private EditText edt_pwchk;
    private EditText edt_lockercode;


    private TextView tv_warning_email;
    private TextView tv_warning_id;
    private TextView tv_warning_pw;
    private TextView tv_warning_pwchk;
    private TextView tv_warning_lockercode;


    private boolean available_email=false;
    private boolean available_id=false;
    private boolean available_pw=false;
    private boolean available_pwchk=false;
    private boolean available_lockercode=false;

    Pattern emailPattern= Patterns.EMAIL_ADDRESS;
    SharedPreferences deviceInfo;

    boolean isAllAvailable(){
        if(available_lockercode==true && available_email==true && available_id==true
                && available_pw==true && available_pwchk==true){
            return true;
        }
        else
            return false;
    }
    public  int countChar(String str, char ch) {
        return str.length() - str.replace(String.valueOf(ch), "").length();
    }

    boolean isAvailable_lockercode(String str){
        if(str.length()!=8){
            return false;
        }
        if(str!=null  && str.matches("[A-Z|0-9]*")) {
            return true;
        }else {
            return false;
        }
    }
    boolean isAvailable_email(String str){
        if(!emailPattern.matcher(str).matches()){
            return false;
        }
        else return true;
    }

    boolean isAvailable_id(String str){
        if(str.length()>16 || str.length()<8){
            return false;
        }
        if(str!=null  && str.matches("[a-z|A-Z|0-9]*")) {
            if(str.matches(".*[a-z|A-Z].*"))
                if(str.matches(".*[0-9].*"))
                    return true;
        }
        return false;
    }
    boolean isAvailable_pw(String str){
        if(str.length()>16 || str.length()<8){
            return false;
        }
        if(str!=null  && str.matches("[a-z|A-Z|0-9|!|@|#|$|%|^|*|(|)]*")) {
            if(str.matches(".*[0-9].*")) {
                if (str.matches(".*[!|@|#|$|%|^|*|(|)].*"))
                    if(str.matches(".*[a-z|A-Z].*"))
                        return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ReLockercode=(RelativeLayout)findViewById(R.id.ReCode);
        ReEmail=(RelativeLayout)findViewById(R.id.ReEmail);
        ReID=(RelativeLayout)findViewById(R.id.ReID);
        RePass=(RelativeLayout)findViewById(R.id.RePass);
        RePassCheck=(RelativeLayout)findViewById(R.id.RePassCheck);

        make_id=(ImageButton)findViewById(R.id.btnMakeID);
        edt_lockercode=(EditText) findViewById(R.id.edt_code);
        edt_email=(EditText) findViewById(R.id.edt_email);
        edt_id=(EditText) findViewById(R.id.edt_id);
        edt_pw=(EditText) findViewById(R.id.edt_pw);
        edt_pwchk=(EditText) findViewById(R.id.edt_pwchk);

        tv_warning_lockercode=(TextView)findViewById(R.id.tv_warning_code);
        tv_warning_email=(TextView)findViewById(R.id.tv_warning_email);
        tv_warning_id=(TextView)findViewById(R.id.tv_warning_id);
        tv_warning_pw=(TextView)findViewById(R.id.tv_warning_pw);
        tv_warning_pwchk=(TextView)findViewById(R.id.tv_warning_pwchk);

        make_id.setEnabled(false);

        deviceInfo=getSharedPreferences("accountOTP", 0);

        deviceInfo=getSharedPreferences("accountOTP", 0);

        // ?????? ?????? EditText??? ???????????? ??????????????? ??????
        edt_lockercode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                int length = txt.length();
                if(length==0){
                    available_lockercode=false;
                    ReLockercode.setBackgroundResource(R.drawable.border_gray);
                    tv_warning_lockercode.setVisibility(View.INVISIBLE);
                    make_id.setEnabled(false);
                }
                else if(!isAvailable_lockercode(txt)){
                    tv_warning_lockercode.setText("??????????????? ????????? ????????????.");
                    available_lockercode=false;
                    tv_warning_lockercode.setVisibility(View.VISIBLE);
                    ReLockercode.setBackgroundResource(R.drawable.border_red);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else{
                    available_lockercode=true;
                    tv_warning_lockercode.setVisibility(View.INVISIBLE);
                    ReLockercode.setBackgroundResource(R.drawable.border_gray);
                    if(isAllAvailable()==true) {
                        make_id.setEnabled(true);
                        make_id.setBackgroundResource(R.drawable.img_addaccount);
                    }
                }

            }
        });

        // email EditText ????????? ?????? ???
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                int length = txt.length();
                if(length==0){
                    ReEmail.setBackgroundResource(R.drawable.border_gray);
                    available_email=false;
                    tv_warning_email.setVisibility(View.INVISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else if(!isAvailable_email(txt)){
                    ReEmail.setBackgroundResource(R.drawable.border_red);
                    available_email=false;
                    tv_warning_email.setVisibility(View.VISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else{
                    available_email=true;
                    tv_warning_email.setVisibility(View.INVISIBLE);
                    ReEmail.setBackgroundResource(R.drawable.border_gray);
                    if(isAllAvailable()==true) {
                        make_id.setEnabled(true);
                        make_id.setBackgroundResource(R.drawable.img_addaccount);
                    }
                }

            }
        });

        edt_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                int length = txt.length();
                if(length==0){
                    ReID.setBackgroundResource(R.drawable.border_gray);
                    available_id=false;
                    tv_warning_id.setVisibility(View.INVISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else if(!isAvailable_id(txt)){
                    tv_warning_id.setText("???????????? 8~16?????? ??????/?????? ???????????? ???????????????.");
                    ReID.setBackgroundResource(R.drawable.border_red);
                    available_id=false;
                    tv_warning_id.setVisibility(View.VISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else{
                    available_id=true;
                    tv_warning_id.setVisibility(View.INVISIBLE);
                    ReID.setBackgroundResource(R.drawable.border_gray);
                    if(isAllAvailable()==true) {
                        make_id.setEnabled(true);
                        make_id.setBackgroundResource(R.drawable.img_addaccount);
                    }
                }
            }
        });

        edt_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                int length = txt.length();
                String pwchk=edt_pwchk.getText().toString();
                if(pwchk.length()>0 ){ // ??????????????? ??????????????? ??? ???????????? ????????? ??????????????? ?????? ??????
                    if(pwchk.compareTo(txt)!=0) {
                        RePassCheck.setBackgroundResource(R.drawable.border_red);
                        available_pwchk = false;
                        tv_warning_pwchk.setVisibility(View.VISIBLE);
                        make_id.setEnabled(false);
                        make_id.setBackgroundResource(R.drawable.account_creation_fail);
                    }
                    else{
                        available_pwchk=true;
                        tv_warning_pwchk.setVisibility(View.INVISIBLE);
                        RePassCheck.setBackgroundResource(R.drawable.border_gray);
                    }
                }
                if(length==0){
                    RePass.setBackgroundResource(R.drawable.border_gray);
                    available_pw=false;
                    tv_warning_pw.setVisibility(View.INVISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else if(!isAvailable_pw(txt)){
                    RePass.setBackgroundResource(R.drawable.border_red);
                    available_pw=false;
                    tv_warning_pw.setVisibility(View.VISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else{
                    available_pw=true;
                    tv_warning_pw.setVisibility(View.INVISIBLE);
                    RePass.setBackgroundResource(R.drawable.border_gray);
                    if(isAllAvailable()==true) {
                        make_id.setEnabled(true);
                        make_id.setBackgroundResource(R.drawable.img_addaccount);
                    }
                }
            }
        });

        edt_pwchk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = editable.toString();
                int length = txt.length();
                if(length==0){
                    RePassCheck.setBackgroundResource(R.drawable.border_gray);
                    available_pwchk=false;
                    tv_warning_pwchk.setVisibility(View.INVISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else if(txt.compareTo(edt_pw.getText().toString())!=0){
                    RePassCheck.setBackgroundResource(R.drawable.border_red);
                    available_pwchk=false;
                    tv_warning_pwchk.setVisibility(View.VISIBLE);
                    make_id.setEnabled(false);
                    make_id.setBackgroundResource(R.drawable.account_creation_fail);
                }
                else{
                    available_pwchk=true;
                    tv_warning_pwchk.setVisibility(View.INVISIBLE);
                    RePassCheck.setBackgroundResource(R.drawable.border_gray);
                    if(isAllAvailable()==true && isAvailable_pw(txt)) {
                        make_id.setEnabled(true);
                        make_id.setBackgroundResource(R.drawable.img_addaccount);
                    }
                }
            }
        });

        make_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallRestApi apiCaller = new CallRestApi();
                String result; // result??? 0?????? ???????????? ??????, -1?????? ???????????? ??????
                result = apiCaller.newAccount(deviceInfo, edt_email.getText().toString(), edt_id.getText().toString(),
                        edt_pw.getText().toString(), edt_lockercode.getText().toString());
                switch(result) {
                    case "success":
                        Log.i("????????????", result + ":???????????? ??????");
                        Toast toastA =  Toast.makeText(getApplicationContext(),"???????????? ??????",Toast.LENGTH_SHORT);
                        toastA.show();
                        finish();
                        break;
                    case "duplicated":
                        Log.i("????????????", result + ":?????? ????????? ??????");
                        tv_warning_id.setText("?????? ???????????? ???????????????.");
                        ReID.setBackgroundResource(R.drawable.border_red);
                        available_id=false;
                        tv_warning_id.setVisibility(View.VISIBLE);
                        make_id.setEnabled(false);
                        make_id.setBackgroundResource(R.drawable.account_creation_fail);
                        break;
                    case "no lockercode":
                        Log.i("????????????", result + ":????????? ?????? ??????");
                        tv_warning_lockercode.setText("???????????? ?????? ????????? ???????????????.");
                        available_lockercode=false;
                        tv_warning_lockercode.setVisibility(View.VISIBLE);
                        make_id.setEnabled(false);
                        make_id.setBackgroundResource(R.drawable.account_creation_fail);
                        break;
                    default:
                        Log.e("????????????", result + ":??? ??? ?????? ??????");
                        break;
                }
            }
        });


    }
}