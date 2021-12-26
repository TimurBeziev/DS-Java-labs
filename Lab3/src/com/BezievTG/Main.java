package com.BezievTG;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Main extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Main("Lab3");
    }

    ResourceBundle currentLanguage;
    ResourceBundle ruLanguage = ResourceBundle.getBundle("res", new Locale("Ru"));
    ResourceBundle enLanguage = ResourceBundle.getBundle("res", new Locale("En"));

    private JButton runButton = new JButton("Run");
    private JButton findClassButton = new JButton("Find Class");
    private JButton setRussianLanButton = new JButton("Русский");
    private JButton setEnglishLanButton = new JButton("EN");

    private List list = new List();
    private Method[] data;
    private JTextField jTextField = new JTextField(20);

    private JLabel timeInfo = new JLabel();
    private JLabel currencyInfo = new JLabel();


    public Main(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentLanguage = enLanguage;
        setDate(currentLanguage);
        setCurrency(currentLanguage);

        Container container = this.getContentPane();
        Box b = new Box(1);
        JPanel p = new JPanel();
        p.add(runButton);
        p.add(findClassButton);
        p.add(jTextField);
        p.setMaximumSize(new Dimension(1920, 100));

        runButton.addActionListener(this);
        findClassButton.addActionListener(this);
        setEnglishLanButton.addActionListener(this);
        setRussianLanButton.addActionListener(this);

        JPanel localizationPannel = new JPanel();
        localizationPannel.add(setEnglishLanButton);
        localizationPannel.add(setRussianLanButton);
        localizationPannel.add(timeInfo);
        localizationPannel.add(currencyInfo);
        localizationPannel.setMaximumSize(new Dimension(1920, 100));

        b.add(p);
        b.add(list);
        b.add(localizationPannel);
        container.add(b);
        pack();
        setSize(1920, 1080);
        setVisible(true);
    }

    private Method getSelectedMethod(String methodName) {
        for (Method method : data) {
            if (Objects.equals(method.toString(), methodName)) {
                return method;
            }
        }
        return null;
    }

    private void changeLanguage(ResourceBundle resourceBundle) {
        runButton.setText(resourceBundle.getString("runButtonText"));
        findClassButton.setText(resourceBundle.getString("findClassButtonText"));
    }

    private void setDate(ResourceBundle currentLanguage) {
        String dateValue = ZonedDateTime
                .now(TimeZone.getDefault().toZoneId())
                .format(DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.FULL)
                        .withLocale(currentLanguage.getLocale()));
        timeInfo.setText(dateValue);
    }

    private void setCurrency(ResourceBundle currentLanguage) {
        currencyInfo.setText(NumberFormat
                .getCurrencyInstance(currentLanguage.getLocale())
                .format(10));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == setRussianLanButton) {
            currentLanguage = ruLanguage;
            setDate(currentLanguage);
            setCurrency(currentLanguage);
            changeLanguage(ruLanguage);
        }
        if (e.getSource() == setEnglishLanButton) {
            currentLanguage = enLanguage;
            setDate(currentLanguage);
            setCurrency(currentLanguage);
            changeLanguage(enLanguage);
        }
        if (e.getSource() == runButton) {
            String method = list.getSelectedItem();

            if (!method.isEmpty()) {
                new CalcJDialog(this, "Execute", getSelectedMethod(method), currentLanguage);
            } else {
                JOptionPane.showMessageDialog(this, "Select element!", "Error!", JOptionPane.PLAIN_MESSAGE);
            }
        }
        if (e.getSource() == findClassButton) {
            String className = jTextField.getText();
            try {
                Class<?> temp = Class.forName(className);
                data = temp.getDeclaredMethods();
                show(list, data);
            } catch (ClassNotFoundException ex) {
//                ex.printStackTrace();
            }
        }
    }

    private void show(List list, Method[] arr) {
        if (arr != null) {
            list.removeAll();
            for (Method m : arr) {
                if (!m.toString().contains("static"))
                    list.add(m.toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "There are no elements!", "Error!", JOptionPane.PLAIN_MESSAGE);
        }

    }
}