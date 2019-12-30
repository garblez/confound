package uk.ac.gla.confound.marktwo;

import java.util.ArrayList;
import java.util.List;

// A second attempt to formulate the general problem
public class Problem {

    public boolean consistent;
    public int numVariables;

    public Variable[] variables;
    public ArrayList<Constraint> constraints;

    public List<Variable[]> solutions;


    public Problem(int numVars) {
        numVariables = numVars;
        variables = new Variable[numVars+1]; // Account for using variables[0] as a dummy variable to check for failure
        constraints = new ArrayList<>();
        solutions = new ArrayList<>();
    }

    public boolean check(int i, int h) {
        // We have not defined any constraints in this abstract class: assume there are no constraints between any variables
        // and override with a problem subclass
        return true;
    }
}
