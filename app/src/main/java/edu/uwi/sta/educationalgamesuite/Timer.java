package edu.uwi.sta.educationalgamesuite;

/**
 * Created by kwasi on 4/18/2016.
 */
public class Timer {
    private long startTime,currTime,timeDiff;

    public Timer(){
        init();
    }
    private void init(){
        startTime = Math.abs(System.currentTimeMillis());
    }

    private void updateTime(){
        currTime= Math.abs(System.currentTimeMillis());
        timeDiff = (currTime-startTime)/1000;
    }
    public String toString(){
        updateTime();
        long hour,sec,min;
        sec =timeDiff %60;
        min = (timeDiff/60)%60;
        hour = (timeDiff/360)%60;
        return String.format("%02d:%02d:%02d",hour,min,sec);
    }
}
