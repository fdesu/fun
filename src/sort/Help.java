package sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Help {

    public static void checkSorted(Collection<Integer> in) {
        int prev = Integer.MIN_VALUE;
        for (Integer i : in) {
            if (prev > i) throw new AssertionError("na-ah!");
            prev = i;
        }
    }

    public static List<Integer> throwTheDices(int size) {
        return new Random().ints(size, 100, 10000)
                .collect(ArrayList::new, List::add, List::addAll);
    }

}
