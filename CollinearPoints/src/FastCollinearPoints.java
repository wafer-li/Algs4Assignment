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

        // Make a copy to avoid mutating the input
        points = Arrays.copyOf(points, points.length);

        if (hasDuplicated(points)) {
            throw new IllegalArgumentException();
        }

        // A segment need at least 4 points
        // Therefore, we do not need to check the last 3
        for (int i = 0; i < points.length - 3; i++) {
            // Sort with their natural order to
            // avoid using a copy
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());
            findSegments(points);
        }
    }

    private void findSegments(Point[] points) {
        int start = 1, end = 2;
        Point base = points[0];

        while (end < points.length) {
            while (end < points.length &&
                    base.slopeTo(points[start]) == base.slopeTo(points[end])) {
                end++;
            }

            // Base need to be the least point,
            // to avoid the subSegments.
            // And because java use MergeSort for reference,
            // so we only need to compare with the start.
            if (end - start >= 3 && base.compareTo(points[start]) < 0) {
                collinear.add(new LineSegment(base, points[end - 1]));
            }

            start = end;
            end++;
        }
    }

    private boolean hasDuplicated(Point[] points) {
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {

            checkNullPointer(points[i]);

            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
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
