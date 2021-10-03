package com.BezievTG;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    private static JFrame frame;
    private static JTextArea main_text_area;
    private static JLabel save_file_label;
    private static boolean is_file_saved = true;

    private static void InitializeUI() {
        frame = new JFrame("LABA 1 JAVA");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!is_file_saved) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Do you want to save the file before closing?",
                            "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        ShowSaveFileDialog();
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
        frame.setSize(400, 400);

        main_text_area = new JTextArea();
        main_text_area.getDocument().addDocumentListener(new ChangeFileListener());

        JMenuBar menu = new JMenuBar();
        JMenu file_bar = new JMenu("FILE");
        JMenuItem open_file = new JMenuItem("Open");
        JMenuItem save_file_as = new JMenuItem("Save as");

        open_file.addActionListener(new OpenFileListener());
        save_file_as.addActionListener(new SaveFileListener());

        menu.add(file_bar);
        file_bar.add(open_file);
        file_bar.add(save_file_as);

        JPanel lower_panel = new JPanel();
        save_file_label = new JLabel();
        IsFileSaved(true);
        JButton format_text = new JButton("Format text");
        format_text.addActionListener(new FormatTextListener());
        lower_panel.add(format_text);
        lower_panel.add(save_file_label);

        frame.getContentPane().add(BorderLayout.SOUTH, lower_panel);
        frame.getContentPane().add(BorderLayout.NORTH, menu);
        frame.getContentPane().add(BorderLayout.CENTER, main_text_area);
        frame.setVisible(true);
    }

    private static class FormatTextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String formatted_text = FixPunctuation();
            try {
                ShowText(formatted_text);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String FixPunctuation() {
        String input_text = main_text_area.getText();
        input_text = input_text.
                replaceAll("( )+", " ").
                replaceAll("\s+(?=[,.?!:«()»;…])", "").
                replaceAll("«+\s*", " «").
                replaceAll("[(]+\s*", " (");
        return input_text;
    }

    private static void ShowText(String text) throws InterruptedException {
        main_text_area.setText(text);
    }

    private static void ShowOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                String text_from_file;
                main_text_area.setText("");
                while ((text_from_file = br.readLine()) != null) {
                    main_text_area.append(text_from_file);
                }
                IsFileSaved(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class OpenFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!is_file_saved) {
                int input = JOptionPane.showConfirmDialog(
                        null,
                        "File is not saved. Do you want to save it?",
                        "Attention!",
                        JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    ShowSaveFileDialog();
                }
            }
            ShowOpenFileDialog();
        }
    }

    private static void ShowSaveFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (selectedFile.createNewFile()) {
                    WriteToFile(selectedFile, main_text_area.getText());
                    IsFileSaved(true);
                } else {
                    int input = JOptionPane.showConfirmDialog(
                            null,
                            "File already exists. Do you want to rewrite it?",
                            "Attention!",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (input == 0) {
                        WriteToFile(selectedFile, main_text_area.getText());
                        IsFileSaved(true);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class SaveFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowSaveFileDialog();
        }
    }

    private static void WriteToFile(File file, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getName(), false);
        fileWriter.write(text);
        fileWriter.close();
    }

    private static void IsFileSaved(boolean is_saved) {
        is_file_saved = is_saved;
        if (is_saved) {
            save_file_label.setText("");
        } else {
            save_file_label.setText("Edited");
        }
    }

    private static class ChangeFileListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            IsFileSaved(false);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            IsFileSaved(false);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            IsFileSaved(false);
        }
    }

    public static void main(String[] args) {
        InitializeUI();
    }
}
