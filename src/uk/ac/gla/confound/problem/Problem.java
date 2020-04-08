package uk.ac.gla.confound.problem;

import uk.ac.gla.confound.constraint.ConstraintMatrix;

import java.util.*;

public abstract class Problem {
    public boolean consistent;
    public int numVariables;

    public Variable[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null

    public ConstraintMatrix constraints; // Constraint for each variable pair

    public List<int[]> solutions;

    public Problem(Domain dom, int numVariables){
        this.numVariables = numVariables;
        variables = new Variable[numVariables+1];
        for (int i = 0; i < this.numVariables+1; i++) {
            Variable.incrementAmount();
            variables[i] = new Variable(dom);
        }

        constraints = new ConstraintMatrix(numVariables);
        solutions = new ArrayList<>();
    }

    public Problem(int numVariables) {
        // Initialise the domain
        Domain domain = new Domain(numVariables);

        this.numVariables = numVariables;
        variables = new Variable[this.numVariables + 1];

        for (int i = 0; i < this.numVariables + 1; i++)
            variables[i] = new Variable(domain);


        // Initialise a constraint mapping such that the pair of indices of the concerned variables map to the
        // binary constraint they both share
        constraints = new ConstraintMatrix(numVariables);

        solutions = new ArrayList<>();
    }


    public void print(int i) {
        System.out.println("----------------------------");
        for (int j = 0; j < solutions.get(i).length; j++)
            System.out.println(variables[j+1].name+"="+solutions.get(i)[j]);
    }

    public boolean check(int i, int j)
    {
        return constraints.checkFor(i, j);
    }

    public void printAll()
    {
        for (int i = 0; i < solutions.size(); i++)
            print(i);
    }
}
