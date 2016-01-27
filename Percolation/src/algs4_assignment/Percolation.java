package algs4_assignment;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This is the Percolation class
 * It use the two dimensions of boolean array
 * and to use WeightUnionFind, transform the tow
 * dimensions array to the one dimension one.
 * <p>
 * The coordinate of the grid begins with 1;
 * That is the upper-lefter site is (1, 1)
 * <p>
 * The index of the uf's private id[] starts at 0
 * Using two more room to store the virtual top and bottom.
 * So that the virtual top's index is id[N * N]
 * the bottom's index is id[N * N + 1]
 *
 * @author Wafer
 * @version 1.0.1
 * @since 2016/1/27 0:36
 */
public class Percolation {

    private boolean[][] grid;

    private WeightedQuickUnionUF uf;

    private int N;  // The N-by-N

    /**
     * The virtual top is id[N * N]
     * The virtual bottom is id[ N * N + 1]
     */
    private int virtualTopIndex;
    private int virtualBottomIndex;


    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);

        virtualTopIndex = N * N;
        virtualBottomIndex = N * N + 1;
    }

    /**
     * Calculate the reference of uf's private id array
     * of the current array
     * <p>
     * The relationship of id and i, j is
     * id = (i - 1) * N + j - 1
     *
     * @param i The current site's row i
     * @param j The current site's column j
     * @return The reference of the id array
     */
    private int calculateId(int i, int j) {
        return (i - 1) * N + j - 1;
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


            // Link the top and the bottom to the virtual top and bottom
            if (i == 1) {
                if (!uf.connected(calculateId(i, j), virtualTopIndex))
                    uf.union(calculateId(i, j), virtualTopIndex);
            }

            if (i == N) {
                if (!uf.connected(calculateId(i, j), virtualBottomIndex))
                    uf.union(calculateId(i, j), virtualBottomIndex);
            }

            // Union the sides of it
            if (i + 1 <= N) {
                if (isOpen(i + 1, j)) {
                    if (!uf.connected(calculateId(i, j), calculateId(i + 1, j)))
                        uf.union(calculateId(i, j), calculateId(i + 1, j));
                }
            }
            if (i - 1 > 0) {
                if (isOpen(i - 1, j)) {
                    if (!uf.connected(calculateId(i, j), calculateId(i - 1, j)))
                        uf.union(calculateId(i, j), calculateId(i - 1, j));
                }
            }
            if (j + 1 <= N) {
                if (isOpen(i, j + 1)) {
                    if (!uf.connected(calculateId(i, j), calculateId(i, j + 1)))
                        uf.union(calculateId(i, j), calculateId(i, j + 1));
                }
            }
            if (j - 1 > 0) {
                if (isOpen(i, j - 1)) {
                    if (!uf.connected(calculateId(i, j), calculateId(i, j - 1)))
                        uf.union(calculateId(i, j), calculateId(i, j - 1));
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
        return uf.connected(calculateId(i, j), virtualTopIndex);
    }

    public boolean percolates() {
        // if the top is connected to the bottom
        return uf.connected(virtualTopIndex, virtualBottomIndex);
    }

    public static void main(String[] args) {
        int [][] a = new int[][] {
                {1,3},
                {2,3},
                {3,3},
                {3,1},
                {2,1},
                {1,1},

        };

        Percolation percolation = new Percolation(50);

        for (int[] anA : a) {
            int i = anA[0];
            int j = anA[1];

            if (i == 3 && j == 1) {
                StdOut.println("isFull? " + percolation.isFull(3, 1));
                StdOut.println("isPercolate? " + percolation.percolates());
            }

            percolation.open(i, j);

            if (i == 3 && j == 1) {
                StdOut.println("isFull? " + percolation.isFull(3, 1));
                StdOut.println("isPercolate? " + percolation.percolates());
            }

        }
    }

}
