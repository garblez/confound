package uk.ac.gla.confound;

import java.util.ArrayList;

public class Variable {
    public int value = 0;
    public ArrayList<Integer> currentDomain = new ArrayList<>();

    public Variable(Domain currentDomain) {
        for (Integer value: currentDomain.values())
            this.currentDomain.add(value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Variable && value == ((Variable) o).value && currentDomain.equals(((Variable) o).currentDomain);
    }
}
