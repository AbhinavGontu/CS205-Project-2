package edu.abhi.ai.selection;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;

import edu.abhi.ai.Input;
import edu.abhi.ai.Knn;

public class ForwardPropagation extends Selection{

    private final Logger logger = LogManager.getLogger(ForwardPropagation.class);

    private final Knn knn; 

    public ForwardPropagation(String name, Knn knn) {
        super(name);
        this.knn = knn;
        logger.debug("Attached Knn to forward propagation");
    }

    public Set<Integer> select(Input input) {
        
        logger.info(new ParameterizedMessage("Forward propagate over {} number of features", input.noOfFeatures()));
        Set<Integer> allFeatureIds = new HashSet<>();
        for (int fid = 0; fid < input.noOfFeatures(); fid++) allFeatureIds.add(fid);
       
        // Compute accuracy when all features are considered
        knn.fit(input.getFeatures(), input.getLabels());
        Double allAccuracy = knn.leaveOneOutValidation();
        logger.info(allMessage(allAccuracy));

        Set<Integer> inclusions = new HashSet<>();
        Double maxAccuracy = 0.0;
        Double accuracy;
        for (int level = 1; level < input.noOfFeatures(); level++) {

            // for each level of search tree
            logger.info(new ParameterizedMessage("On the {}th level of search tree", level));

            int fid2Add = -1;
            // for each feature id
            for (int fid = 0; fid < input.noOfFeatures(); fid++) {

                // if already included, ignore
                if (inclusions.contains(fid)) continue;

                logger.info(new ParameterizedMessage("- Considering adding {}th feature", fid+1));
                
                // include feature id
                inclusions.add(fid);

                // Fit knn after selecting only included features
                knn.fit(input.getFeatures(inclusions, false), input.getLabels());

                // get kfold - cross validation accuracy
                accuracy = knn.leaveOneOutValidation();
                logger.info(genMessage(inclusions, accuracy));

                // check level max
                if (maxAccuracy < accuracy) {
                    maxAccuracy = accuracy;
                    fid2Add = fid;
                }

                inclusions.remove(fid);
            }
            if (fid2Add == -1) {
                logger.info(new ParameterizedMessage("On level {}, No increase in accuracy, Stop search !!", level+1));
                break;
            } else {
                inclusions.add(fid2Add);
            }
            logger.info(new ParameterizedMessage("On level {}, Added {}th feature to currrent set ", level+1, fid2Add+1));
        }
    
        logger.info(maxMessage(inclusions, maxAccuracy));
        
        return inclusions;
    }
    
    
}
