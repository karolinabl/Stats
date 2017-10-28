package com.stats;

import java.util.ArrayList;
import java.util.List;

public class Results {

    static boolean batch = false;
    static List<String> results = new ArrayList<>();
    static List<Weka> datas = new ArrayList<>();

    public static void addData(Weka data) {
        datas.add(data);
    }

    public static Weka getData(int index) {
        return datas.get(index);
    }

    public static void setResult(String string, int index) {
        results.set(index, string);
    }

    public static String getResult(int index) {
        return results.get(index);
    }

    public static void addResult() {
        results.add("");
    }

    public static void removeResult(int index) {
        results.remove(index);
    }

    public static void setBatch(boolean bool) {
        batch = bool;
    }

    public static boolean getBatch() {
        return batch;
    }

}
