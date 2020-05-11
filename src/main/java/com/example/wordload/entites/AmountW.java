package com.example.wordload.entites;

public class AmountW {
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public AmountW(int year, double hour) {
        this.year = year;
        this.hour = hour;
    }

    int year;
    double hour;
}
