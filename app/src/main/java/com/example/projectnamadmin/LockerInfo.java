package com.example.projectnamadmin;

public class LockerInfo {
    public String result;
    public String[] lockername;
    public String[] location;


    public void init(int lockercount){
        lockername=new String[lockercount];
        location=new String[lockercount];

    }



    public String[] getLockername() {
        return lockername;
    }

    public void setLockername(String[] lockername) {
        this.lockername = lockername;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }




}
