package uk.ac.gla.confound.problem;


import uk.ac.gla.confound.constraint.Constraint;

import java.util.*;

public abstract class Problem {
    public boolean consistent;
    public int numVariables;

    public Variable[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    //public Domain domain; // Assume all integer variables have the same domain

    public Constraint[][] constraints; // Constraint for each variable pair

    public List<int[]> solutions;

    public Problem(Domain dom, int numVariables){
        //domain = dom;
        this.numVariables = numVariables;
        variables = new Variable[numVariables+1];
        for (int i = 0; i < this.numVariables+1; i++)
            variables[i] = new Variable(dom, i);

        constraints = new Constraint[numVariables+1][numVariables+1];
        solutions = new ArrayList<>();
    }

    public Problem(int numVariables) {
        // Initialise the domain
        Domain domain = new Domain(numVariables);

        this.numVariables = numVariables;
        variables = new Variable[this.numVariables + 1];

        for (int i = 0; i < this.numVariables + 1; i++)
            variables[i] = new Variable(domain, i);


        // Initialise a constraint mapping such that the pair of indices of the concerned variables map to the
        // binary constraint they both share
        constraints = new Constraint[numVariables+1][numVariables+1];

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
        if (constraints[i][j] == null) {
            return true;
        }
        return constraints[i][j].check();
    }

    public void printAll()
    {
        for (int i = 0; i < solutions.size(); i++)
            print(i);
    }
}
