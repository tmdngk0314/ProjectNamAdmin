package com.example.projectnamadmin;

public class IllegalOpenHistoryInfo {
    String result;
    int count;
    int num[];
    String time[];
    int lockernum[];

    public void init(int count){
        this.count=count;
        num=new int[count];
        time=new String[count];
        lockernum=new int[count];
    }
}
