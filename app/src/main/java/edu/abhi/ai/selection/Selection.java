package edu.abhi.ai.selection;

import java.util.Set;

import edu.abhi.ai.Input;

public abstract class Selection {
    
    public abstract Set<Integer> select(Input input);

    private final String name;

    public Selection(String name) {
        this.name = name;
    }

    protected String allMessage(Double accuracy) {
        return "- Considering all features, Accuracy : "+ accuracy*100;
    }

    protected String genMessage(Set<Integer> set , Double accuracy) {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Using featureset ");
        sb.append(set);
        sb.append(" , Accuracy : ");
        sb.append(accuracy*100);
        sb.append("%");
        return sb.toString();
    }

    protected String maxMessage(Set<Integer> set , Double accuracy) {
        StringBuilder sb = new StringBuilder();
        sb.append("*** | " + name + " | Using featureset  ");
        sb.append(set);
        sb.append(" , Max Accuracy : ");
        sb.append(accuracy*100);
        sb.append("% ***\n");
        int len = sb.length();
        sb.insert(0, "\n");
        for (int i = 0; i < len; i++) sb.insert(0, '*');
        for (int i = 0; i < len; i++) sb.append('*');

        return sb.toString();
    }

}
