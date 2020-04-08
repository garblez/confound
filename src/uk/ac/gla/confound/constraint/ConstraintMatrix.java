package uk.ac.gla.confound.constraint;

public class ConstraintMatrix {
    private ConstraintList[][] constraints;

    public ConstraintMatrix(int numVar) {
        constraints = new ConstraintList[numVar+1][numVar+1];
        for (int i = 0; i < constraints.length; i++)
            for (int j = 0; j < constraints.length; j++)
                constraints[i][j] = new ConstraintList();
    }

    public void add(int i, int j, Constraint c) {
        constraints[i][j].add(c);
    }

    public void addBoth(int i, int j, Constraint c) {
        constraints[i][j].add(c);
        constraints[j][i].add(c);
    }


    public ConstraintList getFor(int i, int j) {
        return constraints[i][j];
    }

    public boolean checkFor(int i, int j) {
        return constraints[i][j].check();
    }
}
