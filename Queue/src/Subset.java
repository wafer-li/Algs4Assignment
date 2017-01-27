import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

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
        StdRandom.shuffle(strings);
        Deque<String> deque = new Deque<>();

        for (int i = 0; i < k; i++) {
            deque.addFirst(strings[i]);
        }

        for (String s: deque) {
            StdOut.println(s);
        }
    }
}
