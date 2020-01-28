package uk.ac.gla.confound;


import java.util.*;

public abstract class Problem {
    public boolean consistent;
    public int numVariables;

    public Variable[] variables; // For each variables[i] := queen row |-> col, of size numVar+1 as variables[0] is always null
    public Domain domain; // Assume all integer variables have the same domain

    public ArrayList[] current; // TODO: fix this in future by replacing it with Variable.currentDomain

    public BitSet[] constraints;  // Constraint for each variable pair

    public List<int[]> solutions;

    public Problem(int numVariables) {
        // Initialise the domain
        domain = new Domain(numVariables);

        current = new ArrayList[numVariables+1];
        current[0] = new ArrayList();
        for (int i = 1; i < numVariables+1; i++)
            current[i] = domain.copy();

        this.numVariables = numVariables;
        variables = new Variable[this.numVariables + 1];

        for (int i = 0; i < this.numVariables+1; i++)
            variables[i] = new Variable(domain);


        // Initialise a constraint table: by default, no constraint exists and hence is a null value
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
