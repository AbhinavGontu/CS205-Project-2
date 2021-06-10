package edu.abhi.ai.selection;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

import edu.abhi.ai.Input;
import edu.abhi.ai.Knn;

public class BackwardElimination extends Selection{

    private final Logger logger = LogManager.getLogger(BackwardElimination.class);

    private final Knn knn;

    public BackwardElimination(String name, Knn knn) {
        super(name);
        this.knn = knn;        
        logger.debug("Attached Knn with backward elimination");
    }


    public Set<Integer> select(Input input) {
        
        logger.info(new ParameterizedMessage("Backward eliminate over {} number of features", input.noOfFeatures()));
        Set<Integer> allFeatureIds = new HashSet<>();
        for (int fid = 0; fid < input.noOfFeatures(); fid++) allFeatureIds.add(fid);

        // Compute accuracy when all features are considered
        knn.fit(input.getFeatures(), input.getLabels());
        Double allAccuracy = knn.leaveOneOutValidation();
        logger.info(allMessage(allAccuracy));
        
        // exclusions
        Set<Integer> exclusions = new HashSet<>();

        Set<Integer> featureSet = new HashSet<>();
        Double maxAccuracy = 0.0;
        Double accuracy;
        for (int level = 1; level < input.noOfFeatures(); level++) {

            // for each level of search tree
            logger.info(new ParameterizedMessage("On the {}th level of search tree", level));
            int fid2Drop = -1;

            // for each feature id
            for (int fid = 0; fid < input.noOfFeatures(); fid++) {

                // if already excluded , ignore
                if (exclusions.contains(fid)) continue;

                logger.info(new ParameterizedMessage("- Considering dropping {}th feature", fid+1));

                // exclude feature id
                exclusions.add(fid);

                // Fit knn after selecting only non-excluded features
                knn.fit(input.getFeatures(exclusions, true), input.getLabels());

                // get kfold - cross validation accuracy
                accuracy = knn.leaveOneOutValidation();
                logger.info(genMessage(exclude(allFeatureIds, exclusions), accuracy));

                // check level max
                if (maxAccuracy < accuracy) {
                    maxAccuracy = accuracy;
                    fid2Drop = fid;
                }

                // include feature id
                exclusions.remove(fid);
            }
            if (fid2Drop == -1) {
                logger.info(new ParameterizedMessage("On level {}, No increase in accuracy, Stop search !!", level));
                break;
            } else {
                exclusions.add(fid2Drop);
            }
            logger.info(new ParameterizedMessage("On level {}, Dropped {}th feature from currrent set ", level, fid2Drop+1));
        }
        
        featureSet.addAll(exclude(allFeatureIds, exclusions));
        logger.info(maxMessage(featureSet, maxAccuracy));
        
        return featureSet;
    }

    private Set<Integer> exclude(Set<Integer> allFeatureIds,Set<Integer> exclusions) {
        Set<Integer> set = new HashSet<>(allFeatureIds);
        set.removeIf(s -> exclusions.contains(s));
        return set;
    }
    
}
