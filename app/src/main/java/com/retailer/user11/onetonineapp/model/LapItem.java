package com.retailer.user11.onetonineapp.model;

public class LapItem {

    private int id;
    private long date;
    private String duration;
    private long interval;

    public long getDate() {
        return this.date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getInterval() {
        return this.interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
