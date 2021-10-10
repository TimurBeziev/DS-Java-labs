package com.BezievTG;

import java.io.Serializable;

public class Student implements Serializable {
    public Student(String surname, int group, boolean exam1Passed, boolean exam2Passed) {
        this.surname = surname;
        this.group = group;
        this.exam1Passed = exam1Passed;
        this.exam2Passed = exam2Passed;
    }

    public String getSurname() {
        return surname;
    }

    public int getGroup() {
        return group;
    }

    public boolean isDebtor() {
        return !(exam1Passed & exam2Passed);
    }

    private final String surname;
    private final int group;
    private final boolean exam1Passed;
    private final boolean exam2Passed;

    @Override
    public String toString() {
        return surname + '\t' + group + '\t' +
                "Exam1 passed:\t " + exam1Passed + '\t' +
                "Exam2 passed:\t " + exam2Passed + '\n';
    }
}
