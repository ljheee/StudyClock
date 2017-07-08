package com.ljheee.studyclock.bean;





/**
 *问题
 */
public class QuestionRecord {

    private int qid;
    private String uid;   //用户id
    private int majorId;  //所属专业
    private String title;  //问题题目
    private String detail; //问题内容
    private String addTime;  //添加时间


    public QuestionRecord() {
    }



    public QuestionRecord(String uid, int majorId, String title, String detail) {
        super();
        this.uid = uid;
        this.majorId = majorId;
        this.title = title;
        this.detail = detail;
    }




    public QuestionRecord(int qid, String uid, int majorId, String title,
                          String detail, String addTime) {
        super();
        this.qid = qid;
        this.uid = uid;
        this.majorId = majorId;
        this.title = title;
        this.detail = detail;
        this.addTime = addTime;
    }







    public int getQid() {
        return qid;
    }


    public void setQid(int qid) {
        this.qid = qid;
    }


    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }


    public int getMajorId() {
        return majorId;
    }


    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDetail() {
        return detail;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }



    public String getAddTime() {
        return addTime;
    }



    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }



}

