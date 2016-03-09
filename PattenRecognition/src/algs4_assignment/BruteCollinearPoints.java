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

    private Point[] points;
    private LineSegment[] collinear;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;
        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int w = k + 1; w < points.length; w++) {
                        // Check the three slopes of four points
                        // Points: p, q, r, s

                        double slopePToQ = points[i].slopeTo(points[j]);
                        double slopePToR = points[i].slopeTo(points[k]);
                        double slopePToS = points[i].slopeTo(points[w]);

                        if (slopePToQ == slopePToR) {
                            if (slopePToR == slopePToS) {
                                lineSegments.add(new LineSegment(points[i], points[w]));
                            }
                        }
                    }
                }
            }
        }

        collinear = new LineSegment[lineSegments.size()];
        collinear = lineSegments.toArray(collinear);
    }

    public int numberOfSegments() {
        return collinear.length;
    }

    public LineSegment[] segments() {
        return collinear;
    }
}
