package com.android.tusk.Admin.model;

public class StudentsList {
    private String rollno, name;

    public StudentsList(String rollno, String name) {
        this.rollno = rollno;
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
