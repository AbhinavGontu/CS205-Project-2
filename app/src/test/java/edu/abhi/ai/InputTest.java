package edu.abhi.ai;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class InputTest {

    // @Test 
    public void testLabels() {
        Input input = new Input(this.getClass().getClassLoader().getResource("test.txt").getPath().toString());
        assertEquals(input.noOfFeatures(), 3);
        assertEquals(input.noOfRecords(), 3);
        ArrayList<BigDecimal> expected = new ArrayList<BigDecimal>();
        expected.add(new BigDecimal("1.0000000e+00"));
        expected.add(new BigDecimal("2.0000000e+00"));
        expected.add(new BigDecimal("2.0000000e+00"));
        ArrayList<BigDecimal> actual = input.getLabels();
        for (int i = 0; i < input.noOfRecords(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }

        
    }

    // @Test
    public void testFeatures() {
        Input input = new Input(this.getClass().getClassLoader().getResource("test.txt").getPath().toString());
        assertEquals(input.noOfFeatures(), 3);
        assertEquals(input.noOfRecords(), 3);

        ArrayList<BigDecimal> feature1 = new ArrayList<BigDecimal>();
        feature1.add(new BigDecimal("3.0000000e-01"));
        feature1.add(new BigDecimal("5.0000000e-01"));
        feature1.add(new BigDecimal("9.0000000e-01"));

        ArrayList<BigDecimal> feature2 = new ArrayList<BigDecimal>();
        feature2.add(new BigDecimal("1.0000000e+00"));
        feature2.add(new BigDecimal("-3.0000000e-01"));
        feature2.add(new BigDecimal("-4.0000000e-01"));

        ArrayList<BigDecimal> feature3 = new ArrayList<BigDecimal>();
        feature3.add(new BigDecimal("-3.0000000e-01"));
        feature3.add(new BigDecimal("8.0000000e-01"));
        feature3.add(new BigDecimal("4.0000000e-01"));

        ArrayList<ArrayList<BigDecimal>> features = new ArrayList<>();
        features.add(feature1);
        features.add(feature2);
        features.add(feature3);
        ArrayList<ArrayList<BigDecimal>> actual = input.getFeatures();
        for (int i = 0; i < input.noOfFeatures(); i++) {
            for (int j =- 0; j < input.noOfRecords(); j++) {
                assertEquals(features.get(i).get(j), actual.get(i).get(j) );
            }
        }

        features.clear();
        Set<Integer> set = new HashSet<>();
        set.add(0);
        actual = input.getFeatures(set, true);
        features.add(feature2);
        features.add(feature3);
        for (int i = 0; i < input.noOfFeatures()-1; i++) {
            for (int j =- 0; j < input.noOfRecords(); j++) {
                assertEquals(features.get(i).get(j), actual.get(i).get(j));
            }
        }

        features.clear();
        set.clear();
        set.add(0);
        actual = input.getFeatures(set, false);
        features.add(feature1);
        for (int i = 0; i < 1; i++) {
            for (int j =- 0; j < input.noOfRecords(); j++) {
                assertEquals(features.get(i).get(j), actual.get(i).get(j));
            }
        }

    }
    
}
