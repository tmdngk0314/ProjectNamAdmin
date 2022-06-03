package com.example.projectnamadmin;

public class OverdueListInfo {
    String result;
    int count;
    int[] num;
    String[] name;
    String[] id;
    String[] email;
    int[] lockernum;
    String[] startdate;
    String[] enddate;
    String[] iscollected;
    String[] returntime;



    public void init(int count){
        this.count=count;
        num=new int[count];
        name=new String[count];
        id=new String[count];
        email=new String[count];
        lockernum=new int[count];
        startdate=new String[count];
        enddate=new String[count];
        iscollected=new String[count];
        returntime=new String[count];
    }

}
