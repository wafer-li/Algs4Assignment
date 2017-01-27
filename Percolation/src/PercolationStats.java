import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This is the percolationStats class.
 * Using the Union-Find algorithm to simulate the percolation.
 * And to find the probabilities of the percolation
 *
 * @author Wafer
 * @version 1.0.1
 * @since 2016/1/27 0:30
 */
public class PercolationStats {

    private double[] simulationData;

    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        simulationData = new double[T];
        Percolation percolation;

        // Do the simulation
        for (int k = 0; k < T; k++) {

            int count = 0;

            percolation = new Percolation(N);

            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);

                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    count++;
                }
            }

            simulationData[k] = ((double) count) / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(simulationData);
    }

    public double stddev() {
        return StdStats.stddev(simulationData);
    }

    public double confidenceLo() {

        double u = mean();
        double w = stddev();

        double confidenceLo = u - ((1.96 * w) / Math.sqrt((double) simulationData.length));

        return confidenceLo;
    }

    public double confidenceHi() {

        double u = mean();
        double w = stddev();

        double confidenceHi = u + ((1.96 * w) / Math.sqrt((double) simulationData.length));

        return confidenceHi;
    }

    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(N, T);
        StdOut.println("mean\t\t\t\t\t = " + percolationStats.mean());
        StdOut.println("stddev\t\t\t\t\t = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() +
                " , " + percolationStats.confidenceHi());
    }
}
