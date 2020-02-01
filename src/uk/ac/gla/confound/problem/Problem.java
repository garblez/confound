package uk.ac.gla.confound.problem;


import uk.ac.gla.confound.constraint.AlwaysTrueConstraint;
import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.constraint.IndexPair;

import java.util.*;

public abstract class Problem {
    public boolean consistent;
    public int numVariables;

    public Variable[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    public Domain domain; // Assume all integer variables have the same domain

    public ArrayList<Integer>[] current;


    public Map<IndexPair, Constraint> constraints; // Constraint for each variable pair

    public List<int[]> solutions;

    public Problem(int numVariables) {
        // Initialise the domain
        domain = new Domain(numVariables);

        current = new ArrayList[numVariables+1];
        current[0] = new ArrayList<Integer>();
        for (int i = 1; i < numVariables+1; i++)
            current[i] = domain.copy();

        this.numVariables = numVariables;
        variables = new Variable[this.numVariables + 1];

        for (int i = 0; i < this.numVariables+1; i++)
            variables[i] = new Variable(domain);


        // Initialise a constraint mapping such that the pair of indices of the concerned variables map to the
        // binary constraint they both share
        constraints = new HashMap<>();

        solutions = new ArrayList<>();
    }


    public void print(int x)
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVariables; i++) {
            for (int j = 0; j < numVariables; j++) {
                if (j==solutions.get(x)[i])
                    s.append("*");
                else
                    s.append("  ");
            }
            s.append("\n");
        }
        for (int i = 0; i < numVariables; i++)
            s.append("==");
        s.append("\n");
        System.out.print(s.toString());
    }

    public boolean check(int i, int j)
    {
        return constraints.getOrDefault(new IndexPair(i, j), new AlwaysTrueConstraint()).check();
    }

    public void printAll()
    {
        for (int i = 0; i < solutions.size(); i++)
            print(i);
    }
}
