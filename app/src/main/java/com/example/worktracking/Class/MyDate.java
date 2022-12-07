package com.example.worktracking.Class;

import com.example.worktracking.R;

import java.io.Serializable;

public class MyDate implements Serializable {
    private String Id;
    private String Month;
    private String Company;
    private String Day;
    private String Year;
    private String Date;
    private String StartTime;
    private String EndTime;
    private String TotalWorkTime;
    public MyDate() { }
    public MyDate(String id, String month, String company, String day, String year, String startTime, String endTime) {
        Id = id;
        Month = month;
        Year = year;
        Company = company;
        Day = day;
        Date = day + "/" + month + "/" + year;
        StartTime = startTime;
        EndTime = endTime;
        TotalWorkTime = (Integer.valueOf(StartTime.substring(0,2)) - Integer.valueOf(EndTime.substring(0,2))) + ":" + (Integer.valueOf(StartTime.substring(3,5)) - Integer.valueOf(EndTime.substring(3,5)));
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getYear() {
        return Year;
    }
    public void setYear(String year) {
        Year = year;
    }
    public String getMonth() {
        return Month;
    }
    public void setMonth(String month) {
        Month = month;
    }
    public String getCompany() {
        return Company;
    }
    public void setCompany(String company) {
        Company = company;
    }
    public String getDay() {
        return Day;
    }
    public void setDay(String day) {
        Day = day;
    }
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public String getStartTime() {
        return StartTime;
    }
    public void setStartTime(String startTime) {
        StartTime = startTime;
    }
    public String getEndTime() {
        return EndTime;
    }
    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
    public String getTotalWorkTime() {
        return TotalWorkTime;
    }
    public void setTotalWorkTime(String totalWorkTime) {
        TotalWorkTime = totalWorkTime;
    }
}
