package com.BezievTG;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static JFrame frame;
    private static JTextArea mainTextArea;
    private JTextArea openTagArea;

    public void initializeUI() {
        frame = new JFrame("LAB 4 JAVA");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(exitListener);
        frame.setSize(1920, 1080);

        mainTextArea = new JTextArea();
        mainTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(mainTextArea);
        TextLineNumber tln = new TextLineNumber(mainTextArea);
        scrollPane.setRowHeaderView(tln);


        JMenuBar menu = new JMenuBar();
        JMenu fileBar = new JMenu("FILE");
        JMenuItem openFile = new JMenuItem("Open");

        openFile.addActionListener(new openFileListener());

        menu.add(fileBar);
        fileBar.add(openFile);

        JPanel lowerPanel = new JPanel();
        openTagArea = new JTextArea();
        JButton findClosingTag = new JButton("Find Closing Tag");
        findClosingTag.addActionListener(new findClosingTagListener());
        openTagArea.setColumns(10);
        lowerPanel.add(findClosingTag);
        lowerPanel.add(openTagArea);

        frame.getContentPane().add(BorderLayout.SOUTH, lowerPanel);
        frame.getContentPane().add(BorderLayout.NORTH, menu);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.setVisible(true);

    }
        private class findClosingTagListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String temp = openTagArea.getText();
            if (temp.isEmpty()) {
                return;
            }
            String closeTag = "</" + temp + "[^>/]*>";
            String openCloseTag = "<" + temp + "[^>]*/>";
            String regex = closeTag + "|" + openCloseTag;
            String text = mainTextArea.getText();
            Highlighter hl = mainTextArea.getHighlighter();
            hl.removeAllHighlights();

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                try {
                    hl.addHighlight(matcher.start(), matcher.end(), DefaultHighlighter.DefaultPainter);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            openFile(fileChooser.getSelectedFile());
        }
    }

    private void openFile(File file) {
        try {
            mainTextArea.setText("");
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                mainTextArea.append(line);
                mainTextArea.append("\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private class openFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showOpenFileDialog();
        }
    }

    public static void main(String[] args) {
        Main lab3 = new Main();
        lab3.initializeUI();
    }
}
