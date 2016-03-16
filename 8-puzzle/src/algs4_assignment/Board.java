package algs4_assignment;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the Board class.
 * Please put more info.
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/9 00:27
 */
public class Board {

    private int [][] pieces;
    private final int N;

    public Board(int [][] blocks) {
        N = blocks.length;

        // Copy the pieces
        pieces = new int[N][N];
        for (int i = 0; i < N; i++) {
            pieces[i] = Arrays.copyOf(blocks[i], blocks[i].length);
        }
    }

    public int dimension() {
        return N;
    }

    /**
     * Number of blocks out of place
     * @return The number of blocks out of place
     */
    public int hamming() {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (pieces[i][j] != goalValueAt(i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private int goalValueAt(int i, int j) {
        if (isEnd(i, j)) {
            return 0;
        }
        else {
            return i * N + j + 1;
        }
    }

    private boolean isEnd(int i, int j) {
        return i == N - 1 && j == N - 1;
    }

    /**
     * Sum of manhattan distance between blocks and goal
     * @return The sum of manhattan distance between blocks and goal
     */
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = pieces[i][j];

                if (value != 0 && value != goalValueAt(i, j)) {
                    int goalX = value / N;
                    int goalY = value % N - 1;
                    int distance = Math.abs(i - goalX) + Math.abs(j - goalY);

                    sum += distance;
                }

            }
        }

        return sum;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Twin is that a board obtained by
     * exchanging two adjacent blocks in the same row
     * @return The twin board
     */
    public Board twin() {
        Board board = new Board(pieces);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (pieces[i][j] != 0 && pieces[i][j + 1] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }

        return null;
    }

    private boolean swap(int i, int j, int it, int jt) {
        if (it < 0 || it >= N || jt < 0 || jt >= N) {
            return false;
        }

        int temp = pieces[i][j];
        pieces[i][j] = pieces[it][jt];
        pieces[it][jt] = temp;

        return true;
    }

    public Iterable<Board> neighbors() {
        // Find the empty block
        int i0 = 0, j0 = 0;
        find_empty_block:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (pieces[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    break find_empty_block;
                }
            }
        }

        // Create board and add it to list
        List<Board> neighbors = new ArrayList<>();
        Board board = new Board(pieces);

        // Swap with the around side
        // Swap up side
        if (board.swap(i0, j0, i0 - 1, j0)) {
            neighbors.add(board);
        }

        // Swap down side
        board = new Board(pieces);
        if (board.swap(i0, j0, i0 + 1, j0)) {
            neighbors.add(board);
        }

        // Swap left side
        board = new Board(pieces);
        if (board.swap(i0, j0, i0, j0 - 1)) {
            neighbors.add(board);
        }

        // Swap right side
        board = new Board(pieces);
        if (board.swap(i0, j0, i0, j0 + 1)) {
            neighbors.add(board);
        }

        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Board that = (Board) obj;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.pieces[i][j] != that.pieces[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                stringBuilder.append(pieces[i][j]);
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static void main(String [] args) {
        int[][] pieces = new int[3][3];

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces.length; j++) {
                pieces[i][j] = i * pieces.length + j + 1;
            }
        }

        Board board = new Board(pieces);
        Board board1 = new Board(pieces);

        StdOut.println(board.equals(board1));
    }
}
