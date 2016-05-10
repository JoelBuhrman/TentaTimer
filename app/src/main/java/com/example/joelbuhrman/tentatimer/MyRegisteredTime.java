package com.example.joelbuhrman.tentatimer;

/**
 * Created by JoelBuhrman on 16-05-10.
 */
public class MyRegisteredTime {
    private String course;
    private long time;

    public MyRegisteredTime(String course, long time) {
        this.course=course;
        this.time= time;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCourse() {

        return course;
    }

    public long getTime() {
        return time;
    }
}
