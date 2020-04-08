package uk.ac.gla.confound.solver;

import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Variable;

import java.util.*;

public class ViolationRegister {
    public Variable subject;
    public HashMap<Integer, HashSet<Integer>> rule = new HashMap<>();

    public ViolationRegister(Variable variable) {
        subject = variable;
    }

    // Adds a violating variable to the subject variable's violation rule for the violated value and updates the
    // subject variable
    public void addViolation(Integer value, Integer variable) {
        HashSet<Integer> violators = rule.getOrDefault(value, new HashSet<>());
        violators.add(variable);
        rule.put(value, violators);
    }

    // Remove the violation rule for value
    public void removeViolation(Integer value) {
        rule.remove(value);
    }

    // Create a rule for the violation of the subject's current value with all previous violators
    public void supposeViolation() {
        HashSet<Integer> violators = new HashSet<>();
        rule.values().forEach(violators::addAll);
        if (violators.isEmpty())
            violators.add(subject.index-1);
        rule.put(subject.value, violators);
    }


    // Get the most recent violating variable responsible for the domain wipeout of the subject variable
    public Integer getRecentCulprit() {
        HashSet<Integer> violators = new HashSet<>();
        rule.values().forEach(violators::addAll);
        if (violators.isEmpty()) {
            return subject.index - 1;
        }
        return Collections.max(violators);
    }

    // Update the current domain for the subject variable such that it is the domain less the restricted value
    // - intended for use on domain-wipeout
    public void updateCurrentDomain() {
        subject.currentDomain = subject.domain.copy();
        for (Integer value: rule.keySet())
            subject.currentDomain.remove(value);
    }

    public Set<Integer> forbiddenValues() {
        return rule.keySet();
    }




    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject.index);
        sb.append(" {");
        for (Integer value: rule.keySet()) {
            sb.append(value);
            sb.append(": ");
            sb.append(rule.get(value));
            sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String... args) {
        Variable[] v = new Variable[3];
        v[0] = new Variable(new Domain(1,2));
        v[1] = new Variable(new Domain(1,2));
        v[2] = new Variable(new Domain(1,3));

        ViolationRegister[] violate = new ViolationRegister[3];
        violate[0] = new ViolationRegister(v[0]);
        violate[1] = new ViolationRegister(v[1]);
        violate[2] = new ViolationRegister(v[2]);
        violate[1].addViolation(2, 0);
        violate[2].addViolation(1, 0);
        violate[2].addViolation(1, 3);
        violate[2].addViolation(0, 0);
        for (ViolationRegister viola: violate)
            System.out.println(viola);
        violate[2].removeViolation(1);
        for (ViolationRegister viola: violate)
            System.out.println(viola);
    }
}
