package uk.ac.gla.confound;


import java.util.*;

public abstract class Problem {
    boolean consistent;
    int numVariables;

    Variable[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    Domain domain; // Assume all integer variables have the same domain

    ArrayList<Integer>[] current; // TODO: fix this in future by replacing it with Variable.currentDomain

    BitSet[] constraints;  // Constraint bitset for each variable

    List<int[]> solutions;

    public Problem(int numVariables) {
        // Initialise the domain
        domain = new Domain(numVariables);

        current = new ArrayList[numVariables+1];
        current[0] = new ArrayList<>();
        for (int i = 1; i < numVariables+1; i++)
            current[i] = domain.copy();

        this.numVariables = numVariables;
        variables = new Variable[this.numVariables + 1];
        for (int i = 0; i < this.numVariables + 1; i++)
            variables[i] = new Variable(domain);


        // Initialise a has-constraint table
        constraints = new BitSet[this.numVariables];
        for (int i = 0; i < this.numVariables; i++)
            constraints[i] = new BitSet(this.numVariables);

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
        return true;
    }

    public void printAll()
    {
        for (int i = 0; i < solutions.size(); i++)
            print(i);
    }
}
