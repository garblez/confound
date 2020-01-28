package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EliminationSet {
    Elimination[] explanation;

    public EliminationSet(int numVariables) {
        explanation = new Elimination[numVariables+1];
        for (int i = 0; i < numVariables; i++) {
            explanation[i + 1] = new Elimination();
        }
    }
/*
    public void addExplanation(int which, Elimination explanation) {
        explanation[which]
    }

    public boolean isPrecluded(int index, int value) {
        return explanation()
    }*/

}
