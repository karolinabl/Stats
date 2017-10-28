package com.stats;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Form extends JFrame {
    private JPanel panel;
    private JTextArea resultArea;
    private JComboBox attList;
    private JButton processButton;
    private JTextField pathField;
    private JButton fileButton;
    Weka data;
    String result = "";

    public Form() {

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!attList.getSelectedItem().toString().equals("")) {
                    String attribute = attList.getSelectedItem().toString();
                    if (!attribute.equals("All Attributes")) {
                        if (data.attributeExist(attribute)) {
                            processing(data.findIndexAttribute(attribute));
                        }
                    }
                    else {
                        for (int i = 0; i < data.getNumAttributes(); i++) {
                            processing(i);
                            result += System.getProperty("line.separator");
                        }
                    }
                }
                result = "";
            }
        });

        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadData();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void processing(int index) {
        result += data.getStatistics(index);
        Results.setResult(result, TabbedForm.getTab());
        resultArea.setText(result);
    }

    public  JPanel getPanel() {
        return panel;
    }

    public void fillComboBox() {
        attList.removeAllItems();
        for (int i = 0; i < data.getNumAttributes(); i++) {
            attList.addItem(data.getAttribute(i).toString());
        }
        attList.addItem("All Attributes");
    }

    public void loadData() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int choosenVal = fileChooser.showOpenDialog(Form.this);

        if (choosenVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().toString();

            pathField.setText(path);
            data = new Weka(path);
            Results.addData(data);
            fillComboBox();
        }
    }

}
