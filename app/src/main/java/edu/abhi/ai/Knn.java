package edu.abhi.ai;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class Knn {

    private final Logger logger = LogManager.getLogger(Knn.class);
    private final int k;
    private ArrayList<ArrayList<BigDecimal>> features;
    private ArrayList<BigDecimal> labels;
    private int size = 0;
    private int noOfFeatures = 0;

    public Knn(int k) {
        this.k = k;
    }

    public void fit(ArrayList<ArrayList<BigDecimal>> features, ArrayList<BigDecimal> labels) {
        this.features = features;
        this.labels = labels;
        this.size = labels.size();
        this.noOfFeatures = features.size();
        assert features.get(0).size() == size;
        logger.debug("Fitting to " + size + " data points and " + noOfFeatures +" number of features");        
    }

    public BigDecimal predict(ArrayList<BigDecimal> data, int ignore) {
        logger.debug(new ParameterizedMessage("Predict for {} ", data));
        PriorityQueue<Integer> pq = new PriorityQueue<>(new KnnComparator(data, features));

        for (int i = 0; i < size; i++) {
            if (i == ignore) continue;
            pq.add(i);
        }

        Map<BigDecimal, Integer> map = new HashMap<>();
        for (int i = 0; i < Math.min(k, pq.size()); i++) {
            int rId = pq.poll();
            BigDecimal label = labels.get(rId);
            logger.debug(new ParameterizedMessage("K  : {} - Record {} - class {}", i+1, rId, label));
            
            if (!map.containsKey(label)) 
                map.put(label, 1);
            else 
                map.put(label, map.get(label) + 1);
        }
        
        int maxCount = 0;
        BigDecimal maxLabel = null;
        for (Entry<BigDecimal, Integer> entry : map.entrySet()) {
            logger.debug(new ParameterizedMessage("Count  : {} - {}", entry.getKey(), entry.getValue()));
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxLabel = entry.getKey();
            }
            logger.debug(new ParameterizedMessage("Max  : {} - {}", maxLabel, maxCount));
        }
        
        return maxLabel;
    }

    public Double leaveOneOutValidation() {
        int count = 0;
        // Initialize feature 
        ArrayList<BigDecimal> data = new ArrayList<>();
        for (int i = 0; i < noOfFeatures; i++) data.add(Constants.ZERO);

        // For every data point
        for (int i = 0; i < size; i++) {
            for (int fid = 0; fid < noOfFeatures; fid++) data.set(fid, features.get(fid).get(i));
            BigDecimal prediction = predict(data, i);
            logger.debug(new ParameterizedMessage("Prediction for {} with class {} is {}", data, labels.get(i) , prediction));
            if (prediction.equals(labels.get(i)))
                count++;
        }
        return ((double) count)/size;
    }
    
}
