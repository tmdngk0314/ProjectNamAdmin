package com.example.projectnamadmin;

public class CheckLockerInfo {
    String date;
    String lockernum;
    public CheckLockerInfo(String lockernum,  String date){
        this.lockernum = lockernum;
        this.date = date;}

    public String getLockernum(){ return lockernum;}

    public void setLockernum(String lockernum){this.lockernum = lockernum;}

    public String getLockerState(){return date;}

    public void setDate(String date){this.date = date;}
}
