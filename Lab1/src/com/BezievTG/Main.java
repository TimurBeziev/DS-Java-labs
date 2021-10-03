package com.BezievTG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Main {
    private static JFrame frame;
    private static JTextArea main_text_area;

    private static void InitializeUI() {
        frame = new JFrame("LABA 1 JAVA");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 400);

        main_text_area = new JTextArea();

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
        JButton format_text = new JButton("Format text");
        format_text.addActionListener(new FormatTextListener());
        lower_panel.add(format_text);

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

    private static class OpenFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                    String text_from_file;
                    while ((text_from_file = br.readLine()) != null) {
                        main_text_area.append(text_from_file);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void WriteToFile(File file, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getName(), false);
        fileWriter.write(text);
        fileWriter.close();
    }

    private static class SaveFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    if (selectedFile.createNewFile()) {
                        WriteToFile(selectedFile, main_text_area.getText());
                    } else {
                        int input = JOptionPane.showConfirmDialog(null,
                                "File already exists. Do you want to rewrite it?", "Attention!", JOptionPane.YES_NO_OPTION);
                        if (input == 0) {
                            WriteToFile(selectedFile, main_text_area.getText());
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        InitializeUI();
    }
}
