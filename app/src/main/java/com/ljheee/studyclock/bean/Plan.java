package com.ljheee.studyclock.bean;


public class Plan {


    private String name = "未命名";

    public Plan(){
    }
    public Plan(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
