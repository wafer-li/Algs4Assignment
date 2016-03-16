package algs4_assignment;

import edu.princeton.cs.algs4.BoruvkaMST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Solver class.
 * Please put more info.
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/15 23:32
 */
public class Solver {

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private int moves;
        private SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        private int priority() {
            return board.hamming() + moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return priority() - o.priority();
        }
    }

    private boolean isSolvable;
    private final int moves;

    private List<Board> solutions;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        SearchNode originNode = new SearchNode(initial, 0, null);
        SearchNode twinNode = new SearchNode(initial.twin(), 0, null);

        SearchNode goal = solve(originNode, twinNode);

        if (goal == null) {
            moves = -1;
        }
        else {
            moves = goal.moves;
        }
    }

    private SearchNode solve(SearchNode origin, SearchNode twin) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();

        pq.insert(origin);
        pqTwin.insert(twin);

        SearchNode goal = null;
        solutions = new ArrayList<>();

        while (true) {
            SearchNode node = pq.delMin();
            SearchNode nodeTwin = pqTwin.delMin();

            solutions.add(node.board);

            // Check the removed node and its twin
            if (node.board.isGoal()) {
                isSolvable = true;
                goal = node;
                break;
            }

            if (nodeTwin.board.isGoal()) {
                isSolvable = false;
                solutions = null;
                break;
            }

            // Get neighbors and insert
            insertNeighbor(node, pq);
            insertNeighbor(nodeTwin, pqTwin);
        }

        return goal;
    }

    private void insertNeighbor(SearchNode node, MinPQ<SearchNode> pq) {
        for (Board board
                : node.board.neighbors()) {
            if (node.prev == null) {
                SearchNode nodeNeighbor = new SearchNode(board, node.moves + 1, node);
                pq.insert(nodeNeighbor);
            }
            else if (!board.equals(node.prev.board)) {
                SearchNode nodeNeighbor = new SearchNode(board, node.moves + 1, node);
                pq.insert(nodeNeighbor);
            }
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return solutions;
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
