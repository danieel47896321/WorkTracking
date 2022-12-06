package com.example.worktracking.Class;

public class MyDate {
    private String Month;
    private String Company;
    private String Day;
    private String Date;
    private String StartTime;
    private String EndTime;
    private String TotalWorkTime;
    public MyDate(String month, String company, String day, String date, String startTime, String endTime, String totalWorkTime) {
        Month = month;
        Company = company;
        Day = day;
        Date = date;
        StartTime = startTime;
        EndTime = endTime;
        TotalWorkTime = totalWorkTime;
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
