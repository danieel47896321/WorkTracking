package com.example.worktracking.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Month implements Serializable, Comparable<Month> {
    private String Month;
    private ArrayList<MyDate> Dates;
    public Month() {}
    public Month(String month) {
        Dates = new ArrayList<>();
        Month = month;
    }
    public String getMonth() {
        return Month;
    }
    public int getMonthID(){
        if(Month.equals("January")){
            return 1;
        }else if(Month.equals("February")){
            return 2;
        }else if(Month.equals("March")){
            return 3;
        }else if(Month.equals("April")){
            return 4;
        }else if(Month.equals("May")){
            return 5;
        }else if(Month.equals("June")){
            return 6;
        }else if(Month.equals("July")){
            return 7;
        }else if(Month.equals("August")){
            return 8;
        }else if(Month.equals("September")){
            return 9;
        }else if(Month.equals("October")){
            return 10;
        }else if(Month.equals("November")){
            return 11;
        }else if(Month.equals("December")){
            return 12;
        }
        return 1;
    }
    public void setMonth(String month) {
        Month = month;
    }
    public ArrayList<MyDate> getDates() {
        return Dates;
    }
    public void setDates(ArrayList<MyDate> dates) {
        this.Dates = dates;
    }
    @Override
    public int compareTo(Month o) {
        return this.getMonthID() - o.getMonthID();
    }
}
