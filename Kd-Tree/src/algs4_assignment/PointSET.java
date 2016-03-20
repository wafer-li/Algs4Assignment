package algs4_assignment;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the PointSET class.
 * The brute force implementation of
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/9 00:27
 */
public class PointSET {

    private SET<Point2D> point2DSET;

    /**
     * Construct an empty set of points
     * I use the SET class of algs4.jar
     */
    public PointSET() {
        point2DSET = new SET<>();
    }

    /**
     * Is the set empty?
     *
     * @return True if the set is empty.
     */
    public boolean isEmpty() {
        return point2DSET.isEmpty();
    }

    /**
     * Number of the points of the set
     * @return The number of the set points
     */
    public int size() {
        return point2DSET.size();
    }

    /**
     * Insert the points into the set,
     * if it is NOT inside the set
     *
     * @param p The point to be inserted
     */
    public void insert(Point2D p) {
        if (!point2DSET.contains(p)) {
            point2DSET.add(p);
        }
    }

    /**
     * If the set contain the point?
     *
     * @param p The specific point
     * @return True if contains.
     */
    public boolean contains(Point2D p) {
        return point2DSET.contains(p);
    }

    /**
     * Draw all the points to std draw
     */
    public void draw() {
        for (Point2D point
                : point2DSET) {
            point.draw();
        }
    }

    /**
     * All the points inside the rectangle
     * @param rect The specific rectangle
     * @return The points inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> point2DList = new ArrayList<>();

        for (Point2D point
                : point2DSET) {
            if (rect.contains(point)) {
                point2DList.add(point);
            }
        }

        return point2DList;
    }

    public Point2D nearest(Point2D p) {
        double distance = -1;
        Point2D goal = null;

        for (Point2D point
                : point2DSET) {
            if (distance == -1) {
                distance = p.distanceTo(point);
                goal = point;
            } else {
                double tempDistance = p.distanceTo(point);
                if (tempDistance < distance) {
                    distance = tempDistance;
                    goal = point;
                }
            }
        }

        return goal;
    }

    public static void main(String [] args) {

    }
}
