package com.BezievTG;

import javax.swing.*;
import java.awt.*;

public class AddStudentFrame {
    public AddStudentFrame() {
        JDialog studentFrame = new JDialog();
        Container content = studentFrame.getContentPane();
        content.setLayout(new GridLayout(7, 1));

        JTextField studentSurnameField = new JTextField(20);
        content.add(new JLabel("Surname"));
        content.add(studentSurnameField);

        JTextField studentGroup = new JTextField(10);
        content.add(new JLabel("Group"));
        content.add(studentGroup);

        JCheckBox exam1 = new JCheckBox("Exam 1 passed");
        JCheckBox exam2 = new JCheckBox("Exam 2 passed");
        content.add(exam1);
        content.add(exam2);

        JButton addStudentButton = new JButton("Add student to list");
        content.add(addStudentButton, BorderLayout.SOUTH);

        studentFrame.setSize(400, 400);
        studentFrame.setVisible(true);
    }
}
