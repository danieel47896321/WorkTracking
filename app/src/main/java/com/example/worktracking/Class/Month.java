package com.example.worktracking.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Month implements Serializable {
    private String Month;
    private ArrayList<MyDate> Dates;
    public Month(String month) {
        Dates = new ArrayList<>();
        Month = month;
    }
    public String getMonth() {
        return Month;
    }
    public void setMonth(String month) {
        Month = month;
    }
    public void AddDate (MyDate date){ Dates.add(date); }
    public void RemoveDate (MyDate date){ Dates.remove(date); }
    public ArrayList<MyDate> getDates() {
        return Dates;
    }
    public void setDates(ArrayList<MyDate> dates) {
        this.Dates = dates;
    }
}
