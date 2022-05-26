package com.example.projectnamadmin;

public class LockerDetailInfo {

    public int lockernum;
    public String status;

    public LockerDetailInfo(int lockernum, String status){
        this.lockernum = lockernum;
        this.status = status;
        }

    public int getLockernum(){ return lockernum;}

    public void setLockernum(int lockernum){this.lockernum = lockernum;}

    public String getLockerstatus(){ return status;}

    public void setLockerStatus(String status){this.status= status;}


}
