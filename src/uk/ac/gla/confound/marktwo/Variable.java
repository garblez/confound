package uk.ac.gla.confound.marktwo;

import uk.ac.gla.confound.marktwo.Constraint;
import uk.ac.gla.confound.marktwo.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Variable {
    public int index; // Index of the variable in the global variable array
    public int value; // Value of the variable
    public Domain domain;
    public Domain currentDomain;
    public Map<Integer, Constraint> constraints; // Variable Index -> Constraint

    public Variable(int x, Integer[] domainValues) {
        value = x;
        domain = new Domain(domainValues);
        currentDomain = new Domain(domainValues);
        constraints = new HashMap<>();
    }

    public void addConstraintWith(Variable variable, Constraint cons) {
        constraints.put(variable.index, cons);
    }

    // value set as null by default!
    public Variable() {
        domain = new Domain();
        currentDomain = new Domain();
        constraints = new HashMap<>();
    }

    public Variable(int index, int value) {
        this.index = index;
        domain = new Domain();
        currentDomain = new Domain();
        constraints = new HashMap<>();
        this.value = value;
    }

    public Variable with(Domain domain) {
        this.domain = domain;
        this.currentDomain = domain.clone();
        System.out.print("Variable: "+this.index+": ");
        for (Integer value: this.domain.values)
            System.out.print(value+", ");
        System.out.println();
        for (Integer value: this.currentDomain.values)
            System.out.print(value+", ");
        System.out.println();
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Variable && ((Variable) other).value == this.value;
    }
}
