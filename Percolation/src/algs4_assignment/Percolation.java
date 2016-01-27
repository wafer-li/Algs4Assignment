package algs4_assignment;

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

    private byte[] grid;

    private WeightedQuickUnionUF uf, auxUf;

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
        grid = new byte[N * N + 2];
        uf = new WeightedQuickUnionUF(N * N + 2);
        auxUf = new WeightedQuickUnionUF(N * N + 1);

        virtualTopIndex = N * N;
        virtualBottomIndex = N * N + 1;
    }

    /**
     * Calculate the index of uf's private id array
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
            grid[calculateId(i, j)] = 1;

            // Link the top and the bottom to the virtual top and bottom
            if (i == 1) {
                if (!uf.connected(calculateId(i, j), virtualTopIndex)) {
                    uf.union(calculateId(i, j), virtualTopIndex);
                    auxUf.union(calculateId(i, j), virtualTopIndex);
                }
            }

            if (i == N) {
                if (!uf.connected(calculateId(i, j), virtualBottomIndex))
                    uf.union(calculateId(i, j), virtualBottomIndex);
            }

            // Union the sides of it
            if (i + 1 <= N) {
                if (isOpen(i + 1, j)) {
                    uf.union(calculateId(i, j), calculateId(i + 1, j));
                    auxUf.union(calculateId(i, j), calculateId(i + 1, j));
                }
            }
            if (i - 1 > 0) {
                if (isOpen(i - 1, j)) {
                    uf.union(calculateId(i, j), calculateId(i - 1, j));
                    auxUf.union(calculateId(i, j), calculateId(i - 1, j));
                }
            }
            if (j + 1 <= N) {
                if (isOpen(i, j + 1)) {
                    uf.union(calculateId(i, j), calculateId(i, j + 1));
                    auxUf.union(calculateId(i, j), calculateId(i, j + 1));
                }
            }
            if (j - 1 > 0) {
                if (isOpen(i, j - 1)) {
                    uf.union(calculateId(i, j), calculateId(i, j - 1));
                    auxUf.union(calculateId(i, j), calculateId(i, j - 1));
                }
            }
        }
    }

    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        return grid[calculateId(i, j)] == 1;

    }

    public boolean isFull(int i, int j) {

        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        return uf.connected(calculateId(i, j), virtualTopIndex) && auxUf.connected(calculateId(i, j), virtualTopIndex);

    }

    public boolean percolates() {
        // if the top is connected to the bottom
        return uf.connected(virtualTopIndex, virtualBottomIndex);
    }

    public static void main(String[] args) {

    }

}
