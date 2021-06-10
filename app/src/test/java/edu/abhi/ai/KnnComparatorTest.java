package edu.abhi.ai;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.PriorityQueue;

import org.junit.Test;

public class KnnComparatorTest {

    @Test
    public void testCompare() {
        Input input = new Input(this.getClass().getClassLoader().getResource("testCircle.txt").getPath().toString());
        ArrayList<ArrayList<BigDecimal>> features = input.getFeatures();

        ArrayList<BigDecimal> feature = new ArrayList<>();

        for (int rId = 0; rId < input.noOfRecords(); rId++) {
            for (int fid = 0; fid < input.noOfFeatures(); fid++) feature.add(features.get(fid).get(rId));
            PriorityQueue<Integer> pq = new PriorityQueue<>(new KnnComparator(feature, features));

            for (int e = 0; e < input.noOfRecords(); e++) pq.add(e);

            int actual = pq.poll();
            int expected = rId;
            
            assertEquals(expected, actual);
            pq.clear();
            feature.clear();
        }
    }

    @Test
    public void testCompareIgnore() {
        Input input = new Input(this.getClass().getClassLoader().getResource("testCircle.txt").getPath().toString());
        ArrayList<ArrayList<BigDecimal>> features = input.getFeatures();

        ArrayList<BigDecimal> feature = new ArrayList<>();

        for (int fid = 0; fid < input.noOfFeatures(); fid++) feature.add(features.get(fid).get(15));
        PriorityQueue<Integer> pq = new PriorityQueue<>(new KnnComparator(feature, features));

        for (int e = 0; e < input.noOfRecords(); e++) 
            if (e != 15)
                pq.add(e);
        int actual = pq.poll();
        int expected = 2;
        
        assertEquals(expected, actual);
        pq.clear();
        feature.clear();
    }

}
