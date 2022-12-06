package com.example.worktracking.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Year implements Serializable {
    private String Year;
    private ArrayList<MyDate> dates;
    public Year(String year) {
        dates = new ArrayList<>();
        Year = year;
    }
    public void AddDate (MyDate date){ dates.add(date); }
    public void RemoveDate (MyDate date){ dates.remove(date); }
    public ArrayList<MyDate> getDates() {
        return dates;
    }
    public void setDates(ArrayList<MyDate> dates) {
        this.dates = dates;
    }
    public String getYear() { return Year; }
    public void setYear(String year) { Year = year; }
}
