package algs4_assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the FastCollinearPoints class.
 * Please put more info.
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/9 15:51
 */
public class FastCollinearPoints {

    private ArrayList<LineSegment> collinear = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }

        points = Arrays.copyOf(points, points.length);
        Arrays.sort(points);

        Point[] sorted = new Point[points.length];
        System.arraycopy(points, 0, sorted, 0, points.length);

        Point prev = null;
        for (int i = 0; i < points.length; i++) {

            checkNullPointer(points[i]);

            // Check repeated point
            if (i == 0) {
                prev = points[i];
            } else if (prev.compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            } else {
                prev = points[i];
            }

            Point base = points[i];

            Arrays.sort(sorted, points[i].slopeOrder());

            int count = 0;
            int j;
            for (j = 1; j < sorted.length; j++) {
                double slope = base.slopeTo(sorted[j]);
                double slopePrev = base.slopeTo(sorted[j - 1]);

                if (slope == slopePrev) {
                    count++;
                } else if (count >= 2) {
                    collinear.add(new LineSegment(base, sorted[j - 1]));
                    count = 0;
                } else {
                    count = 0;
                }
            }

            if (count != 0) {
                collinear.add(new LineSegment(base, sorted[j - 1]));
            }
        }
    }

    private void checkNullPointer(Point point) {
        if (point == null) {
            throw new NullPointerException();
        }
    }

    public int numberOfSegments() {
        return collinear.size();
    }

    public LineSegment[] segments() {
        LineSegment[] collinearLines = new LineSegment[collinear.size()];
        collinearLines = collinear.toArray(collinearLines);
        return collinearLines;
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
