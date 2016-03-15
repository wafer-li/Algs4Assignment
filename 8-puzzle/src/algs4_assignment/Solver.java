package algs4_assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * This is the Solver class.
 * Please put more info.
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/15 23:32
 */
public class Solver {

    public Solver(Board initial) {

    }

    public boolean isSolvable() {
        return false;
    }

    public int moves() {
        return 0;
    }

    public Iterable<Board> solution() {
        return null;
    }

    public static void main(String [] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
