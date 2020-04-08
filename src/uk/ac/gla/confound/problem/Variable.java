package uk.ac.gla.confound.problem;

import java.util.ArrayList;

public class Variable {
    // Track the total number of variables, when instantiating the array of variables, we don't want to count the
    // initial nil variable
    public static int total = -1;
    public int index;
    public int value;
    public ArrayList<Integer> currentDomain;
    public Domain domain;
    public String name = "";

    public static void incrementAmount() {
        ++total;
    }

    public Variable(Domain domain) {
        this.domain = domain;
        this.currentDomain = new ArrayList<>();
        this.value = domain.values()[0];
        index = Variable.total;
        this.name = "Variable("+index+")";
        for (Integer value: domain.values())
            this.currentDomain.add(value);
    }

    // For when you want to instantiate a variable later without changing the total and with manually setting the index
    // - used when defining constants in extended Problem classes
    public Variable(Domain domain, int index) {
        this.domain = domain;
        this.currentDomain = new ArrayList<>();
        this.value = domain.values()[0];
        this.index = index;
        for (Integer value: domain.values())
            this.currentDomain.add(value);
    }


    public Variable(Domain domain, String name) {
        this.domain = domain;
        this.currentDomain = new ArrayList<>();
        this.value = domain.values()[0];
        index = Variable.total;
        for (Integer value: domain.values())
            this.currentDomain.add(value);

        this.name = name;
    }

    // Variables are only equal to other variables if they share the same index
    @Override
    public boolean equals(Object o) {
        return o instanceof Variable && this.index == ((Variable) o).index;
    }

    // Print variable as name=value
    // by default this is Variable(index)=value
    @Override
    public String toString() {
        return name + "=" + value;
    }

    public void setName(String newName) {
        name = newName;
    }

    public boolean isNil() {
        return this.index == 0;
    }
}
