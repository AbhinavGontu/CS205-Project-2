package edu.abhi.ai;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class KnnTest {

   private Input input;
   private Knn knn1;
   private Knn knn3;

    @Before
    public void setUp() {
        input = new Input(this.getClass().getClassLoader().getResource("testCircle.txt").getPath().toString());
        knn1 = new Knn(1);
        knn1.fit(input.getFeatures(), input.getLabels());

        knn3 = new Knn(3);
        knn3.fit(input.getFeatures(), input.getLabels());
    }

    @Test
    public void testPredict1() {

        ArrayList<BigDecimal> feature = new ArrayList<>();
        ArrayList<ArrayList<BigDecimal>> features = input.getFeatures();
        

        for (int rId = 0; rId < input.noOfRecords(); rId++) {
            for (int fid = 0; fid < input.noOfFeatures(); fid++) feature.add(features.get(fid).get(rId));
            BigDecimal actual = knn1.predict(feature, -1);
            BigDecimal expected = input.getLabels().get(rId);
            assertEquals(expected, actual);
            feature.clear();
        }

        ArrayList<BigDecimal> labels = new ArrayList<>();
        // 0: 1, 0
        labels.add(new BigDecimal("1.0000000e+00"));
        // 1: -1, 0
        labels.add(new BigDecimal("1.0000000e+00"));
        // 2: 0, 1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 3: 0, -1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 4: 0, 0
        labels.add(new BigDecimal("1.0000000e+00"));

        // 5: -1, 1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 6: -1, -1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 7: 1, -1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 8: 1, 1
        labels.add(new BigDecimal("1.0000000e+00"));
        
        // 9: -0.707, 0.707
        labels.add(new BigDecimal("2.0000000e+00"));
        // 10: -0.707, -0.707
        labels.add(new BigDecimal("2.0000000e+00"));
        // 11: 0.707, -0.707
        labels.add(new BigDecimal("2.0000000e+00"));
        // 12: 0.707, 0.707
        labels.add(new BigDecimal("2.0000000e+00"));

        // 13: 2, 0
        labels.add(new BigDecimal("1.0000000e+00"));
        // 14: -2, 0
        labels.add(new BigDecimal("1.0000000e+00"));
        // 15: 0, 2
        labels.add(new BigDecimal("1.0000000e+00"));
        // 16: 0, -2
        labels.add(new BigDecimal("1.0000000e+00"));

        // 17: 2 , 2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 18: 2, -2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 19: -2, -2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 20: -2, 2
        labels.add(new BigDecimal("2.0000000e+00"));

        for (int rId = 0; rId < 21; rId++) {
            for (int fid = 0; fid < input.noOfFeatures(); fid++) feature.add(features.get(fid).get(rId));
            BigDecimal actual = knn1.predict(feature, rId);
            BigDecimal expected = labels.get(rId);
            assertEquals(expected, actual);
            feature.clear();
        }

    }

    @Test 
    public void testPredict3() {
        
        ArrayList<BigDecimal> feature = new ArrayList<>();
        ArrayList<ArrayList<BigDecimal>> features = input.getFeatures();
        
        ArrayList<BigDecimal> labels = new ArrayList<>();
        // 0: 1, 0
        labels.add(new BigDecimal("1.0000000e+00"));
        // 1: -1, 0
        labels.add(new BigDecimal("1.0000000e+00"));
        // 2: 0, 1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 3: 0, -1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 4: 0, 0
        labels.add(new BigDecimal("1.0000000e+00"));

        // 5: -1, 1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 6: -1, -1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 7: 1, -1
        labels.add(new BigDecimal("1.0000000e+00"));
        // 8: 1, 1
        labels.add(new BigDecimal("1.0000000e+00"));
        
        // 9: -0.707, 0.707
        labels.add(new BigDecimal("1.0000000e+00"));
        // 10: -0.707, -0.707
        labels.add(new BigDecimal("1.0000000e+00"));
        // 11: 0.707, -0.707
        labels.add(new BigDecimal("1.0000000e+00"));
        // 12: 0.707, 0.707
        labels.add(new BigDecimal("1.0000000e+00"));

        // 13: 2, 0
        labels.add(new BigDecimal("2.0000000e+00"));
        // 14: -2, 0
        labels.add(new BigDecimal("2.0000000e+00"));
        // 15: 0, 2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 16: 0, -2
        labels.add(new BigDecimal("2.0000000e+00"));

        // 17: 2 , 2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 18: 2, -2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 19: -2, -2
        labels.add(new BigDecimal("2.0000000e+00"));
        // 20: -2, 2
        labels.add(new BigDecimal("2.0000000e+00"));

        for (int rId = 0; rId < 21; rId++) {
            for (int fid = 0; fid < input.noOfFeatures(); fid++) feature.add(features.get(fid).get(rId));
            BigDecimal actual = knn3.predict(feature, rId);
            BigDecimal expected = labels.get(rId);
            assertEquals(expected, actual);
            feature.clear();
        }
    }

    @Test 
    public void testLeaveOneOutValidation3() {
        Double actual = knn3.leaveOneOutValidation();
        Double expected = ((double) 17)/21;
        assertEquals(expected , actual);
    }

    @Test
    public void testLeaveOneOutValidation1() {
        Double actual = knn1.leaveOneOutValidation();
        Double expected = ((double) 9)/21;
        assertEquals(expected , actual);

    }
    
}
