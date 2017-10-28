package com.stats;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

	    if (args.length == 0) {
            JFrame frame = new JFrame();
            frame.setContentPane(new TabbedForm().getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
        else {
            ArrayList<String> arguments = toArrayList(args);
            if (arguments.contains("-o")) {
                String path = arguments.get(findIndexOfArgument("-o", arguments) + 1);
                Weka data = new Weka(path);
                String result = "";
                if (arguments.contains("-a") && !arguments.contains("-A")) {
                    int idAttribute = Integer.parseInt(arguments.get(findIndexOfArgument("-a", arguments) + 1));
                    result = data.getStatistics(idAttribute);
                }
                if (arguments.contains("-A") && !arguments.contains("-a")) {
                    for (int i = 0; i < data.getNumAttributes(); i++) {
                        result += data.getStatistics(i);
                    }
                }
                if (!arguments.contains("-a") && !arguments.contains("-A")) {
                    System.out.println("Please define next time, which argument should be taken into account during processing.");
                    System.out.println("-a <arg> defines, which argument will be taken into account during processing.");
                    System.out.println("-A makes, that every attribute is taken into account.");
                    System.exit(0);
                }
                if (arguments.contains("-s")) {
                    System.out.println(result);
                }
                if (arguments.contains("-f")) {
                    String savePath = arguments.get(findIndexOfArgument("-f", arguments) + 1);
                    File file = new File(savePath);
                    if (!file.isDirectory()) {
                        if (!file.exists()) {
                            Files.createFile(file.toPath());
                        }
                        List<String> lines = Arrays.asList(result.split(System.getProperty("line.separator")));
                        Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
                        System.out.println("Result was saved to the given file.");
                        System.exit(0);
                    }
                    else {
                        System.out.println("Error! Given saving path is a directory!");
                        System.out.println("Given path should end on a text file(.txt)");
                        System.exit(0);
                    }
                }
            }
        }
    }

    public static int findIndexOfArgument(String arg, ArrayList<String> args) {
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).equals(arg)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList toArrayList(Object[] tab) {
        ArrayList<Object> array = new ArrayList<>();
        for (int i = 0; i < tab.length; i++) {
            array.add(tab[i]);
        }
        return array;
    }
}
