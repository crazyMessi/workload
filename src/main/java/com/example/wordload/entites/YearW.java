package com.example.wordload.entites;

public class YearW {
    public int getMunth() {
        return munth;
    }

    public void setMunth(int munth) {
        this.munth = munth;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    int munth;
    double hour;

    public YearW(int munth, double hour) {
        this.munth = munth;
        this.hour = hour;
    }
}
