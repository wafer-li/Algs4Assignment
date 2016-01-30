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

    private byte[] grid;

    private WeightedQuickUnionUF auxUf;

    private int N;  // The N-by-N

    /**
     * There is the statuses of the site.
     * By far, I define that it could has 3 statuses
     */
    private final byte CLOSE = 0;
    private final byte OPEN = 1;
    private final byte CONNECT_TO_BOTTOM = 2;

    /**
     * The virtual top is id[N * N]
     * The virtual bottom is id[ N * N + 1]
     */
    private int virtualTopIndex;


    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;

        grid = new byte[N * N + 1];

        auxUf = new WeightedQuickUnionUF(N * N + 1);

        virtualTopIndex = N * N;
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
     * Mark the specified index of site as connected to bottom.
     * <p>
     * First we check if itself or the site around it are marked as connected to bottom
     * if either is true, we mark the <b>origin site</b>, the <b>site around the origin site</b>
     * and the <b>ancestor of the origin site</b> as connected to bottom
     *
     * @param originComponentIdentifier The current site's component identifier
     *                                  in the grid[] (One Dimension)
     * @param aroundSideSiteComponentIdentifier The component's identifier of the site
     *                                          which is around the current site in the
     *                                          grid[] (One Dimension)
     */
    private void markAsConnectedToBottom(int originComponentIdentifier, int aroundSideSiteComponentIdentifier) {
        if (grid[originComponentIdentifier] == CONNECT_TO_BOTTOM || grid[aroundSideSiteComponentIdentifier] == CONNECT_TO_BOTTOM) {
            grid[originComponentIdentifier] = CONNECT_TO_BOTTOM;
            grid[aroundSideSiteComponentIdentifier] = CONNECT_TO_BOTTOM;
        }
    }



    private void union(int originIndex, int aroundSideIndex) {
        int originSideComponentIdentifier = auxUf.find(originIndex);
        int aroundSideComponentIdentifier = auxUf.find(aroundSideIndex);
        markAsConnectedToBottom(originSideComponentIdentifier,aroundSideComponentIdentifier);
        auxUf.union(originIndex, aroundSideIndex);
    }


    /**
     * Open and link the specified site;
     * When the specified site was opened,
     * it will search the side site of the
     * current site.
     * If there is opened site, it will link it.
     * <p>
     * If the site is located at the first row, link it to the virtualTop.
     * If the site is located at the last row, mark it as connected to bottom.
     *
     * @param i The row of the specified site, within range [1, N]
     * @param j The column of the specified site, within range [1, N]
     */
    public void open(int i, int j) {

        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(i, j)) {
            grid[calculateId(i, j)] = OPEN;

            // Link to the top or mark as connected to bottom
            if (i == 1) {
                auxUf.union(calculateId(i, j), virtualTopIndex);
            }

            if (i == N) {
                grid[calculateId(i, j)] = CONNECT_TO_BOTTOM;
            }

            // Union the sides of it
            // Mark the connect to the top
            if (i + 1 <= N) {
                if (isOpen(i + 1, j)) {
                    union(calculateId(i, j), calculateId(i + 1, j));
                }
            }
            if (i - 1 > 0) {
                if (isOpen(i - 1, j)) {
                    union(calculateId(i, j), calculateId(i - 1, j));
                }
            }
            if (j + 1 <= N) {
                if (isOpen(i, j + 1)) {
                    union(calculateId(i, j), calculateId(i, j + 1));
                }
            }
            if (j - 1 > 0) {
                if (isOpen(i, j - 1)) {
                    union(calculateId(i, j), calculateId(i, j - 1));
                }
            }
        }
    }


    /**
     * Check if the site is open.
     * By checking the gird's value.
     *
     * @param i The row of the site.
     * @param j The column of the site.
     * @return If opened, return true. If not opened, return false.
     */
    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        return grid[calculateId(i, j)] != CLOSE;

    }

    /**
     * Check if the site is Full.
     * If the site has a <b>direct</b> way connect to the top,
     * we call it a full site.
     *
     * This method check this by checking the site is connected to the top.
     * Because we do not have the virtual bottom, so there cannot be the backwash.
     *
     * @param i The row of the specified site
     * @param j The column of the specified site
     * @return If the site is full, return true. If it's not full, return false
     */
    public boolean isFull(int i, int j) {

        if (i < 1 || j < 1 || i > N || j > N) {
            throw new IndexOutOfBoundsException();
        }

        return auxUf.connected(calculateId(i, j), virtualTopIndex);
    }


    /**
     * Check the graph is percolated.
     *
     * This method check this by checking the virtualTop's ancestor is connected to the bottom.
     * As we know, the UF only check connectivity by comparing the root of the component,
     * and if the virtualTop's root is connected to bottom, the virtualTop is definitely connected to the bottom
     *
     * @return If the graph is percolated, return true. If not, return false
     */
    public boolean percolates() {
        // if the top is connected to the bottom
        return grid[auxUf.find(virtualTopIndex)] == CONNECT_TO_BOTTOM;
    }


    /**
     * The test unit
     * @param args The program arguments
     */
    public static void main(String[] args) {

        int[][] a = new int[][]{
                {1, 3},
                {2, 3},
                {3, 3},
                {3, 1},
                {2, 1},
                {1, 1},

        };

        Percolation percolation = new Percolation(3);

        for (int[] auxAn :
                a) {
            int i = auxAn[0];
            int j = auxAn[1];

            percolation.open(i, j);

            StdOut.println("Open site " + i + " , " + j);
            StdOut.println("Is percolated? " + percolation.percolates());
            StdOut.println("Is " + i + " , " + j + " Full? " + percolation.isFull(i, j));
            StdOut.println();
        }

        StdOut.println("Is 3, 1 Full " + percolation.isFull(3, 1));
    }

}

