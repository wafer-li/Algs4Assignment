package algs4_assignment;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This is the Subset class.
 * Please put more info.
 *
 * @author Wafer
 * @version 1.0.0
 * @since 16/3/5 17:09
 */
public class Subset {
    public static void main(String [] args) {
        int k = Integer.parseInt(args[0]);
        String [] strings = StdIn.readAllStrings();

        RandomizedQueue<String> stringRandomizedQueue = new RandomizedQueue<>();

        for (String s :
                strings) {
            stringRandomizedQueue.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(stringRandomizedQueue.dequeue());
        }
    }
}
