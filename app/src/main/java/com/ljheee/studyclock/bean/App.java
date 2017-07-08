package com.ljheee.studyclock.bean;



/**
 *软件App
 */
public class App {

    private int appId;
    private int appTypeId;
    private String appName;
    private float appHours;
    private int useNum;
    private String appPkg;

    public App() {
    }

    public App(int appId, int appTypeId, String appName, float appHours, int useNum, String appPkg) {
        super();
        this.appId = appId;
        this.appTypeId = appTypeId;
        this.appName = appName;
        this.appHours = appHours;
        this.useNum = useNum;
        this.appPkg = appPkg;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAppTypeId() {
        return appTypeId;
    }

    public void setAppTypeId(int appTypeId) {
        this.appTypeId = appTypeId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public float getAppHours() {
        return appHours;
    }

    public void setAppHours(float appHours) {
        this.appHours = appHours;
    }

    public int getUseNum() {
        return useNum;
    }

    public void setUseNum(int useNum) {
        this.useNum = useNum;
    }

    public String getAppPkg() {
        return appPkg;
    }

    public void setAppPkg(String appPkg) {
        this.appPkg = appPkg;
    }



}

