package com.BezievTG;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static JFrame frame;
    private static JTextArea mainTextArea;
    private static JLabel saveFileLabel;
    private static boolean isFileSaved = true;
    private SessionResults sessionResults;

    public void initializeUI() {
        frame = new JFrame("LABA 2 JAVA");
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
        lowerPanel.add(addStudent);
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

    private void showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                mainTextArea.setText("");
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                for (String line : lines) {
                    mainTextArea.append(line);
                    mainTextArea.append("\n");
                }
                isFileSaved(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (selectedFile.createNewFile()) {
                    writeToFile(selectedFile, mainTextArea.getText());
                    isFileSaved(true);
                } else {
                    int input = JOptionPane.showConfirmDialog(
                            null,
                            "File already exists. Do you want to rewrite it?",
                            "Attention!",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (input == 0) {
                        writeToFile(selectedFile, mainTextArea.getText());
                        isFileSaved(true);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
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

    private void writeToFile(File file, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getName(), false);
        fileWriter.write(text);
        fileWriter.close();
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
