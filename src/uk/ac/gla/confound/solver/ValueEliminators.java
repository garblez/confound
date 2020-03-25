package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Problem;

import java.util.*;


// Allows the variable domain restrictions to be recorded alongside their reasons
public class ValueEliminators {
    public HashMap<Integer, HashSet<Integer>>[] elimination;
    Problem p;

    public ValueEliminators(Problem p) {
        this.p = p;
        elimination = new HashMap[p.numVariables+1];
        for (int i = 0; i < p.numVariables+1; i++)
            elimination[i] = new HashMap<Integer, HashSet<Integer>>();
    }

    public Set<Integer> getEliminatedValues(int index) {
        return elimination[index].keySet();
    }

    // For variable index, add the indices to all variables in which a constraint they share is violated.
    // This allows us to preclude the current value of the current variable by adding it to the eliminator.
    public void updateEliminations(int index) {
        HashSet causes = elimination[index].getOrDefault(p.variables[index].value, new HashSet<>());
        for (int i = 0; i < p.numVariables+1; i++)
            if (!p.constraints[index][i].check())
                causes.add(i);

        elimination[index].put(p.variables[index].value, causes);
    }

    // Remove all values from the current domain that are disallowed by the elimination explanations
    public void updateCurrentDomain(int index) {
        p.variables[index].currentDomain.removeAll(elimination[index].keySet());
    }

    // Disallow value for variable[index] by recomputing the elimination explanation for that value up to that point in
    // the search progression. This must be done with the current value of variable[index] as value
    public void disallowValue(int index, int value) {
        HashSet causes = new HashSet<Integer>(); // Recompute variable indices where constraints don't hold with index's variable
        for (int i = 0; i < index; i++)
            if (!p.constraints[index][i].check())
                causes.add(i);

        elimination[index].put(value, causes);
    }

    // Remove any eliminating explanations for variables after index that have values precluded because of variable index
    public void removeExplanationsAfterChange(int index) {
        ArrayList<Integer> removals = new ArrayList<>();
        for (int k = index + 1; k < p.numVariables+1;  k++) {
            for (Integer value: elimination[k].keySet()){
                if (elimination[k].get(value).contains(index))
                    removals.add(value);
            }
            for (Integer value: removals)
                elimination[k].remove(value);
            removals.clear();
        }
    }

    public void addElimination(int index, int value, int responsibleVariable) {
        HashSet explanation = elimination[index].getOrDefault(value, new HashSet<>());
        explanation.add(responsibleVariable);
        elimination[index].put(value, explanation);
    }

    public void addElimination(int index, int value, HashSet<Integer> responsibleVariables) {
        HashSet explanation = elimination[index].getOrDefault(value, new HashSet<>());
        explanation.addAll(responsibleVariables);
        elimination[index].put(value, explanation);
    }

    public int getMostRecentViolator(int index) {
        HashSet<Integer> violators = new HashSet<Integer>();
        elimination[index].values().forEach(violators::addAll);

        return Collections.max(violators);
    }

    public HashSet<Integer> getAllViolators(int index) {
        HashSet<Integer> violators = new HashSet<Integer>();
        elimination[index].values().forEach(violators::addAll);
        return violators;
    }
}
