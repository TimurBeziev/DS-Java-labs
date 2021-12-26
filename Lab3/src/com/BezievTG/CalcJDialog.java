package com.BezievTG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

public class CalcJDialog extends JDialog implements ActionListener {

    private JButton exec = new JButton("Run");
    private Method method;
    private ArrayList<JTextField> args = new ArrayList<>();
    private JTextField result = new JTextField("");


    public CalcJDialog(JFrame parent, String title, Method m, ResourceBundle currentLanguage) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.method = m;
        exec.addActionListener(this);
        setTitle(currentLanguage.getString("DialogTitleText"));

        exec.setText(currentLanguage.getString("execText"));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(method.getParameterCount() + 2, 2));

        for (Parameter arg : method.getParameters()) {
            JTextField text = new JTextField();
            args.add(text);
            container.add(new Label("   " + arg.toString()));
            container.add(text);
        }
        container.add(new Label("   " + currentLanguage.getString("resultText")));
        container.add(result);
        container.add(new Label(""));
        container.add(exec);

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exec) {
            try {
                Object[] arguments = new Object[args.size()];
                int id = 0;
                String temp;

                for (JTextField jtf : args) {
                    if (jtf.getText().equals("")) {
                        throw new DataFormatException("Set arguments!");
                    }
                    temp = method.getParameters()[id].getType().toString();
                    if (temp.equals("int") || temp.equals("class java.lang.Integer")) {
                        arguments[id] = Integer.parseInt(jtf.getText());
                    } else if (temp.equals("long") || temp.equals("class java.lang.Long")) {
                        arguments[id] = Long.parseLong(jtf.getText());
                    } else if (temp.equals("double") || temp.equals("class java.lang.Double")) {
                        arguments[id] = Double.parseDouble(jtf.getText());
                    } else if (temp.equals("float") || temp.equals("class java.lang.Float")) {
                        arguments[id] = Float.parseFloat(jtf.getText());
                    } else {
                        arguments[id] = jtf.getText();
                    }
                    id++;
                }

                System.out.println(method.toString());
                System.out.println(method.getDeclaringClass());
                Constructor<?> ctor = method.getDeclaringClass().getConstructor();
                Object res = method.invoke(ctor.newInstance(), arguments);
                result.setText(res.toString());

            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(this, err, "Error!", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(this, err.getMessage(), "Error!", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}