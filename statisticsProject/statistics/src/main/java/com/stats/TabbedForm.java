package com.stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;


public class TabbedForm extends JFrame {
    private JPanel panel;
    static JTabbedPane tabs;
    int numTabs = 0;

    public TabbedForm() {
        panel.setLayout(new BorderLayout());

        JMenu menu = new JMenu("Menu");
        JMenuItem addTabItem = new JMenuItem("Nowa zakładka");
        JMenuItem closeTabItem = new JMenuItem("Zamknij zakładkę");
        JMenuItem saveItem = new JMenuItem("Zapisz wyniki");
        JMenuItem exitItem = new JMenuItem("Wyjście");

        addTabItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.add("Zakładka " + ++numTabs, new Form().getPanel());
                Results.addResult();
            }
        });

        closeTabItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabs.remove(tabs.getSelectedIndex());
                Results.removeResult(tabs.getSelectedIndex());
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveResult();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(addTabItem);
        menu.add(closeTabItem);
        menu.add(saveItem);
        menu.add(exitItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("Menu");
        menuBar.add(menu);
        menuBar.setVisible(true);
        panel.add(menuBar, BorderLayout.NORTH);
        tabs = new JTabbedPane();
        tabs.add("Zakładka " + ++numTabs, new Form().getPanel());
        Results.addResult();
        panel.add(tabs);

    }

    private void saveResult() throws IOException {
        String result = Results.getResult(getTab());

        if (!result.equals(null)) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int choosenVal = fileChooser.showOpenDialog(TabbedForm.this);

            if (choosenVal == JFileChooser.APPROVE_OPTION) {
                char slashSeparator = 92;
                String path = fileChooser.getSelectedFile().toString() + slashSeparator;
                String fileName = "wynik";
                String addon = ".txt";

                File file = new File(path + fileName + addon);
                int attempts = 1;
                while(file.exists()) {
                    fileName = "wynik(" + Integer.toString(attempts) + ")";
                    file = new File(path + fileName + addon);
                    attempts++;
                }

                java.util.List<String> lines = Arrays.asList(result.split(System.getProperty("line.separator")));
                Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
                showInfoAlert("Wynik został zapisany do " + fileName + addon + ".");
            }
        }
    }

    private void showInfoAlert(String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Info!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public static int getTab() {
        return tabs.getSelectedIndex();
    }

    public JPanel getPanel() {
        return panel;
    }
}
