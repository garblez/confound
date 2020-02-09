package tests;

import org.junit.Before;
import org.junit.Test;

import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Variable;

import static org.junit.Assert.*;

public class VariableTest {

    Variable a;
    Variable b;

    Domain d;

    Variable[] vars;

    Constraint[][] C;


    @Before
    public void setUp() throws Exception {
        d = new Domain(1,4);
        a = new Variable(d, 4);
        b = new Variable(d,5);
        vars = new Variable[4];
        for (int i = 0; i < 4;i++)
            vars[i] = new Variable(d,i);

        vars[0].value = 2;
        vars[1].value = 0;
        vars[2].value = 3;
        vars[3].value = 1;


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
    public void testRemoveFromCurrent() {
        a.currentDomain.remove(0);
        assertNotEquals(a.currentDomain, d.copy());
    }

    @Test
    public void testAddToCurrent() {
        b.currentDomain.add(3);
        assertEquals(b.currentDomain.size(), d.copy().size() + 1);
    }
}