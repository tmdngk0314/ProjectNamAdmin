package com.example.projectnamadmin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CurrentLoggedInID {
    public static String ID="";
    public static String email="";
    public static Boolean isLoggedIn=false;
    private static String token="";
    private static String lockername="";
    private static String location="";
    private static String fcmtoken="";

    public static String getFCMtoken() {
        return fcmtoken;
    }
    public static void setFCMToken(String input){
        fcmtoken=input;
    }
    public static void resetInfo(){
        ID="";
        email="";
        isLoggedIn=false;
        token="";
        lockername="";
        location="";
    }
    public static String getLocation(){
        return location;
    }
    public static void setLocation(String inputlocation){
        location=inputlocation;
    }

    public static String getLockername() {
        return lockername;
    }
    public static void setLockername(String lockername) {
        CurrentLoggedInID.lockername = lockername;
    }
    public static String getAuthToken(){
        return token;
    }
    public static void setAuthToken(String newToken) {

        token= newToken;
    }


}
