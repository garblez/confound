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

    public DistanceConstraint(Variable a, Variable b, String op, int scale) {
        super(a, b);
        Op operator;
        switch (op) {
            case "<":
                operator = Op.LT;
                break;
            case "<=":
                operator = Op.LTE;
                break;
            case ">=":
                operator = Op.GTE;
                break;
            case ">":
                operator = Op.GT;
                break;
            case "!=":
                operator = Op.NEQ;
                break;
            case "=":
            default:
                operator = Op.EQ;
        }

        this.scale = scale;
        this.op = operator;
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
