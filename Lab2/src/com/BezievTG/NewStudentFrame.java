package com.BezievTG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewStudentFrame {
    private final JTextField studentSurnameField;
    private final JTextField studentGroup;
    private final JCheckBox exam1;
    private final JCheckBox exam2;
    private final SessionResults sessionResults;
    private final JLabel isCorrectInputData;
    private final JTextArea textArea;

    public NewStudentFrame(SessionResults sessionResults, JTextArea mainTextArea) {
        JDialog studentFrame = new JDialog();
        Container content = studentFrame.getContentPane();
        content.setLayout(new GridLayout(8, 1));

        this.sessionResults = sessionResults;
        this.textArea = mainTextArea;
        studentSurnameField = new JTextField(20);
        content.add(new JLabel("Surname"));
        content.add(studentSurnameField);

        studentGroup = new JTextField(10);
        content.add(new JLabel("Group"));
        content.add(studentGroup);

        exam1 = new JCheckBox("Exam 1 passed", true);
        exam2 = new JCheckBox("Exam 2 passed", true);
        content.add(exam1);
        content.add(exam2);

        isCorrectInputData = new JLabel("");
        content.add(isCorrectInputData);

        JButton confirmNewStudentButton = new JButton("Add student to list");
        confirmNewStudentButton.addActionListener(new confirmNewStudentListener());
        content.add(confirmNewStudentButton, BorderLayout.SOUTH);

        studentFrame.setSize(400, 400);
        studentFrame.setVisible(true);
    }

    private void updateStudentsInfo() {
        textArea.setText("");
        for (Student student : sessionResults.getStudents()) {
            textArea.append(student.toString());
        }
    }

    private class confirmNewStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (studentSurnameField.getText().isEmpty() || studentGroup.getText().isEmpty()) {
                isCorrectInputData.setText("Surname or Group field is empty!");
                return;
            }
            int group;
            try {
                group = Integer.parseInt(studentGroup.getText());
            } catch (NumberFormatException exception) {
                isCorrectInputData.setText("Incorrect Group number!");
                return;
            }
            if (group < 1) {
                isCorrectInputData.setText("Incorrect Group number!");
                return;
            }
            isCorrectInputData.setText("");
            Student student = new Student(
                    studentSurnameField.getText(),
                    group,
                    exam1.isSelected(),
                    exam2.isSelected());
            sessionResults.addStudent(student);
            updateStudentsInfo();
        }
    }
}
