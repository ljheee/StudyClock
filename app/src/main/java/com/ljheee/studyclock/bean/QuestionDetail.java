package com.ljheee.studyclock.bean;



import java.util.List;

public class QuestionDetail {

    private int qid;
    private String uid;   //用户id
    private int majorId;  //所属专业
    private String title;  //问题题目
    private String detail; //问题内容
    private String addTime;  //添加时间
    List<Comment> comments;
    private int commtAgree;


    public int getCommtAgree() {
        return commtAgree;
    }
    public void setCommtAgree(int commtAgree) {
        this.commtAgree = commtAgree;
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
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}

