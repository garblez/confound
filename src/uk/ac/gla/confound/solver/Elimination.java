package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Variable;

import java.util.Map;
import java.util.Set;

public class Elimination {
    Map<Integer, Set<Variable>> explanation;

    public Set<Integer> preclusives() {
        return explanation.keySet();
    }
}
