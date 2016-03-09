package algs4_assignment;

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

    private LineSegment[] collinear;

    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

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

            double slope = base.slopeTo(sorted[0]);
            int count = 0;
            for (int j = 1; j < sorted.length; j++) {
                double slopeNext = base.slopeTo(points[j]);

                if (slope == slopeNext) {
                    count++;
                } else if (count >= 2) {
                    lineSegments.add(new LineSegment(base, sorted[count]));
                    count = 0;
                } else {
                    count = 0;
                }

                slope = slopeNext;
            }
        }

        collinear = new LineSegment[lineSegments.size()];
        collinear = lineSegments.toArray(collinear);
    }

    private void checkNullPointer(Point point) {
        if (point == null) {
            throw new NullPointerException();
        }
    }

    public int numberOfSegment() {
        return collinear.length;
    }

    public LineSegment[] segments() {
        return collinear;
    }
}
