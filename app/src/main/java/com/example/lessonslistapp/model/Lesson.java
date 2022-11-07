package com.example.lessonslistapp.model;

import java.util.Date;

public class Lesson {

    private String date;
    private Less less;

    public Lesson(String date, Less less) {
        this.date = date;
        this.less = less;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Less getLess() {
        return less;
    }

    public void setLess(Less less) {
        this.less = less;
    }
}
