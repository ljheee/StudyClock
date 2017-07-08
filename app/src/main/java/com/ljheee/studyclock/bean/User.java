package com.ljheee.studyclock.bean;



/**
 *用户
 */
public class User {

    private String uid;
    private String name;
    private String password;
    private int askLevelId;
    private int majorId;
    private int totalScore;
    private int dayScore;
    private int loginDay;

    public User() {
    }

    public User(String uid, String password) {
        this.uid = uid;
        this.password = password;
    }




    public User(String uid, String name, String password, int askLevelId, int majorId, int totalScore, int dayScore,
                int loginDay) {
        super();
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.askLevelId = askLevelId;
        this.majorId = majorId;
        this.totalScore = totalScore;
        this.dayScore = dayScore;
        this.loginDay = loginDay;
    }





    public User(String uid) {
        this.uid = uid;
    }




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAskLevelId() {
        return askLevelId;
    }

    public void setAskLevelId(int askLevelId) {
        this.askLevelId = askLevelId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getDayScore() {
        return dayScore;
    }

    public void setDayScore(int dayScore) {
        this.dayScore = dayScore;
    }

    public int getLoginDay() {
        return loginDay;
    }

    public void setLoginDay(int loginDay) {
        this.loginDay = loginDay;
    }



}

