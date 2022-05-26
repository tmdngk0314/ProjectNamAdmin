package com.example.projectnamadmin;

public class NoticeInfo extends CallRestApi{
    public String result;
    public Integer[] index;
    public String[] title;
    public String[] date;
    public String[] body;
    public Integer noticeCount;

    public NoticeInfo(){
        index=new Integer[10];
        title=new String[10];
        date=new String[10];
        body=new String[10];
    }

    public Integer[] getIndex() {
        return index;
    }

    public void setIndex(Integer[] index) {
        this.index = index;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String[] getBody() {
        return body;
    }

    public void setBody(String[] body) {
        this.body = body;
    }

    public Integer getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(Integer noticeCount) {
        this.noticeCount = noticeCount;
    }

}
