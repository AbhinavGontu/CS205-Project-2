package edu.abhi.ai;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

public class MetricsTest {

    // @Test
    public void testAccuracy() {
        ArrayList<BigDecimal> actual = new ArrayList<>();
        actual.add(new BigDecimal(0));
        actual.add(new BigDecimal(1));
        actual.add(new BigDecimal(2));
        actual.add(new BigDecimal(3));
        actual.add(new BigDecimal(4));    
        Double result = Metrics.accuracy(actual, actual);
        Double expected = 1.0;
        assertEquals(result, expected);

        ArrayList<BigDecimal> prediction = new ArrayList<>();
        prediction.add(new BigDecimal(1));
        prediction.add(new BigDecimal(1));
        prediction.add(new BigDecimal(2));
        prediction.add(new BigDecimal(2));
        prediction.add(new BigDecimal(3));
        result = Metrics.accuracy(prediction, actual);
        expected = 0.4;
        assertEquals(result, expected);
        
    }
    
}
