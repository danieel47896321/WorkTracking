package com.example.worktracking.Class;

import java.io.Serializable;
import java.util.ArrayList;

public class Year implements Serializable {
    private String Year;
    private ArrayList<Month> Months;
    private Boolean Clicked = false;
    public Year(String year) {
        Months = new ArrayList<>();
        Year = year;
    }
    public Boolean getClicked() {
        return Clicked;
    }
    public void setClicked(Boolean clicked) {
        Clicked = clicked;
    }
    public void AddMonth (Month month){ Months.add(month); }
    public void RemoveMonth (Month month){ Months.remove(month); }
    public ArrayList<Month> getMonths() {
        return Months;
    }
    public void setMonths(ArrayList<Month> months) {
        this.Months = months;
    }
    public String getYear() { return Year; }
    public void setYear(String year) { Year = year; }
}
