package com.stats;

import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

public class Weka {

    Instances data;

    public Weka(String path) throws IOException {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(path));
        data = loader.getDataSet();
    }

    public Instances getData() {
        return data;
    }

    public int getNumAttributes() {
        return data.numAttributes();
    }

    public Attribute getAttribute(int index) {
        return data.attribute(index);
    }

    public int findIndexAttribute(String attribute) {
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            if (attribute.equals(data.attribute(i).toString())) {
                return i;
            }
        }
        return -1;
    }

    public boolean attributeExist(String attribute) {
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            if (attribute.equals(data.attribute(i).toString())) {
                return true;
            }
        }
        return false;
    }

    public String getStatistics(int index) {
        StringBuilder sb = new StringBuilder();

        if (data.attribute(index).isNominal()) {
            sb.append(computeNominalStats(index));
        }
        if (data.attribute(index).isNumeric()) {
            sb.append(computeNumericStats(index));
        }

        return sb.toString();
    }

    private String computeNumericStats(int index) {
        StringBuilder sb = new StringBuilder();

        AttributeStats attributeStats = data.attributeStats(index);
        sb.append(data.attribute(index).toString() + System.getProperty("line.separator"));
        sb.append("max: " + attributeStats.numericStats.max + System.getProperty("line.separator"));
        sb.append("min: " + attributeStats.numericStats.min + System.getProperty("line.separator"));
        sb.append("mean: " + attributeStats.numericStats.mean + System.getProperty("line.separator"));
        sb.append("std Dev: " + attributeStats.numericStats.stdDev + System.getProperty("line.separator"));
        sb.append("count: " + attributeStats.numericStats.count + System.getProperty("line.separator"));

        return sb.toString();
    }

    private String computeNominalStats(int index) {
        StringBuilder sb = new StringBuilder();

        AttributeStats attributeStats = data.attributeStats(index);
        sb.append(data.attribute(index).toString() + System.getProperty("line.separator"));
        sb.append("length(c): " + attributeStats.nominalCounts.length + System.getProperty("line.separator"));
        sb.append("lenght(w): " + attributeStats.nominalWeights.length + System.getProperty("line.separator"));

        return sb.toString();
    }
}
