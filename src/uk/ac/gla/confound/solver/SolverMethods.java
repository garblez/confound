package uk.ac.gla.confound.solver;

/**
 * All solvers extending the base solver must implement the solver methods label(int i) and unlabel(int i)
 * These methods operate as advancing and retreating operations a solver undertakes in a search process:
 * Other solvers such as simple Backtracking must define the definitions of these operations. This is to allow
 * all extending solvers to operate with the same solve() back-end, allowing the same statistics to be gathered
 * in the same way and the general search algorithm to differ only in finer details.
 */
public interface SolverMethods {
    int label(int i);
    int unlabel(int i);
}
