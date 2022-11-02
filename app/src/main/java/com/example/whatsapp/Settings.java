package com.example.whatsapp;

public class Settings {
    private String  time;
    private String percent;
    private int count;
    //private boolean silence;

    public Settings(String time,String percent){
        this.time=time;
        this.percent=percent;
        toCount();

    }
    public int toCount(){
        if(time.equals("5m")){
            count=1;
        }
        if(time.equals("10m")){
            count=2;
        }
        if(time.equals("15m")){
            count=3;
        }
        return count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
