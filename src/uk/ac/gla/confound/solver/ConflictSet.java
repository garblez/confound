package uk.ac.gla.confound.solver;

import java.util.HashSet;
import java.util.Set;

public class ConflictSet {
    public Set<Integer>[] conflicts;

    public ConflictSet(int quantity) {
        conflicts = new HashSet[quantity+1];
        for (int i = 0; i < quantity+1; i++)
            conflicts[i] = new HashSet<>(0);
    }

    public int getMaxValue(int index) {
        int max = 0;
        for (Integer value: conflicts[index])
            if (value > max)
                max = value;
        return max;
    }

    public int size(int index) {
        return conflicts[index].size();
    }

    public void addTo(int index, int value) {
        conflicts[index].add(value);
    }

    public void update(int h, int i) {
        conflicts[h].addAll(conflicts[i]);
        conflicts[h].remove(h);
        for (int j = h+1; j < i; j++) {
            conflicts[j] = new HashSet<>(0);
        }
    }
}
