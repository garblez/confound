package uk.ac.gla.confound.problem;

import java.util.ArrayList;

public class Variable {
    public int value;
    public ArrayList<Integer> currentDomain;

    public Variable(Domain currentDomain) {
        this.currentDomain = new ArrayList<>();
        this.value = 0;
        for (Integer value: currentDomain.values())
            this.currentDomain.add(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Variable && value == ((Variable) o).value && currentDomain.equals(((Variable) o).currentDomain);
    }

}
