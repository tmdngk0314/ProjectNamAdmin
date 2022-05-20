package com.example.projectnamadmin;

public class LockerStatusInfo {
    public String result="";
    private int count;
    private String[] status;
    public void setSize(int size){
       count=size;
       status=new String[count];
    }
    public void setStatus(String inputStatus, int lockernum){
        status[lockernum-1]=inputStatus;
    }
    public int getCount(){
        return count;
    }
    public String getStatus(int lockernum){
        return status[lockernum-1];
    }
}
