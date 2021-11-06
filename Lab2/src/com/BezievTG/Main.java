package com.BezievTG;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    private static JFrame frame;
    private static JTextArea mainTextArea;
    private static JLabel saveFileLabel;
    private static boolean isFileSaved = true;
    private SessionResults sessionResults;

    public void initializeUI() {
        frame = new JFrame("LAB 2 JAVA");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!isFileSaved) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Do you want to save the file before closing?",
                            "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        if (showSaveFileDialog() != JFileChooser.APPROVE_OPTION) {
                            return;
                        }
                        System.exit(0);
                    }
                    if (confirm == 1) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        };
        frame.addWindowListener(exitListener);
        frame.setSize(1920, 1080);

        mainTextArea = new JTextArea();
        mainTextArea.getDocument().addDocumentListener(new changeFileListener());
        mainTextArea.setEditable(false);

        JMenuBar menu = new JMenuBar();
        JMenu fileBar = new JMenu("FILE");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFileAs = new JMenuItem("Save as");

        openFile.addActionListener(new openFileListener());
        saveFileAs.addActionListener(new saveFileListener());

        menu.add(fileBar);
        fileBar.add(openFile);
        fileBar.add(saveFileAs);

        JPanel lowerPanel = new JPanel();
        saveFileLabel = new JLabel();
        isFileSaved(true);
        JButton addStudent = new JButton("Add Student");
        addStudent.addActionListener(new addStudentListener());
        JButton showDebtorStudents = new JButton("Show Debtor Students");
        showDebtorStudents.addActionListener(new showDebtorsListener());
        JButton showAllStudents = new JButton("Show All Students");
        showAllStudents.addActionListener(new showAllStudentsListener());
        lowerPanel.add(addStudent);
        lowerPanel.add(showDebtorStudents);
        lowerPanel.add(showAllStudents);
        lowerPanel.add(saveFileLabel);

        sessionResults = new SessionResults();

        frame.getContentPane().add(BorderLayout.SOUTH, lowerPanel);
        frame.getContentPane().add(BorderLayout.NORTH, menu);
        frame.getContentPane().add(BorderLayout.CENTER, mainTextArea);
        frame.setVisible(true);
    }

    private class addStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NewStudentFrame newStudentFrame = new NewStudentFrame(sessionResults, mainTextArea);
        }
    }

    private class showDebtorsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateStudentsInfo(true);
        }
    }   private class showAllStudentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateStudentsInfo(false);
        }
    }

    private void showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            openFile(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void openFile(String path) {
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(path));
            sessionResults = (SessionResults) objectInputStream.readObject();
            objectInputStream.close();
            updateStudentsInfo(false);
            isFileSaved(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateStudentsInfo(boolean onlyDebtors) {
        mainTextArea.setText("");
        for (Student student : sessionResults.getStudents()) {
            if (onlyDebtors && !student.isDebtor()) {
                continue;
            }
            mainTextArea.append(student.toString());
        }
    }

    private class openFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isFileSaved) {
                int input = JOptionPane.showConfirmDialog(
                        null,
                        "File is not saved. Do you want to save it?",
                        "Attention!",
                        JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    showSaveFileDialog();
                }
            }
            showOpenFileDialog();
        }
    }

    private int showSaveFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                writeToFile(fileChooser.getSelectedFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return option;
    }

    private class saveFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showSaveFileDialog();
        }
    }

    private void writeToFile(String path) throws IOException {
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(path));
            objectOutputStream.writeObject(sessionResults);
            isFileSaved(true);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isFileSaved(boolean isSaved) {
        isFileSaved = isSaved;
        if (isSaved) {
            saveFileLabel.setText("");
        } else {
            saveFileLabel.setText("Edited");
        }
    }

    private class changeFileListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            isFileSaved(false);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            isFileSaved(false);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            isFileSaved(false);
        }
    }

    public static void main(String[] args) {
        Main lab2 = new Main();
        lab2.initializeUI();
    }
}
