package com.BezievTG;

import java.util.ArrayList;

public class SessionResults {
    private final ArrayList<Student> students;
    public SessionResults() {
        students = new ArrayList<>();
    }
    public void addStudent(Student student) {
        if (student == null) {
            return;
        }
        students.add(student);
    }
    public ArrayList<Student> getStudents() {
        return students;
    }
}
