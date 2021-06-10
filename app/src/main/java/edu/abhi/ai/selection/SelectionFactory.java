package edu.abhi.ai.selection;

import edu.abhi.ai.Knn;

public class SelectionFactory {

    public Selection getSelection(int type, Knn knn) {
        Selection selection = null;
        switch (type) {
            case 1:
                selection = new ForwardPropagation("ForwardPropagation", knn);
                break;
        
            case 2:
                selection = new BackwardElimination("BackwardElimination", knn);
                break;

            default:
                selection = new ForwardPropagation("ForwardPropagation", knn);
                break;
        }
        return selection;
    }
    
}
