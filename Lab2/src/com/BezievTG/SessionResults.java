package com.BezievTG;

import java.util.ArrayList;

public class SessionResults {
    private ArrayList<Student> names;
    public SessionResults() {
        names = new ArrayList<>();
    }
    public void addStudent(Student student) {
        names.add(student);
    }
}
