package uk.ac.gla.confound.problem;

import java.util.ArrayList;

public class Variable {
    public int index;
    public int value;
    public ArrayList<Integer> currentDomain;
    public Domain domain;
    public String name = "";

    public Variable(Domain domain, int index) {
        this.domain = domain;
        this.currentDomain = new ArrayList<>();
        this.value = domain.values()[0];
        this.index = index;
        this.name = "Variable("+index+")";
        for (Integer value: domain.values())
            this.currentDomain.add(value);
    }

    public Variable(Domain domain, int index, String name) {
        this.domain = domain;
        this.currentDomain = new ArrayList<>();
        this.value = domain.values()[0];
        this.index = index;
        for (Integer value: domain.values())
            this.currentDomain.add(value);

        this.name = name;
    }

    // Variables are only equal to other variables iff they share the same index
    @Override
    public boolean equals(Object o) {
        return o instanceof Variable && this.index == ((Variable) o).index;
    }

    @Override
    public String toString() {
        return name + ": " + value + "\n";
    }


    public void setName(String newName) {
        name = newName;
    }
}
