package tests;

import org.junit.Before;
import org.junit.Test;

import uk.ac.gla.confound.constraint.AlwaysTrueConstraint;
import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;

import static org.junit.Assert.*;

public class VariableTest {

    Variable a;
    Variable b;

    Domain d;

    Variable[] vars;

    Constraint[][] C;

    public class TestConstraintQueens extends Constraint{
        Variable[] vars;
        int i, j;
        TestConstraintQueens(Problem p, int i, int j, Variable[] v) {
            super(null, i, j);
            this.i=i;
            this.j=j;
            vars = v;
        }

        public boolean check() {
            return !vars[i].equals(vars[j])
                    && Math.abs(vars[i].value - vars[j].value) != Math.abs(i - j);
        }
    }


    @Before
    public void setUp() throws Exception {
        d = new Domain(1,4);
        a = new Variable(d);
        b = new Variable(d);
        vars = new Variable[4];
        for (int i = 0; i < 4;i++)
            vars[i] = new Variable(d);

        vars[0].value = 2;
        vars[1].value = 0;
        vars[2].value = 3;
        vars[3].value = 1;

        C = new Constraint[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    C[i][j] = new AlwaysTrueConstraint();
                } else {
                    C[i][j] = new TestConstraintQueens(null, i, j, vars);
                }
            }
        }

    }

    @Test
    public void testEquals() {
        assertEquals(a, b);
    }

    @Test
    public void testInequal() {
        b.value = 4;
        assertNotEquals(a, b);
    }


    @Test
    public void testConstraintsHeld() {
        for (int i =0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertTrue(C[i][j].check());
    }
}