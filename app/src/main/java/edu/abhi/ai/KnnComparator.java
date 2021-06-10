package edu.abhi.ai;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

public class KnnComparator implements Comparator<Integer>{

    private final Logger logger = LogManager.getLogger(KnnComparator.class);

    private final ArrayList<BigDecimal> reference;
    private final ArrayList<ArrayList<BigDecimal>> features;

    public KnnComparator(ArrayList<BigDecimal> f, ArrayList<ArrayList<BigDecimal>> features) {
        this.reference = f;
        logger.debug("Reference " + reference);
        this.features = features;
    }

    @Override
    public int compare(Integer i0, Integer i1) {
        Double d1 = 0.0, d2 = 0.0;
        for (int i = 0; i < reference.size(); i++) {
            d1 += (features.get(i).get(i0).subtract(reference.get(i)).pow(2).doubleValue());
            d2 += (features.get(i).get(i1).subtract(reference.get(i)).pow(2).doubleValue());
        }

        logger.trace(new ParameterizedMessage("Distance of {} from reference : {}", i0, d1));
        logger.trace(new ParameterizedMessage("Distance of {} from reference : {}", i1, d2));
        logger.trace(new ParameterizedMessage("Compare {} - {} : {}", i0, i1, Double.compare(d1, d2)));
        return Double.compare(d1, d2);
    }
    
}