package edu.abhi.ai;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Input {

    private final Logger logger = LogManager.getLogger(Input.class);

    private final ArrayList<ArrayList<BigDecimal>> features = new ArrayList<>();
    private final ArrayList<BigDecimal> labels = new ArrayList<>();
    private final int noOfRecords;
    private final int noOfFeatures;

    public Input(String path) {
        Scanner sc = null;
        int f = 0, n = 0;
        try {
            logger.info("Read data from " + path);
            
            sc = new Scanner(new FileInputStream(path));
            while(sc.hasNextLine()) {
                String[] nums = sc.nextLine().trim().split("\\s+");

                // first element is the class label
                labels.add(new BigDecimal(nums[0]));
    
                // Create features
                for (int i = 0; i < nums.length-1; i++) {
                    if (features.size() == i) features.add(new ArrayList<BigDecimal>());
                    features.get(i).add(new BigDecimal(nums[i+1]));
                }
            }

            f = features.size();
            n = labels.size();

            assert labels.size() == features.get(0).size();
            logger.info("Numer of records read : " + n);
            logger.info("Number of features per record : " + f);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.close();
            noOfFeatures = f;
            noOfRecords = n;
        }
    }

    public ArrayList<ArrayList<BigDecimal>> getFeatures(Set<Integer> set, boolean exclude) {
        ArrayList<ArrayList<BigDecimal>> subSet = new ArrayList<>();
        for (int i = 0; i < noOfFeatures; i++) {
            if (set.contains(i) ^ exclude) subSet.add(features.get(i));
        }
        return subSet;
    }

    public ArrayList<ArrayList<BigDecimal>> getFeatures() {
        return features;
    }

    public ArrayList<BigDecimal> getLabels() {
        return labels;
    }
    
    public int noOfFeatures() {
        return noOfFeatures;
    }

    public int noOfRecords() {
        return noOfRecords;
    }

}