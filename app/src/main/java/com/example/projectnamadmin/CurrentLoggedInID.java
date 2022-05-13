package com.example.projectnamadmin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CurrentLoggedInID {
    public static String ID="";
    public static String name="";
    public static String email="";
    public static Boolean isLoggedIn=false;
    private static String token="";
    public static String getAuthToken(){
        return token;
    }
    public static void resetAuthToken(){
        token="";
    }
    public static void setAuthToken(String newToken) {

        token= newToken;
    }


}
