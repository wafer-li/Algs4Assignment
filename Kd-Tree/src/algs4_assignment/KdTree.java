package algs4_assignment;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the KdTree class
 * The unit square is between range(0, 1)
 *
 * @author Wafer Li
 * @since 16/3/20 22:28
 */
public class KdTree {

    private static class Node {
        private Point2D p;          // The point
        private RectHV rectHV;      // The axis-aligned rectangle corresponding to this node
        private Node lb;            // The left/bottom subtree
        private Node rt;            // The right/top subtree

        Node(Point2D p, RectHV rectHV) {
            this.p = p;
            this.rectHV = rectHV;
            lb = null;
            rt = null;
        }
    }


    private Node root;

    private int N;

    private final boolean EVEN_LEVEL = false;
    private final boolean ODD_LEVEL = true;

    private Point2D champion;

    public KdTree() {
        root = null;
        N = 0;
        champion = null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Point2D p) {
        root = insert(root, null, EVEN_LEVEL, p);
        N++;
    }

    private Node insert(Node node, Node prev, boolean levelState, Point2D p) {
        // levelState: true == odd, false == even

        if (node == null && prev == null) {
            // root is null
            return new Node(p, new RectHV(0, 0, 1, 1));
        } else if (node == null) {
            // found the specific position

            double xMin, xMax, yMin, yMax;

            if (levelState) {
                // odd level, upper is even

                yMin = prev.rectHV.ymin();
                yMax = prev.rectHV.ymax();

                double cmp = p.x() - prev.p.x();

                if (cmp < 0) {
                    xMin = prev.rectHV.xmin();
                    xMax = prev.p.x();
                } else {
                    xMin = prev.p.x();
                    xMax = prev.rectHV.xmax();
                }
            } else {
                // even level, upper is odd

                xMin = prev.rectHV.xmin();
                xMax = prev.rectHV.xmax();

                double cmp = p.y() - prev.p.y();

                if (cmp < 0) {
                    yMin = prev.rectHV.ymin();
                    yMax = prev.p.y();
                } else {
                    yMin = prev.p.y();
                    yMax = prev.rectHV.ymax();
                }
            }

            return new Node(p, new RectHV(xMin, yMin, xMax, yMax));
        } else {
            // Not find the popper position
            // Deep down into subtree

            if (levelState) {
                // odd level
                double cmp = p.y() - node.p.y();

                if (cmp < 0) {
                    node.lb = insert(node.lb, node, EVEN_LEVEL, p);
                } else {
                    node.rt = insert(node.rt, node, EVEN_LEVEL, p);
                }
            } else {
                // even level
                double cmp = p.x() - node.p.x();

                if (cmp < 0) {
                    node.lb = insert(node.lb, node, ODD_LEVEL, p);
                } else {
                    node.rt = insert(node.rt, node, ODD_LEVEL, p);
                }
            }
        }

        return node;
    }


    public boolean contains(Point2D p) {
        return contains(root, false, p) != null;
    }

    private Node contains(Node node, boolean levelState, Point2D p) {
        // levelState: odd == true, even == false

        if (node == null) {
            return null;
        }

        if (levelState) {
            // odd level

            double cmp = p.y() - node.p.y();

            if (cmp < 0) {
                return contains(node.lb, EVEN_LEVEL, p);
            } else if (cmp > 0) {
                return contains(node.rt, EVEN_LEVEL, p);
            } else {
                // The same y, check x

                if (p.x() == node.p.x()) {
                    return node;
                } else {
                    return contains(node.rt, EVEN_LEVEL, p);
                }
            }
        } else {
            // even level

            double cmp = p.x() - node.p.x();

            if (cmp < 0) {
                return contains(node.lb, ODD_LEVEL, p);
            } else if (cmp > 0) {
                return contains(node.rt, ODD_LEVEL, p);
            } else {
                // The same x, check y

                if (p.y() == node.p.y()) {
                    return node;
                } else {
                    return contains(node.rt, ODD_LEVEL, p);
                }
            }
        }
    }


    public void draw() {
        draw(root, EVEN_LEVEL);
    }

    private void draw(Node node, boolean levelState) {

        if (node == null) {
            return;
        }

        if (levelState) {
            // odd level, draw BLUE line

            // Draw point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            node.p.draw();

            // Draw line
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rectHV.xmin(), node.p.y(), node.rectHV.xmax(), node.p.y());

            // Recursively traversal
            draw(node.lb, EVEN_LEVEL);
            draw(node.rt, EVEN_LEVEL);
        } else {
            // even level, draw RED line

            // Draw point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            node.p.draw();

            // Draw line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rectHV.ymin(), node.p.x(), node.rectHV.ymax());

            // Recursively traversal
            draw(node.lb, ODD_LEVEL);
            draw(node.rt, ODD_LEVEL);
        }
    }


    /**
     * Find the points which inside the given rectangle
     * <p>
     * Just determine whether the node's rectangle intersects with the given one
     * If there is no intersects, it is no need to access it and its subtree.
     *
     * @param rectHV The specific rectangle
     * @return The list of the points
     */
    public Iterable<Point2D> range(RectHV rectHV) {
        List<Point2D> point2DList = new ArrayList<>();
        range(root, rectHV, point2DList);
        return point2DList;
    }

    private void range(Node node, RectHV rectHV, List<Point2D> point2DList) {

        if (node == null) {
            return;
        }

        if (rectHV.intersects(node.rectHV)) {
            // intersects!!

            if (rectHV.contains(node.p)) {
                point2DList.add(node.p);
            }

            range(node.lb, rectHV, point2DList);
            range(node.rt, rectHV, point2DList);
        }

    }


    /**
     * Find the closest point to the given point
     * <p>
     * The idea of quick find it is:
     * It's NO need to find a node(and its subtree),
     * if the champion is closer than its rectangle.
     *
     * @param p the given point
     * @return The closest point to the given point
     */
    public Point2D nearest(Point2D p) {
        nearest(root, p);
        return champion;
    }

    private void nearest(Node node, Point2D p) {

        if (node == null) {
            return;
        }

        if (champion == null) {
            champion = node.p;
        }

        // Check if the rectangle is farther than champion
        if (champion.distanceSquaredTo(p) >= node.rectHV.distanceSquaredTo(p)) {
            // Nope, check distance and update champion
            if (node.p.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
                champion = node.p;
            } else {
                // deep into subtrees
                nearest(node.lb, p);
                nearest(node.rt, p);
            }
        }
    }

    public static void main(String[] args) {

    }
}
