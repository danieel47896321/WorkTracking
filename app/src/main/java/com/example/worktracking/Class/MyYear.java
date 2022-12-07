package com.example.worktracking.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class MyYear implements Serializable, Comparable<MyYear> {
    private int Year;
    private ArrayList<Month> Months;
    public MyYear() { }
    public MyYear(int year) {
        Months = new ArrayList<>();
        Year = year;
    }
    public int getYear() {
        return Year;
    }
    public void setYear(int year) {
        Year = year;
    }
    public void AddMonth(Month month) { Months.add(month); }
    public void AddRemove(Month month) { Months.remove(month); }
    public ArrayList<Month> getMonths() {
        return Months;
    }
    public void setMonths(ArrayList<Month> months) {
        Months = months;
    }
    @Override
    public int compareTo(MyYear o) {
        return o.getYear() - this.getYear();
    }
}
