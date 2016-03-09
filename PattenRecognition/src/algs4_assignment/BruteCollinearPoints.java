package algs4_assignment;

import java.util.ArrayList;

/**
 * This is the BruteCollinearPoints class.
 * Please put more info.
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/9 00:28
 */
public class BruteCollinearPoints {

    private LineSegment[] collinear;

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        // Check four points P, Q, R, S
        for (int i = 0; i < points.length; i++) {
            checkNullPointer(points[i]);

            for (int j = i + 1; j < points.length; j++) {
                checkNullPointer(points[j]);
                checkRepeatedPoint(points[i], points[j]);

                double slopePToQ = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length; k++) {
                    checkNullPointer(points[k]);

                    checkRepeatedPoint(points[i], points[k]);
                    checkRepeatedPoint(points[j], points[k]);

                    double slopePToR = points[i].slopeTo(points[k]);

                    // P Q R not at the same line, skip.
                    if (slopePToQ != slopePToR) {
                        continue;
                    }

                    for (int w = k + 1; w < points.length; w++) {
                        checkNullPointer(points[w]);

                        checkRepeatedPoint(points[i], points[w]);
                        checkRepeatedPoint(points[j], points[w]);
                        checkRepeatedPoint(points[k], points[w]);

                        double slopePToS = points[i].slopeTo(points[w]);

                        if (slopePToR == slopePToS) {
                            lineSegments.add(new LineSegment(points[i], points[w]));
                        }
                    }
                }
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

    private void checkRepeatedPoint(Point p1, Point p2) {
        if (p1.compareTo(p2) == 0) {
            throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return collinear.length;
    }

    public LineSegment[] segments() {
        return collinear;
    }
}
