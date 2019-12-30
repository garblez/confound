package uk.ac.gla.confound;

import java.util.ArrayList;

public class Variable {
    int value; // Value of the variable
    Domain domain;
    Domain currentDomain;
    ArrayList<Constraint> constraint;

    public Variable(int x, Integer[] domainValues) {
        value = x;
        domain = new Domain(domainValues);
        currentDomain = new Domain(domainValues);
        constraint = new ArrayList<Constraint>();
    }
}
