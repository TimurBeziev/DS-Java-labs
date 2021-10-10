package com.BezievTG;

public class Student {
    public Student(String surname, int group, boolean exam1Passed, boolean exam2Passed) {
        this.surname = surname;
        this.group = group;
        this.exam1Passed = exam1Passed;
        this.exam2Passed = exam2Passed;
    }
    private String surname;
    private int group;
    private boolean exam1Passed;
    private boolean exam2Passed;
}
