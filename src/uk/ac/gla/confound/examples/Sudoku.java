package uk.ac.gla.confound.examples;

import uk.ac.gla.confound.constraint.Constraint;
import uk.ac.gla.confound.constraint.NeqConstraint;
import uk.ac.gla.confound.problem.Domain;
import uk.ac.gla.confound.problem.Problem;
import uk.ac.gla.confound.problem.Variable;
import uk.ac.gla.confound.solver.BacktrackSolver;
import uk.ac.gla.confound.solver.Solver;

public class Sudoku extends Problem {
    static int LEN = 9;

    public Sudoku() {
        super(new Domain(1, LEN), LEN*LEN);
        Variable[][] board = new Variable[LEN][LEN];
        for (int i = 0; i < LEN; i++)
            for (int j = 0; j < LEN; j++)
                board[i][j] = variables[stride(i,j)+1];


        // Make the values in each row all unique
        for (int row = 0; row < LEN; row++)
            makeRowUnique(row, board);


        // Make the values in each column unique
        for (int col = 0; col < LEN; col++)
            makeColUnique(col, board);

        makeGridUnique(0, 0, board);
        makeGridUnique(0, 3, board);
        makeGridUnique(0, 6, board);
        makeGridUnique(3, 0, board);
        makeGridUnique(3, 3, board);
        makeGridUnique(3, 6, board);
        makeGridUnique(6, 0, board);
        makeGridUnique(6,3, board);
        makeGridUnique(6, 6, board);

    }

    public int stride(int x, int y) {
        return x * LEN + y;
    }

    public void makeRowUnique(int row, Variable[][] board) {
        for (int c = 0; c < LEN; c++)
            for (int c1 = 0; c1 < LEN; c1++)
                if (c != c1)
                    constraints[stride(row, c)+1][stride(row, c1)+1] = Constraint.compose(
                            constraints[stride(row, c)+1][stride(row, c1)+1],
                            new NeqConstraint(board[row][c], board[row][c1])
                    );
    }

    public void makeColUnique(int col, Variable[][] board) {
            for (int r = 0; r < LEN; r++)
                for (int r1 = 0; r1 < LEN; r1++)
                    if (r != r1) {
                        constraints[stride(r, col) + 1][stride(r1, col) + 1] = Constraint.compose(
                                constraints[stride(r, col) + 1][stride(r1, col) + 1],
                                new NeqConstraint(board[r][col], board[r1][col])
                        );

                    }
    }

    public int[] gridIndices(int x, int y, int len, Variable[][] board)
    {
        if (board.length % len != 0) return null;
        int[] indices = new int[len*len];
        for (int i = y; i < y+len; i++)
            for (int j = x; j < x+len; j++)
                indices[(j-x)*len+i - y] = variables[stride(i,j)+1].index;
        return indices;
    }

    public void makeGridUnique(int x, int y, Variable[][] board) {
        int[] indices;
        indices = gridIndices(x, y, 3, board);
        for (int i = 0; i < indices.length; i++)
            for (int j = 0; j < indices.length; j++)
                if (i!=j)
                    constraints[indices[i]][indices[j]] = Constraint.compose(
                            constraints[indices[i]][indices[j]], new NeqConstraint(variables[indices[i]], variables[indices[j]])
                    );
    }


    public static void main(String... args) {
        Problem p = new Sudoku();
        Solver btSolver = new BacktrackSolver(p);
        btSolver.solveFor(1);
        btSolver.report(p);
    }
}
