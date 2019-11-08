package uk.ac.gla.confound;

public interface Solver {
    public void search();
    int label(int i);
    int unlabel(int i);
    boolean check(int i, int h);
    public String solution();
}
