package edu.abhi.ai;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Metrics {

    public static Double accuracy(ArrayList<BigDecimal> prediction , ArrayList<BigDecimal> actual) {
        assert prediction.size() == actual.size();
        int total = prediction.size();
        int count = 0;
        for (int i = 0; i < total; i++) {
            if (prediction.get(i).equals(actual.get(i)) )
                count++;
        }
        System.out.println(count);
        return count*1.0/total;
    }
    
}
