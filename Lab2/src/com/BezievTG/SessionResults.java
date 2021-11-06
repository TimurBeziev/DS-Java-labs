package com.BezievTG;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SessionResults implements Serializable {
    private List<Student> students;

    public SessionResults() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student == null) {
            return;
        }
        students.add(student);
        sortStudents();
    }

    public List<Student> getStudents() {
        return students;
    }

    private void sortStudents() {
        Comparator<Student> studentComparator
                = Comparator.comparing(Student::getGroup)
                .thenComparing(Student::getSurname);
        students = students.stream().sorted(studentComparator).collect(Collectors.toList());
    }
}
