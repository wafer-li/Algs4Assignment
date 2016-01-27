package algs4_assignment;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This is the Percolation class
 * It use the two dimensions of boolean array
 * and to use WeightUnionFind, transform the tow
 * dimensions array to the one dimension one.
 *
 * @author Wafer
 * @version 1.0.1
 * @since 2016/1/27 0:36
 */
public class Percolation {

    private boolean[][] grid;

    private WeightedQuickUnionUF uf;

    private int N;  // The N-by-N

    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
    }

    /**
     * Open and link the specified site;
     * When the specified site was opened,
     * it will search the side site of the
     * current site.
     * If there is opened site, it will link it.
     *
     * @param i The row of the specified site, within range [1, N]
     * @param j The column of the specified site, within range [1, N]
     */
    public void open(int i, int j) {

        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;

            /**
             * The relationship of id and i, j is
             * id + 1 = (i - 1) * N + j
             * id = (i - 1) * N + j - 1
             *
             * The virtual top is id[N * N]
             * The virtual bottom is id[N * N + 1]
             */

            // Link the top and the bottom to the virtual top and bottom
            if (i == 1) {
                if (!uf.connected((i - 1) * N + j - 1, N * N))
                    uf.union((i - 1) * N + j - 1, N * N);
            } else if (i == N) {
                if (!uf.connected((i - 1) * N + j - 1, N * N + 1))
                    uf.union((i - 1) * N + j - 1, N * N + 1);
            }

            // Union the sides of it
            if (i + 1 < N + 1) {
                if (isOpen(i + 1, j)) {
                    if (!uf.connected((i - 1) * N + j - 1, (i) * N + j - 1))
                        uf.union((i - 1) * N + j - 1, (i) * N + j - 1);
                }
            }
            if (i - 1 > 0) {
                if (isOpen(i - 1, j)) {
                    if (!uf.connected((i - 1) * N + j - 1, (i - 2) * N + j - 1))
                        uf.union((i - 1) * N + j - 1, ((i - 2) * N) + j - 1);
                }
            }
            if (j + 1 < N + 1) {
                if (isOpen(i, j + 1)) {
                    if (!uf.connected((i - 1) * N + j - 1, (i - 1) * N + j))
                        uf.union((i - 1) * N + j - 1, (i - 1) * N + j);
                }
            }
            if (j - 1 > 0) {
                if (isOpen(i, j - 1)) {
                    if (!uf.connected((i - 1) * N + j - 1, (i - 1) * N + j - 2))
                        uf.union((i - 1) * N + j, (i - 1) * N + j - 2);
                }
            }
        }
    }

    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }
        return grid[i - 1][j - 1];

    }

    public boolean isFull(int i, int j) {

        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        // if the site is connected to the top
        return uf.connected((i - 1) * N + j - 1, N * N);
    }

    public boolean percolates() {
        // if the top is connected to the bottom
        return uf.connected(N * N, N * N + 1);
    }


    public static void main(String[] args) {
        // Test demo

        Percolation percolation = new Percolation(2);

        while (!percolation.percolates()) {
            int i = StdRandom.uniform(1, 3);
            int j = StdRandom.uniform(1, 3);

            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
                StdOut.println("Open " + i + " , " + j + " Site");
                if (percolation.isFull(i, j)) {
                    StdOut.println("The " + i + " , " + j + " is FULL");
                } else {
                    StdOut.println("The " + i + " , " + j + " is not FULL");
                }
            }
        }

        StdOut.println("The graph is percolated!!");
    }

}
