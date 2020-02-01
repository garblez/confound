package uk.ac.gla.confound.solver;

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
