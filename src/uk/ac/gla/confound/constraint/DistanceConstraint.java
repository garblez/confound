package uk.ac.gla.confound.constraint;

import uk.ac.gla.confound.problem.Variable;

public class DistanceConstraint extends Constraint {

    int scale;

    public enum Op {
        EQ, NEQ, GTE, LTE, LT, GT
    }

    Op op;

    public DistanceConstraint(Variable a, Variable b, Op op, int scale) {
        super(a,b);
        this.scale = scale;
        this.op = op;
    }

    @Override
    public boolean check() {
        int distance = Math.abs(vi.value - vj.value);
        boolean result;

        switch (op) {
            case NEQ:
                result = distance != scale;
                break;
            case LT:
                result = distance < scale;
                break;
            case GT:
                result = distance > scale;
                break;
            case GTE:
                result = distance >= scale;
                break;
            case LTE:
                result = distance <= scale;
            case EQ:
            default:
                result = distance == scale;
                break;
        }

        return result;
    }
}
