package com.example.notify;

public class student {

    String name, rollno, phoneno, password;

    student() {

    }

    public student(String name, String rollno, String phoneno, String password) {
        this.name = name;
        this.rollno = rollno;
        this.phoneno = phoneno;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getRollno() {
        return rollno;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getPassword() {
        return password;
    }
}
