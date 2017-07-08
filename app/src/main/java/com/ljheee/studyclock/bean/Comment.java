package com.ljheee.studyclock.bean;


/**
 *评论
 */
public class Comment {

    int cid;//评论id
    int qid;//针对的  QuestionRecord的id
    String uid;// 评论人
    String content;//评论内容
    int isAdopt;
    String addTime;

    public Comment() {
    }


    public Comment(int qid, String uid, String content, int isAdopt,
                   String addTime) {
        this.qid = qid;
        this.uid = uid;
        this.content = content;
        this.isAdopt = isAdopt;
        this.addTime = addTime;
    }


    public Comment(int cid, int qid, String uid, String content, int isAdopt, String addTime) {
        super();
        this.cid = cid;
        this.qid = qid;
        this.uid = uid;
        this.content = content;
        this.isAdopt = isAdopt;
        this.addTime = addTime;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsAdopt() {
        return isAdopt;
    }

    public void setIsAdopt(int isAdopt) {
        this.isAdopt = isAdopt;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }




}

