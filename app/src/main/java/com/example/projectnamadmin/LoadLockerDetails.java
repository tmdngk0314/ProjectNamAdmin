package com.example.projectnamadmin;

public class LoadLockerDetails {
    public String result="";
    int lockernum;
    private String name;
    private String id;
    private String email;
    private String startdate;
    private String status;
    private String enddate;

    public LoadLockerDetails(int lockernum){
        this.lockernum = lockernum;

    }

    public String getStatus() {
        return status;
    }

    public String getname() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    public String getid() {
        return id;
    }

    public String getemail() {
        return email;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public int getLockernum() {
        return lockernum;
    }




}
