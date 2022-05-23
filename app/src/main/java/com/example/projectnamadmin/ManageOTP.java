package com.example.projectnamadmin;

import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ManageOTP {

    private static final int[] DIGITS_POWER
            // 0 1  2   3    4     5      6       7        8
            = {1,10,100,1000,10000,100000,1000000,10000000,100000000 };


    private static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text){
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey =
                    new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }

    private static byte[] hexStr2Bytes(String hex){
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex,16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++)
            ret[i] = bArray[i+1];
        return ret;
    }

    public static String stringToHex(String s) {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            result += String.format("%02X", (int) s.charAt(i));
        }
        result=result.toLowerCase();
        return result;
    }


    public static String generateTOTP(String key,
                                      String time,
                                      String returnDigits,
                                      String crypto){
        int codeDigits = Integer.decode(returnDigits).intValue();
        String result = null;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16 )
            time = "0" + time;

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);


        byte[] hash = hmac_sha(crypto, k, msg);
        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary =
                ((hash[offset] & 0x7f) << 24) |
                        ((hash[offset + 1] & 0xff) << 16) |
                        ((hash[offset + 2] & 0xff) << 8) |
                        (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }

    public static String getCurrentOTP(String ID, SharedPreferences deviceInfo){
        ID=CurrentLoggedInID.ID;
        String result="";
        if(CurrentLoggedInID.isLoggedIn=true){
            long T0 = 0;
            long X = 30;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            try {
                String key=deviceInfo.getString(ID, "no key found");
                if(key.equals("no key found")){
                    return key;
                }
                String mySeed=stringToHex(key); // 저장된 OTP Key를 시드로 변환!!!!
                System.out.println("test"+mySeed);
                for (int i=0; i<1; i++) {
                    long T= (System.currentTimeMillis()/1000 - T0)/X;
                    String steps = Long.toHexString(T).toUpperCase();
                    while (steps.length()<16) steps = "0" + steps;
                    String fmtTime = String.format("%1$-11s", System.currentTimeMillis()/1000);
                    String utcTime = df.format(new Date(System.currentTimeMillis()));
                    Log.e("OTP", "--------result------");
                    result = generateTOTP(mySeed, steps, "6",
                            "HmacSHA256");
                    System.out.print("|  " + fmtTime + "  |  " + utcTime +
                            "  | " + steps + " |");
                    System.out.println(result + "| SHA256 |");
                }
            }catch (final Exception e){
                System.out.println("Error : " + e);
                return "error";
            }
        }
        return result;
    }

}
